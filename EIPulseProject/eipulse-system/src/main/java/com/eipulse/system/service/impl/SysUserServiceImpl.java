package com.eipulse.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.annotation.DataScope;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.domain.entity.SysDept;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.exception.CustomException;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.common.utils.code.CommonBizCode;
import com.eipulse.system.dao.SysDeptDao;
import com.eipulse.system.dao.SysPostDao;
import com.eipulse.system.dao.SysRoleDao;
import com.eipulse.system.dao.SysUserDao;
import com.eipulse.system.dao.SysUserPostDao;
import com.eipulse.system.dao.SysUserRoleDao;
import com.eipulse.system.domain.SysPost;
import com.eipulse.system.domain.SysUserPost;
import com.eipulse.system.domain.SysUserRole;
import com.eipulse.system.service.ISysConfigService;
import com.eipulse.system.service.ISysUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 員工 業務層處理
 */
@Slf4j
@Transactional(readOnly = true)
@Service
public class SysUserServiceImpl implements ISysUserService {

	@Autowired
	private SysUserDao userDao;

	@Autowired
	private SysRoleDao roleDao;

	@Autowired
	private SysPostDao postDao;

	@Autowired
	private SysUserRoleDao userRoleDao;

	@Autowired
	private SysUserPostDao userPostDao;

	@Autowired
	private SysDeptDao deptDao;

	@Autowired
	private ISysConfigService configService;

	/**
	 * 根據條件分頁查詢員工列表
	 *
	 * @param user 員工資訊
	 * @return 員工資訊集合資訊
	 */
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public Page<SysUser> selectUserList(SysUser user) {
		PageDomain pageReq = TableSupport.buildPageRequest();
		Page<SysUser> mixPage = userDao.findMixPage(user,
				PageRequest.of(pageReq.getPageNum() - 1, pageReq.getPageSize()));
		return mixPage;
	}

	/**
	 * 通過員工名查詢員工
	 *
	 * @param userName 員工名
	 * @return 員工物件資訊
	 */
	@Override
	public SysUser selectUserByUserName(String userName) {
		Optional<SysUser> userOptional = userDao.findByUserName(userName);
		if (!userOptional.isPresent()) {
			return null;
		}
		SysUser sysUser = formatUser(userOptional.get());
		return sysUser;
	}

	private SysUser formatUser(SysUser sysUser) {
		List<SysUserRole> userRoles = userRoleDao.findByUserId(sysUser.getUserId());
		if (!userRoles.isEmpty()) {
			List<Long> roleIds = userRoles.stream().map(r -> r.getRoleId()).collect(Collectors.toList());
			List<SysRole> sysRoles = roleDao.findByRoleIdIn(roleIds);
			sysUser.setRoleIds(roleIds.stream().toArray(Long[]::new));
			sysUser.setRoles(sysRoles);
		}
		Optional<SysDept> deptOptional = deptDao.findById(sysUser.getDeptId());
		sysUser.setDept(deptOptional.orElse(new SysDept()));
		List<SysUserPost> sysUserPosts = userPostDao.findByUserId(sysUser.getUserId());
		if (!sysUserPosts.isEmpty()) {
			Long[] postIds = sysUserPosts.stream().map(p -> p.getPostId()).toArray(Long[]::new);
			sysUser.setPostIds(postIds);
		}
		return sysUser;
	}

	/**
	 * 通過員工ID查詢員工
	 *
	 * @param userId 員工ID
	 * @return 員工物件資訊
	 */
	@Override
	public SysUser selectUserById(Long userId) {
		Optional<SysUser> userOptional = userDao.findById(userId);
		SysUser sysUser = formatUser(userOptional.get());
		return sysUser;
	}

	/**
	 * 查詢員工所屬角色組
	 *
	 * @param userName 員工名
	 * @return 結果
	 */
	@Override
	public String selectUserRoleGroup(String userName) {
		List<SysRole> list = roleDao.findRolesByUserName(userName);
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
	@Override
	public String selectUserPostGroup(String userName) {
		List<SysPost> list = postDao.findPostsByUserName(userName);
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
	@Override
	public String checkUserNameUnique(String userName) {
		Optional<SysUser> optionalSysUser = userDao.findByUserName(userName);
		if (optionalSysUser.isPresent()) {
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
	@Override
	public String checkPhoneUnique(SysUser user) {
		Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
		Optional<SysUser> userOptional = userDao.findByPhonenumber(user.getPhonenumber());
		if (userOptional.isPresent() && userOptional.get().getUserId().longValue() != userId.longValue()) {
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
	@Override
	public String checkEmailUnique(SysUser user) {
		Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
		Optional<SysUser> userOptional = userDao.findByEmail(user.getEmail());
		if (userOptional.isPresent() && userOptional.get().getUserId().longValue() != userId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校驗員工是否允許操作
	 *
	 * @param user 員工資訊
	 */
	@Override
	public void checkUserAllowed(SysUser user) {
		if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
			throw new CustomException("不允許操作超級管理員員工");
		}
	}

	/**
	 * 新增保存員工資訊
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertUser(SysUser user) {
		// 新增員工資訊
		user.setDelFlag(CommonBizCode.DelFlag.NO.getCode());
		user.setCreateTime(new Date());
		user.setCreateBy(SecurityUtils.getUsername());
		userDao.save(user);
		// 新增員工職位關聯
		insertUserPost(user);
		// 新增員工與角色管理
		insertUserRole(user);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改保存員工資訊
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateUser(SysUser user) {
		Long userId = user.getUserId();
		// 刪除員工與角色關聯
		userRoleDao.deleteByUserId(userId);
		// 新增員工與角色管理
		insertUserRole(user);
		// 刪除員工與職位關聯
		userPostDao.deleteByUserId(userId);
		// 新增員工與職位管理
		insertUserPost(user);
		Optional<SysUser> userOptional = userDao.findById(user.getUserId());
		if (userOptional.isPresent() && StringUtils.isBlank(user.getPassword())) {
			user.setPassword(userOptional.get().getPassword());
		}
		userDao.save(user);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改員工狀態
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateUserStatus(SysUser user) {
		SysUser sysUser = userDao.findById(user.getUserId()).get();
		sysUser.setStatus(user.getStatus());
		sysUser.setUpdateBy(user.getUpdateBy());
		sysUser.setUpdateTime(user.getUpdateTime());
		userDao.save(sysUser);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改員工基本資訊
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateUserProfile(SysUser user) {
		userDao.save(user);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改員工頭像
	 *
	 * @param userName 員工名
	 * @param avatar   頭像位址
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateUserAvatar(String userName, String avatar) {
		return userDao.updateUserAvatar(userName, avatar) > 0;
	}

	/**
	 * 重設員工密碼
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int resetPwd(SysUser user) {
		SysUser sysUser = userDao.findById(user.getUserId()).get();
		sysUser.setPassword(user.getPassword());
		sysUser.setUpdateBy(user.getUpdateBy());
		sysUser.setUpdateTime(new Date());
		userDao.save(sysUser);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 重設員工密碼
	 *
	 * @param userName 員工名
	 * @param password 密碼
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int resetUserPwd(String userName, String password) {
		SysUser sysUser = userDao.findByUserName(userName).get();
		sysUser.setPassword(password);
		userDao.save(sysUser);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 新增員工角色資訊
	 *
	 * @param user 員工物件
	 */
	@Transactional(rollbackFor = Exception.class)
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
				userRoleDao.saveAll(list);
			}
		}
	}

	/**
	 * 新增員工職位資訊
	 *
	 * @param user 員工物件
	 */
	@Transactional(rollbackFor = Exception.class)
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
				userPostDao.saveAll(list);
			}
		}
	}

	/**
	 * 通過員工ID刪除員工
	 *
	 * @param userId 員工ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteUserById(Long userId) {
		// 刪除員工與角色關聯
		userRoleDao.deleteByUserId(userId);
		// 刪除員工與職位表
		userPostDao.deleteByUserId(userId);
		userDao.deleteById(userId);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 批次刪除員工資訊
	 *
	 * @param userIds 需要刪除的員工ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteUserByIds(Long[] userIds) {
		for (Long userId : userIds) {
			checkUserAllowed(new SysUser(userId));
		}
		// 刪除員工與角色關聯
		userRoleDao.deleteByUserIdIn(Arrays.asList(userIds));
		// 刪除員工與職位關聯
		userPostDao.deleteByUserIdIn(Arrays.asList(userIds));
		userDao.deleteByUserIdIn(Arrays.asList(userIds));
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 導入員工數據
	 *
	 * @param userList        員工數據列表
	 * @param isUpdateSupport 是否更新支持，如果已存在，則進行更新數據
	 * @param operName        操作員工
	 * @return 結果
	 */
	@Override
	public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
		if (StringUtils.isNull(userList) || userList.size() == 0) {
			throw new CustomException("導入員工數據不能為空！");
		}
		int successNum = 0;
		int failureNum = 0;
		StringBuilder successMsg = new StringBuilder();
		StringBuilder failureMsg = new StringBuilder();
		String password = configService.selectConfigByKey("sys.user.initPassword");
		for (SysUser user : userList) {
			try {
				// 驗證是否存在這個員工
				Optional<SysUser> userOptional = userDao.findByUserName(user.getUserName());
				if (!userOptional.isPresent()) {
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
			throw new CustomException(failureMsg.toString());
		} else {
			successMsg.insert(0, "恭喜您，數據已全部導入成功！共 " + successNum + " 條，數據如下：");
		}
		return successMsg.toString();
	}
}
