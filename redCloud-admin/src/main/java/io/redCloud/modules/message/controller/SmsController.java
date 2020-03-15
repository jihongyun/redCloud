

package io.redCloud.modules.message.controller;

import com.alibaba.fastjson.JSON;
import io.redCloud.common.utils.ConfigConstant;
import io.redCloud.common.utils.Constant;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.R;
import io.redCloud.common.validator.ValidatorUtils;
import io.redCloud.common.validator.group.AliyunGroup;
import io.redCloud.common.validator.group.QcloudGroup;
import io.redCloud.modules.message.service.SysSmsService;
import io.redCloud.modules.message.sms.SmsConfig;
import io.redCloud.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * 短信服务
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-22
 */
@RestController
@RequestMapping("sys/sms")
public class SmsController {
	@Autowired
	private SysSmsService sysSmsService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.SMS_CONFIG_KEY;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:sms:all")
	public LayuiPage list(@RequestParam Map<String, Object> params){
		LayuiPage page = sysSmsService.queryPage(params);

		return page;
	}


    /**
     * 短信配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:sms:all")
    public R<SmsConfig> config(){
		SmsConfig config = sysConfigService.getConfigObject(KEY, SmsConfig.class);

        return R.ok(config);
    }


	/**
	 * 保存短信配置信息
	 */
	@RequestMapping("/saveConfig")
	@RequiresPermissions("sys:sms:all")
	public R saveConfig(@RequestBody SmsConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);

		if(config.getPlatform() == Constant.SmsService.ALIYUN.getValue()){
			//校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		}else if(config.getPlatform() == Constant.SmsService.QCLOUD.getValue()){
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}

        sysConfigService.updateValueByKey(KEY, JSON.toJSONString(config));

		return R.ok();
	}

    /**
     * 发送短信
     */
    @RequestMapping("/send")
    @RequiresPermissions("sys:sms:all")
    public R send(String mobile, String params){
        sysSmsService.send(mobile, params);

        return R.ok();
    }


	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public R delete(@RequestBody Long[] ids){
		sysSmsService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
