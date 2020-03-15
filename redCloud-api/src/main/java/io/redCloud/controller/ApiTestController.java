package io.redCloud.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import io.redCloud.annotation.Login;
import io.redCloud.annotation.LoginUser;
import io.redCloud.common.utils.R;
import io.redCloud.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;


@RestController
@RequestMapping("/api")
@Api(tags="测试接口")
public class ApiTestController {

    @Login
    @GetMapping("userInfo")
    @ApiOperation(value="获取用户信息", response=UserEntity.class)
    public R userInfo(@ApiIgnore @LoginUser UserEntity user){
        return R.ok(user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public R userInfo(@ApiIgnore @RequestAttribute("userId") Integer userId){
        return R.ok(userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public R notToken(){
        return R.ok("无需token也能访问。。。");
    }

    @ApiOperation("测试knife4接口文档" )
    @ApiOperationSupport(order = 13,author = "冀鸿运",params = @DynamicParameters(properties = {
            @DynamicParameter(name = "limit",value = "多少条"),
            @DynamicParameter(name = "page",value = "第几页",dataTypeClass = Integer.class,example = "hello")
    }))
    @PostMapping(value = "get",name = "测试接口")
    public R getR(@RequestBody Map<String,Object> params) {

        return R.ok();
    }

}
