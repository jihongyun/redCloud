
package io.redCloud.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.redCloud.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

/**
 * 系统用户
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {

	/**
	 * 查询用户列表
	 */
	List<SysUserEntity> queryList(@Param("pg") Page page, @Param("map") Map<String, Object> params);

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

}
