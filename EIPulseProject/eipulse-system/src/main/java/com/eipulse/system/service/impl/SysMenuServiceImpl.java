package com.eipulse.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.domain.TreeSelect;
import com.eipulse.common.core.domain.entity.SysMenu;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.system.dao.SysMenuDao;
import com.eipulse.system.dao.SysRoleDao;
import com.eipulse.system.dao.SysRoleMenuDao;
import com.eipulse.system.dto.MetaVo;
import com.eipulse.system.dto.RouterVo;
import com.eipulse.system.service.ISysMenuService;

/**
 * 菜單 業務層處理
 */
@Transactional(readOnly = true)
@Service
public class SysMenuServiceImpl implements ISysMenuService {

	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	@Autowired
	private SysMenuDao menuDao;

	@Autowired
	private SysRoleDao roleDao;

	@Autowired
	private SysRoleMenuDao roleMenuDao;

	/**
	 * 根據員工查詢系統菜單列表
	 *
	 * @param userId 員工ID
	 * @return 菜單列表
	 */
	@Override
	public List<SysMenu> selectMenuList(Long userId) {
		return selectMenuList(new SysMenu(), userId);
	}

	/**
	 * 查詢系統菜單列表
	 *
	 * @param menu 菜單資訊
	 * @return 菜單列表
	 */
	@Override
	public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
		List<SysMenu> menuList;
		// 管理員顯示所有菜單資訊
		if (SysUser.isAdmin(userId)) {
			Specification<SysMenu> example = new Specification<SysMenu>() {
				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<SysMenu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<>();
					if (StringUtils.isNoneBlank(menu.getMenuName())) {
						Predicate pre = cb.like(root.get("menuName").as(String.class), "%" + menu.getMenuName() + "%");
						list.add(pre);
					}
					if (StringUtils.isNoneBlank(menu.getVisible())) {
						Predicate pre = cb.equal(root.get("visible").as(String.class), menu.getVisible());
						list.add(pre);
					}
					if (StringUtils.isNoneBlank(menu.getStatus())) {
						Predicate pre = cb.like(root.get("status").as(String.class), menu.getStatus());
						list.add(pre);
					}
					if (list.isEmpty()) {
						return null;
					}
					return cb.and(list.toArray(new Predicate[0]));
				}
			};
			menuList = menuDao.findAll(example);
		} else {
			menu.getParams().put("userId", userId);
			menuList = menuDao.findMenuListByUserId(menu);
		}
		return menuList;
	}

	/**
	 * 根據員工ID查詢權限
	 *
	 * @param userId 員工ID
	 * @return 權限列表
	 */
	@Override
	public Set<String> selectMenuPermsByUserId(Long userId) {
		List<String> perms = menuDao.findMenuPermsByUserId(userId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (StringUtils.isNotEmpty(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet;
	}

	/**
	 * 根據員工ID查詢菜單
	 *
	 * @param userId 員工名稱
	 * @return 菜單列表
	 */
	@Override
	public List<SysMenu> selectMenuTreeByUserId(Long userId) {
		List<SysMenu> menus;
		if (SecurityUtils.isAdmin(userId)) {
			menus = menuDao.findMenuTreeAll();
		} else {
			menus = menuDao.findMenuTreeByUserId(userId);
		}
		return getChildPerms(menus, 0);
	}

	/**
	 * 根據角色ID查詢菜單樹資訊
	 *
	 * @param roleId 角色ID
	 * @return 選中菜單列表
	 */
	@Override
	public List<Long> selectMenuListByRoleId(Long roleId) {
		SysRole role = roleDao.findByRoleId(roleId);
		List<Long> ids = menuDao.findMenuListByRoleId(roleId, role.isMenuCheckStrictly());
		return ids;
	}

	/**
	 * 構建前端路由所需要的菜單
	 *
	 * @param menus 菜單列表
	 * @return 路由列表
	 */
	@Override
	public List<RouterVo> buildMenus(List<SysMenu> menus) {
		List<RouterVo> routers = new LinkedList<>();
		for (SysMenu menu : menus) {
			RouterVo router = new RouterVo();
			router.setHidden("1".equals(menu.getVisible()));
			router.setName(getRouteName(menu));
			router.setPath(getRouterPath(menu));
			router.setComponent(getComponent(menu));
			router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(),
					StringUtils.equals("1", menu.getIsCache().toString())));
			List<SysMenu> cMenus = menu.getChildren();
			if (!cMenus.isEmpty() && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
				router.setAlwaysShow(true);
				router.setRedirect("noRedirect");
				router.setChildren(buildMenus(cMenus));
			} else if (isMeunFrame(menu)) {
				List<RouterVo> childrenList = new ArrayList<>();
				RouterVo children = new RouterVo();
				children.setPath(menu.getPath());
				children.setComponent(menu.getComponent());
				children.setName(StringUtils.capitalize(menu.getPath()));
				children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(),
						StringUtils.equals("1", menu.getIsCache().toString())));
				childrenList.add(children);
				router.setChildren(childrenList);
			}
			routers.add(router);
		}
		return routers;
	}

	/**
	 * 構建前端所需要樹結構
	 *
	 * @param menus 菜單列表
	 * @return 樹結構列表
	 */
	@Override
	public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
		List<SysMenu> returnList = new ArrayList<>();
		List<Long> tempList = new ArrayList<>();
		for (SysMenu dept : menus) {
			tempList.add(dept.getMenuId());
		}
		for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext();) {
			SysMenu menu = iterator.next();
			// 如果是頂級節點, 遍歷該父節點的所有子節點
			if (!tempList.contains(menu.getParentId())) {
				recursionFn(menus, menu);
				returnList.add(menu);
			}
		}
		if (returnList.isEmpty()) {
			returnList = menus;
		}
		return returnList;
	}

	/**
	 * 構建前端所需要下拉樹結構
	 *
	 * @param menus 菜單列表
	 * @return 下拉樹結構列表
	 */
	@Override
	public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
		List<SysMenu> menuTrees = buildMenuTree(menus);
		return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	/**
	 * 根據菜單ID查詢資訊
	 *
	 * @param menuId 菜單ID
	 * @return 菜單資訊
	 */
	@Override
	public SysMenu selectMenuById(Long menuId) {
		return menuDao.findById(menuId).orElse(new SysMenu());
	}

	/**
	 * 是否存在菜單子節點
	 *
	 * @param menuId 菜單ID
	 * @return 結果
	 */
	@Override
	public boolean hasChildByMenuId(Long menuId) {
		int result = menuDao.hasChildByMenuId(menuId);
		return result > 0 ? true : false;
	}

	/**
	 * 查詢菜單使用數量
	 *
	 * @param menuId 菜單ID
	 * @return 結果
	 */
	@Override
	public boolean checkMenuExistRole(Long menuId) {
		int result = roleMenuDao.checkMenuExistRole(menuId);
		return result > 0 ? true : false;
	}

	/**
	 * 新增保存菜單資訊
	 *
	 * @param menu 菜單資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertMenu(SysMenu menu) {
		menuDao.save(menu);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改保存菜單資訊
	 *
	 * @param menu 菜單資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateMenu(SysMenu menu) {
		menuDao.save(menu);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 刪除菜單管理資訊
	 *
	 * @param menuId 菜單ID
	 * @return 結果
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public int deleteMenuById(Long menuId) {
		menuDao.deleteById(menuId);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 校驗菜單名稱是否唯一
	 *
	 * @param menu 菜單資訊
	 * @return 結果
	 */
	@Override
	public String checkMenuNameUnique(SysMenu menu) {
		Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
		List<SysMenu> sysMenus = menuDao.findByMenuNameAndParentId(menu.getMenuName(), menu.getParentId());
		if (!sysMenus.isEmpty() && sysMenus.get(0).getMenuId().longValue() != menuId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 獲取路由名稱
	 *
	 * @param menu 菜單資訊
	 * @return 路由名稱
	 */
	public String getRouteName(SysMenu menu) {
		String routerName = StringUtils.capitalize(menu.getPath());
		// 非外鏈並且是一級目錄（類型為目錄）
		if (isMeunFrame(menu)) {
			routerName = StringUtils.EMPTY;
		}
		return routerName;
	}

	/**
	 * 獲取路由位址
	 *
	 * @param menu 菜單資訊
	 * @return 路由位址
	 */
	public String getRouterPath(SysMenu menu) {
		String routerPath = menu.getPath();
		// 非外鏈並且是一級目錄（類型為目錄）
		if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
				&& UserConstants.NO_FRAME.equals(menu.getIsFrame().toString())) {
			routerPath = "/" + menu.getPath();
		}
		// 非外鏈並且是一級目錄（類型為菜單）
		else if (isMeunFrame(menu)) {
			routerPath = "/";
		}
		return routerPath;
	}

	/**
	 * 獲取組件資訊
	 *
	 * @param menu 菜單資訊
	 * @return 組件資訊
	 */
	public String getComponent(SysMenu menu) {
		String component = UserConstants.LAYOUT;
		if (StringUtils.isNotEmpty(menu.getComponent()) && !isMeunFrame(menu)) {
			component = menu.getComponent();
		} else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
			component = UserConstants.PARENT_VIEW;
		}
		return component;
	}

	/**
	 * 是否為菜單內部跳轉
	 *
	 * @param menu 菜單資訊
	 * @return 結果
	 */
	public boolean isMeunFrame(SysMenu menu) {
		return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
				&& menu.getIsFrame().equals(UserConstants.NO_FRAME);
	}

	/**
	 * 是否為parent_view組件
	 *
	 * @param menu 菜單資訊
	 * @return 結果
	 */
	public boolean isParentView(SysMenu menu) {
		return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
	}

	/**
	 * 根據父節點的ID獲取所有子節點
	 *
	 * @param list     分類表
	 * @param parentId 傳入的父節點ID
	 * @return String
	 */
	public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
		List<SysMenu> returnList = new ArrayList<>();
		for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();) {
			SysMenu t = iterator.next();
			// 一、根據傳入的某個父節點ID,遍歷該父節點的所有子節點
			if (t.getParentId() == parentId) {
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	/**
	 * 遞迴列表
	 *
	 * @param list
	 * @param t
	 */
	private void recursionFn(List<SysMenu> list, SysMenu t) {
		// 得到子節點列表
		List<SysMenu> childList = getChildList(list, t);
		t.setChildren(childList);
		for (SysMenu tChild : childList) {
			if (hasChild(list, tChild)) {
				recursionFn(list, tChild);
			}
		}
	}

	/**
	 * 得到子節點列表
	 */
	private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
		List<SysMenu> tlist = new ArrayList<>();
		Iterator<SysMenu> it = list.iterator();
		while (it.hasNext()) {
			SysMenu n = it.next();
			if (n.getParentId().longValue() == t.getMenuId().longValue()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * 判斷是否有子節點
	 */
	private boolean hasChild(List<SysMenu> list, SysMenu t) {
		return getChildList(list, t).size() > 0 ? true : false;
	}
}
