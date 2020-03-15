

package io.redCloud.modules.sys.controller;


import io.redCloud.common.annotation.SysLog;
import io.redCloud.common.exception.RRException;
import io.redCloud.common.utils.Constant;
import io.redCloud.common.utils.ExcelUtils;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.R;
import io.redCloud.common.validator.Assert;
import io.redCloud.common.validator.ValidatorUtils;
import io.redCloud.common.validator.group.AddGroup;
import io.redCloud.common.validator.group.UpdateGroup;
import io.redCloud.modules.sys.entity.SysUserEntity;
import io.redCloud.modules.sys.excel.SysUserBean;
import io.redCloud.modules.sys.service.SysUserRoleService;
import io.redCloud.modules.sys.service.SysUserService;
import io.redCloud.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public LayuiPage list(@RequestParam Map<String, Object> params){
		LayuiPage page = sysUserService.queryPage(params);

		return page;
	}

	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R<SysUserEntity> info(){
		return R.ok(getUser());
	}

	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");

		//原密码
		password = ShiroUtils.sha256(password, getUser().getSalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return R.error(501,"原密码不正确");
		}

		return R.ok();
	}

	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R<SysUserEntity> info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.getById(userId);

		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);

		return R.ok(user);
	}

	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);

		sysUserService.insert(user);

		return R.ok();
	}

	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		sysUserService.update(user);

		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@SysLog("修改状态")
	@RequestMapping("/status")
	@RequiresPermissions("sys:user:update")
	public R status(Long userId, Integer status){
		if(userId == null){
			throw new RRException("用户ID不能为空");
		}
		if(status == null){
			throw new RRException("状态不能为空");
		}

		if(userId == Constant.SUPER_ADMIN){
			throw new RRException("超级管理状态，不能修改");
		}

		SysUserEntity user = new SysUserEntity();
		user.setUserId(userId);
		user.setStatus(status);
		sysUserService.updateById(user);

		return R.ok();
	}

	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(Arrays.asList(userIds).contains(1L)){
			return R.error(501,"系统管理员不能删除");
		}

		if(Arrays.asList(userIds).contains(getUserId())){
			return R.error(502,"当前用户不能删除");
		}

		sysUserService.removeByIds(Arrays.asList(userIds));

		return R.ok();
	}

	/**
	 * 导出
	 */
	@RequestMapping("/export")
	@RequiresPermissions("sys:user:export")
	public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
		LayuiPage page = sysUserService.queryPage(params);

		ExcelUtils.exportExcelToTarget(response, "用户管理", page.getData(), SysUserBean.class);
	}
}
