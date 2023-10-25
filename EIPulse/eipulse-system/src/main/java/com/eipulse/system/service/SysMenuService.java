package com.eipulse.system.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.constant.Constants;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.domain.TreeSelect;
import com.eipulse.common.core.domain.entity.SysMenu;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.system.dao.SysMenuDao;
import com.eipulse.system.dao.SysRoleDao;
import com.eipulse.system.dao.SysRoleMenuDao;
import com.eipulse.system.domain.vo.MetaVo;
import com.eipulse.system.domain.vo.RouterVo;

/**
 * 菜單 業務層處理
 * 
 * @author eipulse
 */
@Service
@Transactional
public class SysMenuService extends BaseService<SysMenu, Long> {
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	@Autowired
	private SysMenuDao sysMenuDao;

	@Autowired
	private SysRoleDao sysRoleDao;

	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	@Override
	protected BaseDao<SysMenu, Long> getDao() {
		return sysMenuDao;
	}

	/**
	 * 根據員工查詢系統菜單列表
	 * 
	 * @param userId 員工ID
	 * @return 菜單列表
	 */

	public List<SysMenu> findMenuList(Long userId) {
		return findMenuList(new SysMenu(), userId);
	}

	/**
	 * 查詢系統菜單列表
	 * 
	 * @param menu 菜單資訊
	 * @return 菜單列表
	 */

	public List<SysMenu> findMenuList(SysMenu menu, Long userId) {
		List<SysMenu> menuList = null;
		// 管理員顯示所有菜單資訊
		if (SysUser.isAdmin(userId)) {
			menuList = sysMenuDao.findMenuList(menu);
		} else {
			menu.getParams().put("userId", userId);
			menuList = sysMenuDao.findMenuListByUserId(menu);
		}
		return menuList;
	}

	/**
	 * 根據員工ID查詢權限
	 * 
	 * @param userId 員工ID
	 * @return 權限列表
	 */

	public Set<String> selectMenuPermsByUserId(Long userId) {
		List<String> perms = sysMenuDao.findMenuPermsByUserId(userId);
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

	public List<SysMenu> findMenuTreeByUserId(Long userId) {
		List<SysMenu> menus = null;
		if (SecurityUtils.isAdmin(userId)) {
			menus = sysMenuDao.findMenuTreeAll();
		} else {
			menus = sysMenuDao.findMenuTreeByUserId(userId);
		}
		return getChildPerms(menus, 0);
	}

	/**
	 * 根據角色ID查詢菜單樹資訊
	 * 
	 * @param roleId 角色ID
	 * @return 選中菜單列表
	 */

	public List<Long> findMenuListByRoleId(Long roleId) {
		SysRole role = (SysRole) sysRoleDao.getObject(roleId);
		return sysMenuDao.findMenuListByRoleId(roleId, role.isMenuCheckStrictly());
	}

	/**
	 * 構建前端路由所需要的菜單
	 * 
	 * @param menus 菜單列表
	 * @return 路由列表
	 */

	public List<RouterVo> buildMenus(List<SysMenu> menus) {
		List<RouterVo> routers = new LinkedList<RouterVo>();
		for (SysMenu menu : menus) {
			RouterVo router = new RouterVo();
			router.setHidden("1".equals(menu.getVisible()));
			router.setName(getRouteName(menu));
			router.setPath(getRouterPath(menu));
			router.setComponent(getComponent(menu));
			router.setQuery(menu.getQuery());
			router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(),
					StringUtils.equals("1", menu.getIsCache().toString()), menu.getPath()));
			List<SysMenu> cMenus = menu.getChildren();
			if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
				router.setAlwaysShow(true);
				router.setRedirect("noRedirect");
				router.setChildren(buildMenus(cMenus));
			} else if (isMenuFrame(menu)) {
				router.setMeta(null);
				List<RouterVo> childrenList = new ArrayList<RouterVo>();
				RouterVo children = new RouterVo();
				children.setPath(menu.getPath());
				children.setComponent(menu.getComponent());
				children.setName(StringUtils.capitalize(menu.getPath()));
				children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(),
						StringUtils.equals("1", menu.getIsCache().toString()), menu.getPath()));
				children.setQuery(menu.getQuery());
				childrenList.add(children);
				router.setChildren(childrenList);
			} else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
				router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
				router.setPath("/inner");
				List<RouterVo> childrenList = new ArrayList<RouterVo>();
				RouterVo children = new RouterVo();
				String routerPath = StringUtils.replaceEach(menu.getPath(),
						new String[] { Constants.HTTP, Constants.HTTPS }, new String[] { "", "" });
				children.setPath(routerPath);
				children.setComponent(UserConstants.INNER_LINK);
				children.setName(StringUtils.capitalize(routerPath));
				children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
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

	public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
		List<SysMenu> returnList = new ArrayList<SysMenu>();
		List<Long> tempList = new ArrayList<Long>();
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

	public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
		List<SysMenu> menuTrees = buildMenuTree(menus);
		return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	/**
	 * 是否存在菜單子節點
	 * 
	 * @param menuId 菜單ID
	 * @return 結果
	 */

	public boolean hasChildByMenuId(Long menuId) {
		int result = sysMenuDao.hasChildByMenuId(menuId);
		return result > 0 ? true : false;
	}

	/**
	 * 查詢菜單使用數量
	 * 
	 * @param menuId 菜單ID
	 * @return 結果
	 */

	public boolean checkMenuExistRole(Long menuId) {
		int result = sysRoleMenuDao.checkMenuExistRole(menuId);
		return result > 0 ? true : false;
	}

	/**
	 * 校驗菜單名稱是否唯一
	 * 
	 * @param menu 菜單資訊
	 * @return 結果
	 */

	public String checkMenuNameUnique(SysMenu menu) {
		Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
		SysMenu info = sysMenuDao.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
		if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
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
		if (isMenuFrame(menu)) {
			routerName = StringUtils.EMPTY;
		}
		return routerName;
	}

	/**
	 * 獲取路由地址
	 * 
	 * @param menu 菜單資訊
	 * @return 路由地址
	 */
	public String getRouterPath(SysMenu menu) {
		String routerPath = menu.getPath();
		// 內鏈打開外網方式
		if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
			routerPath = StringUtils.replaceEach(routerPath, new String[] { Constants.HTTP, Constants.HTTPS },
					new String[] { "", "" });
		}
		// 非外鏈並且是一級目錄（類型為目錄）
		if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
				&& UserConstants.NO_FRAME.equals(menu.getIsFrame().toString())) {
			routerPath = "/" + menu.getPath();
		}
		// 非外鏈並且是一級目錄（類型為菜單）
		else if (isMenuFrame(menu)) {
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
		if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
			component = menu.getComponent();
		} else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0
				&& isInnerLink(menu)) {
			component = UserConstants.INNER_LINK;
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
	public boolean isMenuFrame(SysMenu menu) {
		return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
				&& menu.getIsFrame().equals(UserConstants.NO_FRAME);
	}

	/**
	 * 是否為內鏈組件
	 * 
	 * @param menu 菜單資訊
	 * @return 結果
	 */
	public boolean isInnerLink(SysMenu menu) {
		return menu.getIsFrame().equals(UserConstants.NO_FRAME) && StringUtils.ishttp(menu.getPath());
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
		List<SysMenu> returnList = new ArrayList<SysMenu>();
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
	 * 遞歸列表
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
		List<SysMenu> tlist = new ArrayList<SysMenu>();
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
