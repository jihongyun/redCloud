

package io.redCloud.service;

import io.redCloud.commons.datasources.DataSourceNames;
import io.redCloud.commons.datasources.annotation.DataSource;
import io.redCloud.modules.sys.entity.SysUserEntity;
import io.redCloud.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试多数据源
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.1.0 2018-01-28
 */
@Service
public class DataSourceTestService {
    @Autowired
    private SysUserService sysUserService;

    public SysUserEntity queryUser(Long userId){
        return sysUserService.getById(userId);
    }

    @DataSource(name = DataSourceNames.FIRST)
    public SysUserEntity queryUser1(Long userId){
        return sysUserService.getById(userId);
    }

    @DataSource(name = DataSourceNames.SECOND)
    public SysUserEntity queryUser2(Long userId){
        return sysUserService.getById(userId);
    }
}
