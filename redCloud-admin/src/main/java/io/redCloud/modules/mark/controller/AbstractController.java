package io.redCloud.modules.mark.controller;


import io.redCloud.modules.sys.entity.SysUserEntity;
import io.redCloud.modules.sys.shiro.ShiroUtils;

public class AbstractController {

    public static SysUserEntity getUser() {
        return ShiroUtils.getUserEntity();
    }
}
