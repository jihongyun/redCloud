package io.redCloud.modules.mark.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import io.redCloud.RedCloudAdminApplication;
import io.redCloud.modules.mark.service.TestService;
/**
 * 
 *
 * @author jihongyun
 * @email 15721460@qq.com
 * @date 2020-02-26 17:25:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedCloudAdminApplication.class)
public class TestTestController{
    @Resource
    private TestService testService;

    @Test
    public void test1() {

    }
}
