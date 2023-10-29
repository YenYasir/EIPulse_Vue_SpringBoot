package com.eipulse.framework.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.eipulse.common.annotation.DataScope;
import com.eipulse.common.core.domain.BaseEntity;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.utils.ServletUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.spring.SpringUtils;
import com.eipulse.framework.web.service.TokenService;

/**
 * 數據過濾處理
 */
@Aspect
@Component
public class DataScopeAspect {
	/**
	 * 全部數據權限
	 */
	public static final String DATA_SCOPE_ALL = "1";

	/**
	 * 自定數據權限
	 */
	public static final String DATA_SCOPE_CUSTOM = "2";

	/**
	 * 部門數據權限
	 */
	public static final String DATA_SCOPE_DEPT = "3";

	/**
	 * 部門及以下數據權限
	 */
	public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

	/**
	 * 僅本人數據權限
	 */
	public static final String DATA_SCOPE_SELF = "5";

	/**
	 * 數據權限過濾關鍵字
	 */
	public static final String DATA_SCOPE = "dataScope";

	// 配置織入點
	@Pointcut("@annotation(com.eipulse.common.annotation.DataScope)")
	public void dataScopePointCut() {
	}

	@Before("dataScopePointCut()")
	public void doBefore(JoinPoint point) throws Throwable {
		handleDataScope(point);
	}

	protected void handleDataScope(final JoinPoint joinPoint) {
		// 獲得註解
		DataScope controllerDataScope = getAnnotationLog(joinPoint);
		if (controllerDataScope == null) {
			return;
		}
		// 獲取當前的員工
		LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
		if (StringUtils.isNotNull(loginUser)) {
			SysUser currentUser = loginUser.getUser();
			// 如果是超級管理員，則不過濾數據
			if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin()) {
				dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(),
						controllerDataScope.userAlias());
			}
		}
	}

	/**
	 * 數據範圍過濾
	 *
	 * @param joinPoint 切點
	 * @param user      員工
	 * @param userAlias 別名
	 */
	public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String deptAlias, String userAlias) {
		StringBuilder sqlString = new StringBuilder();

		for (SysRole role : user.getRoles()) {
			String dataScope = role.getDataScope();
			if (DATA_SCOPE_ALL.equals(dataScope)) {
				sqlString = new StringBuilder();
				break;
			} else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
				sqlString.append(StringUtils.format(
						" OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias,
						role.getRoleId()));
			} else if (DATA_SCOPE_DEPT.equals(dataScope)) {
				sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
			} else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
				sqlString.append(StringUtils.format(
						" OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
						deptAlias, user.getDeptId(), user.getDeptId()));
			} else if (DATA_SCOPE_SELF.equals(dataScope)) {
				if (StringUtils.isNotBlank(userAlias)) {
					sqlString.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
				} else {
					// 數據權限為僅本人且沒有userAlias別名不查詢任何數據
					sqlString.append(" OR 1=0 ");
				}
			}
		}

		if (StringUtils.isNotBlank(sqlString.toString())) {
			Object params = joinPoint.getArgs()[0];
			if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
				BaseEntity baseEntity = (BaseEntity) params;
				baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
			}
		}
	}

	/**
	 * 是否存在註解，如果存在就獲取
	 */
	private DataScope getAnnotationLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(DataScope.class);
		}
		return null;
	}
}
