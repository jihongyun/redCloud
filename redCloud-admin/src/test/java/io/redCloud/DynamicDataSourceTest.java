package io.redCloud;


import cn.hutool.core.util.ObjectUtil;
import io.redCloud.modules.sys.entity.SysUserEntity;
import io.redCloud.service.DataSourceTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDataSourceTest {
    @Autowired
    private DataSourceTestService dataSourceTestService;

    @Test
    public void test(){
        //默认数据源
        SysUserEntity defaultUser = dataSourceTestService.queryUser(1L);
        System.out.println(ObjectUtil.toString(defaultUser));

        //数据源1
        SysUserEntity user1 = dataSourceTestService.queryUser1(1L);
        System.out.println(ObjectUtil.toString(user1));

        //数据源2
        SysUserEntity user2 = dataSourceTestService.queryUser2(1L);
        System.out.println(ObjectUtil.toString(user2));

        //默认数据源
        SysUserEntity defaultUser2 = dataSourceTestService.queryUser(1L);
        System.out.println(ObjectUtil.toString(defaultUser2));
    }

}
