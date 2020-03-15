

package io.redCloud.modules.message.sms;

import io.redCloud.common.utils.ConfigConstant;
import io.redCloud.common.utils.Constant;
import io.redCloud.common.utils.SpringContextUtils;
import io.redCloud.modules.sys.service.SysConfigService;

/**
 * 短信Factory
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-22
 */
public class SmsFactory {
    private static SysConfigService sysConfigService;

    static {
        SmsFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static SmsService build(){
        //获取短信配置信息
        SmsConfig config = sysConfigService.getConfigObject(ConfigConstant.SMS_CONFIG_KEY, SmsConfig.class);

        if(config.getPlatform() == Constant.SmsService.ALIYUN.getValue()){
            return new AliyunSmsService(config);
        }else if(config.getPlatform() == Constant.SmsService.QCLOUD.getValue()){
            return new QcloudSmsService(config);
        }

        return null;
    }
}
