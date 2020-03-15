

package io.redCloud.modules.message.sms;

import java.util.LinkedHashMap;

/**
 * 短信
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-22
 */
public abstract class SmsService {
    /**
     * 发送成功
     */
    public final static int SUCCESS = 0;
    /**
     * 发送失败
     */
    public final static int FAIL = 1;
    /**
     * 短信配置信息
     */
    SmsConfig config;

    /**
     * 发送短信
     * @param mobile 手机号
     * @param params 参数
     */
    public abstract void sendSms(String mobile, LinkedHashMap<String, String> params);

    /**
     *
     * 发送短信
     * @param mobile 手机号
     * @param params 参数
     * @param signName  短信签名
     * @param template 短信模板
     */
    public abstract void sendSms(String mobile, LinkedHashMap<String, String> params, String signName, String template);
}
