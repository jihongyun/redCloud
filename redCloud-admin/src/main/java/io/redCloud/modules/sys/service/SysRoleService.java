

package io.redCloud.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.modules.sys.entity.SysRoleEntity;

import java.util.Map;


/**
 * 角色
 *
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	LayuiPage queryPage(Map<String, Object> params);

	void insert(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

}
