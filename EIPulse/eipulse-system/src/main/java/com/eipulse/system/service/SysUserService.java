package com.eipulse.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.annotation.DataScope;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.domain.entity.SysDept;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.common.exception.ServiceException;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.common.utils.spring.SpringUtils;
import com.eipulse.system.dao.SysDeptDao;
import com.eipulse.system.dao.SysPostDao;
import com.eipulse.system.dao.SysRoleDao;
import com.eipulse.system.dao.SysUserDao;
import com.eipulse.system.dao.SysUserPostDao;
import com.eipulse.system.dao.SysUserRoleDao;
import com.eipulse.system.domain.SysPost;
import com.eipulse.system.domain.SysUserPost;
import com.eipulse.system.domain.SysUserRole;

/**
 * 員工 業務層處理
 * 
 * @author eipulse
 */
@Service
@Transactional
public class SysUserService extends BaseService<SysUser, Long> {
	private static final Logger log = LoggerFactory.getLogger(SysUserService.class);

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysRoleDao sysRoleDao;

	@Autowired
	private SysPostDao sysPostDao;

	@Autowired
	SysDeptDao sysDeptDao;

	@Resource
	private SysUserRoleDao sysUserRoleDao;

	@Resource
	private SysUserPostDao sysUserPostDao;

	@Autowired
	private SysConfigService sysConfigService;

	@Override
	protected BaseDao<SysUser, Long> getDao() {
		return sysUserDao;
	}

	/**
	 * 根據條件分頁查詢員工列表
	 * 
	 * @param user 員工資訊
	 * @return 員工資訊集合資訊
	 */

	@DataScope(deptAlias = "d", userAlias = "u")
	public Page findUserList(SysUser user) {
		return sysUserDao.findUserList(user);
	}

	/**
	 * 根據條件分頁查詢已分配員工角色列表
	 * 
	 * @param user 員工資訊
	 * @return 員工資訊集合資訊
	 */

	@DataScope(deptAlias = "d", userAlias = "u")
	public Page findAllocatedList(SysUser user) {
		return sysUserDao.findAllocatedList(user);
	}

	/**
	 * 根據條件分頁查詢未分配員工角色列表
	 * 
	 * @param user 員工資訊
	 * @return 員工資訊集合資訊
	 */

	@DataScope(deptAlias = "d", userAlias = "u")
	public Page findUnallocatedList(SysUser user) {
		return sysUserDao.findUnallocatedList(user);
	}

	/**
	 * 通過員工名查詢員工
	 * 
	 * @param userName 員工名
	 * @return 員工物件資訊
	 */

	public SysUser selectUserByUserName(String userName) {
		SysUser sysUser = sysUserDao.selectUserByUserName(userName);
		sysUser = formatUser(sysUser);
		return sysUser;
	}

	/**
	 *
	 * @param sysUser 員工
	 * @return 增加部門物件、角色List的員工物件
	 */
	private SysUser formatUser(SysUser sysUser) {
		List<SysUserRole> userRoleList = sysUserRoleDao.findByUserId(sysUser.getUserId());
		if (!ValidateUtil.isEmpty(userRoleList)) {
			List<Long> roleIds = userRoleList.stream().map(r -> r.getRoleId()).collect(Collectors.toList());
			List<SysRole> roleList = sysRoleDao.findByRoleIdIn(roleIds.stream().toArray(Long[]::new));
			sysUser.setRoleIds(roleIds.stream().toArray(Long[]::new));
			sysUser.setRoles(roleList);
		}
		SysDept sysDept = (SysDept) sysDeptDao.getObject(sysUser.getDeptId());
		sysUser.setDept(sysDept);
		List<SysUserPost> userPostList = sysUserPostDao.findByUserId(sysUser.getUserId());
		if (!userPostList.isEmpty()) {
			Long[] postIds = userPostList.stream().map(p -> p.getPostId()).toArray(Long[]::new);
			sysUser.setPostIds(postIds);
		}
		return sysUser;
	}

	/**
	 * 查詢員工所屬角色組
	 * 
	 * @param userName 員工名
	 * @return 結果
	 */

	public String selectUserRoleGroup(String userName) {
		List<SysRole> list = sysRoleDao.findRolesByUserName(userName);
		StringBuffer idsStr = new StringBuffer();
		for (SysRole role : list) {
			idsStr.append(role.getRoleName()).append(",");
		}
		if (StringUtils.isNotEmpty(idsStr.toString())) {
			return idsStr.substring(0, idsStr.length() - 1);
		}
		return idsStr.toString();
	}

	/**
	 * 查詢員工所屬職位組
	 * 
	 * @param userName 員工名
	 * @return 結果
	 */

	public String selectUserPostGroup(String userName) {
		List<SysPost> list = sysPostDao.findPostsByUserName(userName);
		StringBuffer idsStr = new StringBuffer();
		for (SysPost post : list) {
			idsStr.append(post.getPostName()).append(",");
		}
		if (StringUtils.isNotEmpty(idsStr.toString())) {
			return idsStr.substring(0, idsStr.length() - 1);
		}
		return idsStr.toString();
	}

	/**
	 * 校驗員工名稱是否唯一
	 * 
	 * @param userName 員工名稱
	 * @return 結果
	 */

	public String checkUserNameUnique(String userName) {
		int count = sysUserDao.checkUserNameUnique(userName);
		if (count > 0) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校驗員工名稱是否唯一
	 *
	 * @param user 員工資訊
	 * @return
	 */

	public String checkPhoneUnique(SysUser user) {
		Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
		SysUser info = sysUserDao.checkPhoneUnique(user.getPhonenumber());
		if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校驗email是否唯一
	 *
	 * @param user 員工資訊
	 * @return
	 */

	public String checkEmailUnique(SysUser user) {
		Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
		SysUser info = sysUserDao.checkEmailUnique(user.getEmail());
		if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校驗員工是否允許操作
	 * 
	 * @param user 員工資訊
	 */

	public void checkUserAllowed(SysUser user) {
		if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
			throw new ServiceException("不允許操作超級管理員員工");
		}
	}

	/**
	 * 校驗員工是否有數據權限
	 * 
	 * @param userId 員工id
	 */

	public void checkUserDataScope(Long userId) {
		if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
			SysUser user = new SysUser();
			user.setUserId(userId);
			Page page = SpringUtils.getAopProxy(this).findUserList(user);
			if (StringUtils.isEmpty(page.getItems())) {
				throw new ServiceException("沒有權限訪問員工數據！");
			}
		}
	}

	/**
	 * 新增保存員工資訊
	 * 
	 * @param user 員工資訊
	 * @return 結果
	 */

	@Transactional
	public void insertUser(SysUser user) {
		// 新增員工資訊
		sysUserDao.saveObject(user);
		// 新增員工職位關聯
		insertUserPost(user);
		// 新增員工與角色管理
		insertUserRole(user);
	}

	/**
	 * 註冊員工資訊
	 * 
	 * @param user 員工資訊
	 * @return 結果
	 */

	public boolean registerUser(SysUser user) {
		sysUserDao.addObject(user);
		return true;
	}

	/**
	 * 修改保存員工資訊
	 * 
	 * @param user 員工資訊
	 * @return 結果
	 */

	@Transactional
	public void updateUser(SysUser user) {
		Long userId = user.getUserId();
		// 刪除員工與角色關聯
		sysUserRoleDao.deleteUserRoleByUserId(userId);
		// 新增員工與角色管理
		insertUserRole(user);
		// 刪除員工與職位關聯
		sysUserPostDao.deleteUserPostByUserId(userId);
		// 新增員工與職位管理
		insertUserPost(user);
		sysUserDao.updateObject(user);
	}

	/**
	 * 員工授權角色
	 * 
	 * @param userId  員工ID
	 * @param roleIds 角色組
	 */

	@Transactional
	public void insertUserAuth(Long userId, Long[] roleIds) {
		sysUserRoleDao.deleteUserRoleByUserId(userId);
		insertUserRole(userId, roleIds);
	}

	/**
	 * 修改員工狀態
	 * 
	 * @param user 員工資訊
	 * @return 結果
	 */

	public void updateUserStatus(SysUser user) {
		sysUserDao.updateObject(user);
	}

	/**
	 * 修改員工基本資訊
	 * 
	 * @param user 員工資訊
	 * @return 結果
	 */
	@Transactional
	public void updateUserProfile(SysUser user) {
		sysUserDao.updateObject(user);
	}

	/**
	 * 修改員工頭像
	 * 
	 * @param userName 員工名
	 * @param avatar   頭像地址
	 * @return 結果
	 */

	public boolean updateUserAvatar(String userName, String avatar) {
		return sysUserDao.updateUserAvatar(userName, avatar) > 0;
	}

	/**
	 * 重置員工密碼
	 * 
	 * @param userName 員工名
	 * @param password 密碼
	 * @return 結果
	 */

	public int resetUserPwd(String userName, String password) {
		return sysUserDao.resetUserPwd(userName, password);
	}

	/**
	 * 新增員工角色資訊
	 * 
	 * @param user 員工j4ru04
	 */
	public void insertUserRole(SysUser user) {
		Long[] roles = user.getRoleIds();
		if (StringUtils.isNotNull(roles)) {
			// 新增員工與角色管理
			List<SysUserRole> list = new ArrayList<SysUserRole>();
			for (Long roleId : roles) {
				SysUserRole ur = new SysUserRole();
				ur.setUserId(user.getUserId());
				ur.setRoleId(roleId);
				list.add(ur);
			}
			if (list.size() > 0) {
				sysUserRoleDao.saveList(list);
			}
		}
	}

	/**
	 * 新增員工職位資訊
	 * 
	 * @param user 員工物件
	 */
	public void insertUserPost(SysUser user) {
		Long[] posts = user.getPostIds();
		if (StringUtils.isNotNull(posts)) {
			// 新增員工與職位管理
			List<SysUserPost> list = new ArrayList<SysUserPost>();
			for (Long postId : posts) {
				SysUserPost up = new SysUserPost();
				up.setUserId(user.getUserId());
				up.setPostId(postId);
				list.add(up);
			}
			if (list.size() > 0) {
				sysUserPostDao.saveList(list);
			}
		}
	}

	/**
	 * 新增員工角色資訊
	 * 
	 * @param userId  員工ID
	 * @param roleIds 角色組
	 */
	public void insertUserRole(Long userId, Long[] roleIds) {
		if (StringUtils.isNotNull(roleIds)) {
			// 新增員工與角色管理
			List<SysUserRole> list = new ArrayList<SysUserRole>();
			for (Long roleId : roleIds) {
				SysUserRole ur = new SysUserRole();
				ur.setUserId(userId);
				ur.setRoleId(roleId);
				list.add(ur);
			}
			if (list.size() > 0) {
				sysUserRoleDao.saveList(list);
			}
		}
	}

	/**
	 * 通過員工ID刪除員工
	 * 
	 * @param userId 員工ID
	 * @return 結果
	 */

	@Transactional
	public void deleteUserById(Long userId) {
		// 刪除員工與角色關聯
		sysUserRoleDao.deleteUserRoleByUserId(userId);
		// 刪除員工與職位表
		sysUserPostDao.deleteUserPostByUserId(userId);
		sysUserDao.deleteObject(userId);
	}

	/**
	 * 批量刪除員工資訊
	 * 
	 * @param userIds 需要刪除的員工ID
	 * @return 結果
	 */

	@Transactional
	public void deleteUserByIds(Long[] userIds) {
		for (Long userId : userIds) {
			checkUserAllowed(new SysUser(userId));
		}
		// 刪除員工與角色關聯
		sysUserRoleDao.deleteUserRole(userIds);
		// 刪除員工與職位關聯
		sysUserPostDao.deleteUserPost(userIds);
		this.deleteObject(userIds);
	}

	/**
	 * 導入員工數據
	 * 
	 * @param userList        員工數據列表
	 * @param isUpdateSupport 是否更新支持，如果已存在，則進行更新數據
	 * @param operName        操作員工
	 * @return 結果
	 */

	public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
		if (StringUtils.isNull(userList) || userList.size() == 0) {
			throw new ServiceException("導入員工數據不能為空！");
		}
		int successNum = 0;
		int failureNum = 0;
		StringBuilder successMsg = new StringBuilder();
		StringBuilder failureMsg = new StringBuilder();
		String password = sysConfigService.selectConfigByKey("sys.user.initPassword");
		for (SysUser user : userList) {
			try {
				// 驗證是否存在這個員工
				SysUser u = sysUserDao.selectUserByUserName(user.getUserName());
				if (StringUtils.isNull(u)) {
					user.setPassword(SecurityUtils.encryptPassword(password));
					user.setCreateBy(operName);
					this.insertUser(user);
					successNum++;
					successMsg.append("<br/>" + successNum + "、帳號 " + user.getUserName() + " 導入成功");
				} else if (isUpdateSupport) {
					user.setUpdateBy(operName);
					this.updateUser(user);
					successNum++;
					successMsg.append("<br/>" + successNum + "、帳號 " + user.getUserName() + " 更新成功");
				} else {
					failureNum++;
					failureMsg.append("<br/>" + failureNum + "、帳號 " + user.getUserName() + " 已存在");
				}
			} catch (Exception e) {
				failureNum++;
				String msg = "<br/>" + failureNum + "、帳號 " + user.getUserName() + " 導入失敗：";
				failureMsg.append(msg + e.getMessage());
				log.error(msg, e);
			}
		}
		if (failureNum > 0) {
			failureMsg.insert(0, "很抱歉，導入失敗！共 " + failureNum + " 條數據格式不正確，錯誤如下：");
			throw new ServiceException(failureMsg.toString());
		} else {
			successMsg.insert(0, "恭喜您，數據已全部導入成功！共 " + successNum + " 條，數據如下：");
		}
		return successMsg.toString();
	}
}
