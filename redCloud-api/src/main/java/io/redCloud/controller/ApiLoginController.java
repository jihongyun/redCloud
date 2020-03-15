package io.redCloud.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import io.redCloud.annotation.Login;
import io.redCloud.common.utils.R;
import io.redCloud.common.validator.ValidatorUtils;
import io.redCloud.form.LoginForm;
import io.redCloud.service.TokenService;
import io.redCloud.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags="登录接口")
public class ApiLoginController{
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;


    @PostMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        Map<String, Object> map = userService.login(form);

        return R.ok(map);
    }

    @Login
    @PostMapping("logout")
    @ApiOperation(value = "退出登录",tags = "退出登录")
    public R logout(@RequestAttribute("userId") long userId){
        tokenService.expireToken(userId);
        return R.ok();
    }

}
