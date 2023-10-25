package com.eipulse.web.controller.tool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.utils.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * swagger 員工測試方法
 * 
 * @author eipulse
 */
@Api("員工資訊管理")
@RestController
@RequestMapping("/test/user")
public class TestController extends BaseController {
	private final static Map<Integer, UserEntity> users = new LinkedHashMap<Integer, UserEntity>();
	{
		users.put(1, new UserEntity(230001, "admin", "admin123", "0910276000"));
		users.put(2, new UserEntity(230002, "test", "admin123", "0910276000"));
	}

	@ApiOperation("獲取員工列表")
	@GetMapping("/list")
	public AjaxResult userList() {
		List<UserEntity> userList = new ArrayList<UserEntity>(users.values());
		return AjaxResult.success(userList);
	}

	@ApiOperation("獲取員工詳細")
	@ApiImplicitParam(name = "userId", value = "員工ID", required = true, dataType = "int", paramType = "path", dataTypeClass = Integer.class)
	@GetMapping("/{userId}")
	public AjaxResult getUser(@PathVariable Integer userId) {
		if (!users.isEmpty() && users.containsKey(userId)) {
			return AjaxResult.success(users.get(userId));
		} else {
			return error("員工不存在");
		}
	}

	@ApiOperation("新增員工")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "員工id", dataType = "Integer", dataTypeClass = Integer.class),
			@ApiImplicitParam(name = "username", value = "員工名稱", dataType = "String", dataTypeClass = String.class),
			@ApiImplicitParam(name = "password", value = "員工密碼", dataType = "String", dataTypeClass = String.class),
			@ApiImplicitParam(name = "mobile", value = "員工手機", dataType = "String", dataTypeClass = String.class) })
	@PostMapping("/save")
	public AjaxResult save(UserEntity user) {
		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
			return error("員工ID不能為空");
		}
		return AjaxResult.success(users.put(user.getUserId(), user));
	}

	@ApiOperation("更新員工")
	@PutMapping("/update")
	public AjaxResult update(@RequestBody UserEntity user) {
		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
			return error("員工ID不能為空");
		}
		if (users.isEmpty() || !users.containsKey(user.getUserId())) {
			return error("員工不存在");
		}
		users.remove(user.getUserId());
		return AjaxResult.success(users.put(user.getUserId(), user));
	}

	@ApiOperation("刪除員工信息")
	@ApiImplicitParam(name = "userId", value = "員工ID", required = true, dataType = "int", paramType = "path", dataTypeClass = Integer.class)
	@DeleteMapping("/{userId}")
	public AjaxResult delete(@PathVariable Integer userId) {
		if (!users.isEmpty() && users.containsKey(userId)) {
			users.remove(userId);
			return success();
		} else {
			return error("員工不存在");
		}
	}
}

@ApiModel(value = "UserEntity", description = "員工實體")
class UserEntity {
	@ApiModelProperty("員工ID")
	private Integer userId;

	@ApiModelProperty("員工名稱")
	private String username;

	@ApiModelProperty("員工密碼")
	private String password;

	@ApiModelProperty("員工手機")
	private String mobile;

	public UserEntity() {

	}

	public UserEntity(Integer userId, String username, String password, String mobile) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.mobile = mobile;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
