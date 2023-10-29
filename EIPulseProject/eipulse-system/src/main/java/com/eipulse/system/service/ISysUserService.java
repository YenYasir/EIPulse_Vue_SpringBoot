package com.eipulse.system.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.eipulse.common.core.domain.entity.SysUser;

/**
 * 員工 業務層
 */
public interface ISysUserService {
	/**
	 * 根據條件分頁查詢員工列表
	 *
	 * @param user 員工資訊
	 * @return 員工資訊集合資訊
	 */
	Page<SysUser> selectUserList(SysUser user);

	/**
	 * 通過員工名查詢員工
	 *
	 * @param userName 員工名
	 * @return 員工物件資訊
	 */
	SysUser selectUserByUserName(String userName);

	/**
	 * 通過員工ID查詢員工
	 *
	 * @param userId 員工ID
	 * @return 員工物件資訊
	 */
	SysUser selectUserById(Long userId);

	/**
	 * 根據員工ID查詢員工所屬角色組
	 *
	 * @param userName 員工名
	 * @return 結果
	 */
	String selectUserRoleGroup(String userName);

	/**
	 * 根據員工ID查詢員工所屬職位組
	 *
	 * @param userName 員工名
	 * @return 結果
	 */
	String selectUserPostGroup(String userName);

	/**
	 * 校驗員工名稱是否唯一
	 *
	 * @param userName 員工名稱
	 * @return 結果
	 */
	String checkUserNameUnique(String userName);

	/**
	 * 校驗手機號碼是否唯一
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	String checkPhoneUnique(SysUser user);

	/**
	 * 校驗email是否唯一
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	String checkEmailUnique(SysUser user);

	/**
	 * 校驗員工是否允許操作
	 *
	 * @param user 員工資訊
	 */
	void checkUserAllowed(SysUser user);

	/**
	 * 新增員工資訊
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	int insertUser(SysUser user);

	/**
	 * 修改員工資訊
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	int updateUser(SysUser user);

	/**
	 * 修改員工狀態
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	int updateUserStatus(SysUser user);

	/**
	 * 修改員工基本資訊
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	int updateUserProfile(SysUser user);

	/**
	 * 修改員工頭像
	 *
	 * @param userName 員工名
	 * @param avatar   頭像位址
	 * @return 結果
	 */
	boolean updateUserAvatar(String userName, String avatar);

	/**
	 * 重設員工密碼
	 *
	 * @param user 員工資訊
	 * @return 結果
	 */
	int resetPwd(SysUser user);

	/**
	 * 重設員工密碼
	 *
	 * @param userName 員工名
	 * @param password 密碼
	 * @return 結果
	 */
	int resetUserPwd(String userName, String password);

	/**
	 * 通過員工ID刪除員工
	 *
	 * @param userId 員工ID
	 * @return 結果
	 */
	int deleteUserById(Long userId);

	/**
	 * 批次刪除員工資訊
	 *
	 * @param userIds 需要刪除的員工ID
	 * @return 結果
	 */
	int deleteUserByIds(Long[] userIds);

	/**
	 * 導入員工數據
	 *
	 * @param userList        員工數據列表
	 * @param isUpdateSupport 是否更新支持，如果已存在，則進行更新數據
	 * @param operName        操作員工
	 * @return 結果
	 */
	String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);
}
