package io.redCloud;

import cn.hutool.core.util.ObjectUtil;
import io.redCloud.common.utils.RedisUtils;

import io.redCloud.modules.sys.entity.SysUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedCloudAdminApplication.class)
public class RedisTest{
    @Resource
    private RedisUtils redisUtils;

    @Test
    public void contextLoads() {
        SysUserEntity user = new SysUserEntity();
        user.setEmail("123456@qq.com");
        redisUtils.set("user", user);

        System.out.println(ObjectUtil.toString(redisUtils.get("user", SysUserEntity.class)));
    }

}
