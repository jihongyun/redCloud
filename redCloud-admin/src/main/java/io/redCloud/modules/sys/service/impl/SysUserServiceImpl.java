
package io.redCloud.modules.sys.service.impl;




import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.redCloud.common.annotation.DataFilter;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.Query;
import io.redCloud.modules.sys.dao.SysUserDao;
import io.redCloud.modules.sys.entity.SysUserEntity;
import io.redCloud.modules.sys.service.SysUserRoleService;
import io.redCloud.modules.sys.service.SysUserService;
import io.redCloud.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	@DataFilter(subDept = true, user = false, tableAlias = "t1")
	public LayuiPage queryPage(Map<String, Object> params) {
		Page page = new Query<SysUserEntity>(params).getPage();
		List<SysUserEntity> userList = baseMapper.queryList(page, params);
		return new LayuiPage(userList, page.getTotal());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean insert(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomUtil.randomString(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		this.save(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysUserEntity user) {
		if(StrUtil.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		}
		this.updateById(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
		boolean update = this.update(userEntity,
				new UpdateWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
		return update;
    }

}
