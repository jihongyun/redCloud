
package io.redCloud.modules.sys.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.redCloud.common.utils.Constant;
import io.redCloud.common.utils.MapUtils;
import io.redCloud.modules.sys.dao.SysMenuDao;
import io.redCloud.modules.sys.entity.NavMenuEntity;
import io.redCloud.modules.sys.entity.SysMenuEntity;
import io.redCloud.modules.sys.service.SysMenuService;
import io.redCloud.modules.sys.service.SysRoleMenuService;
import io.redCloud.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}

		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for(SysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	public List<SysMenuEntity> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	@Override
	public List<NavMenuEntity> getNavMenuList(Long userId) {
		//导航菜单ID列表
		List<Long> menuIdList = null;
		//非系统管理员
		if(userId != Constant.SUPER_ADMIN){
			menuIdList = sysUserService.queryAllMenuId(userId);
		}

		//查询根菜单列表
		List<NavMenuEntity> navList = queryNavListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(navList, menuIdList);

		return navList;
	}

	@Override
	public void delete(Long menuId){
		//删除菜单
		this.removeById(menuId);
		//删除菜单与角色关联
		sysRoleMenuService.removeByMap(new MapUtils().put("menu_id", menuId));
	}

	/**
	 * 查询导航菜单列表
	 */
	private List<NavMenuEntity> queryNavListParentId(Long parentId, List<Long> menuIdList) {
		//查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(parentId, menuIdList);

		List<NavMenuEntity> navList = new ArrayList<>(menuList.size());
		for(SysMenuEntity menuEntity : menuList){
			NavMenuEntity nav = new NavMenuEntity();
			nav.setId(menuEntity.getMenuId());
			nav.setPid(menuEntity.getParentId());
			nav.setIcon(menuEntity.getIcon());
			nav.setTitle(menuEntity.getName());
			nav.setUrl(menuEntity.getUrl());
			nav.setType(menuEntity.getType());
			nav.setSpread(menuEntity.isSpread());

			navList.add(nav);
		}
		return navList;
	}

	/**
	 * 递归
	 */
	private List<NavMenuEntity> getMenuTreeList(List<NavMenuEntity> navList, List<Long> menuIdList){
		List<NavMenuEntity> subMenuList = new ArrayList<>();

		for(NavMenuEntity nav : navList){
			//目录
			if(nav.getType() == Constant.MenuType.CATALOG.getValue()){
				nav.setChildren(getMenuTreeList(queryNavListParentId(nav.getId(), menuIdList), menuIdList));
			}
			subMenuList.add(nav);
		}

		return subMenuList;
	}
}
