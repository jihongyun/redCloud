package io.redCloud.controller;


import cn.hutool.crypto.digest.DigestUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import io.redCloud.common.utils.R;
import io.redCloud.common.validator.ValidatorUtils;
import io.redCloud.entity.UserEntity;
import io.redCloud.form.RegisterForm;
import io.redCloud.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;



@RestController
@RequestMapping("/api")
@Api(tags="注册接口")
public class ApiRegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    @ApiOperation("注册")
    @ApiOperationSupport(params = @DynamicParameters(properties = {
            @DynamicParameter(value = "多少条",name = "limit",dataTypeClass = Integer.class),
            @DynamicParameter(value = "第几页",name = "page",dataTypeClass = Integer.class)
    },name = "map"))
    public R register(@RequestBody RegisterForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setUsername(form.getMobile());
        user.setPassword(DigestUtil.sha256Hex(form.getPassword()));
        user.setCreateTime(new Date());
        userService.save(user);


        return R.ok();
    }
}
