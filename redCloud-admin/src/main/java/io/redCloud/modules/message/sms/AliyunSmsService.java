

package io.redCloud.modules.message.sms;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import io.redCloud.common.exception.RRException;
import io.redCloud.common.utils.Constant;
import io.redCloud.common.utils.SpringContextUtils;
import io.redCloud.modules.message.service.SysSmsService;
import org.apache.commons.collections.MapUtils;

import java.util.LinkedHashMap;

/**
 * 阿里云短信服务
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-22
 */
public class AliyunSmsService extends SmsService {
    /**
     * 短信API产品名称（短信产品名固定，无需修改）
     */
    private final String PRODUCT = "Dysmsapi";
    /**
     * 短信API产品域名（接口地址固定，无需修改）
     */
    private final String DOMAIN = "dysmsapi.aliyuncs.com";

    private IClientProfile profile;


    public AliyunSmsService(SmsConfig config){
        this.config = config;

        //初始化
        init();
    }

    private void init(){
        try {
            //初始化acsClient，暂不支持region化
            profile = DefaultProfile.getProfile("cn-hangzhou", config.getAliyunAccessKeyId(), config.getAliyunAccessKeySecret());
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSms(String mobile, LinkedHashMap<String, String> params) {
        this.sendSms(mobile, params, config.getAliyunSignName(), config.getAliyunTemplateCode());
    }

    @Override
    public void sendSms(String mobile, LinkedHashMap<String, String> params, String signName, String template) {
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");

        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        //待发送手机号，支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码，批量调用相对于单条调用及时性稍有延迟，验证码类型的短信推荐使用单条调用的方式
        //发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如"0085200000000"
        request.setPhoneNumbers(mobile);
        //短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //短信模板-可在短信控制台中找到
        request.setTemplateCode(template);
        //参数
        if(MapUtils.isNotEmpty(params)){
            request.setTemplateParam(JSON.toJSONString(params));
        }

        SendSmsResponse response;
        try {
            IAcsClient acsClient = new DefaultAcsClient(profile);
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            throw new RRException("发送阿里云短信失败", e);
        }

        int status = SmsService.SUCCESS;
        if(!"OK".equalsIgnoreCase(response.getCode())){
            status = SmsService.FAIL;
        }

        //保存短信记录
        SysSmsService sysSmsService = (SysSmsService) SpringContextUtils.getBean("sysSmsService");
        sysSmsService.insert(Constant.SmsService.ALIYUN.getValue(), mobile, params, status);

        if(status == SmsService.FAIL){
            throw new RRException("发送阿里云短信失败，" + response.getMessage());
        }
    }

    public static void main(String[] args) {
        SmsConfig config = new SmsConfig();
        config.setPlatform(1);
        config.setAliyunAccessKeyId("LTAIjTWNUcRT51hl");
        config.setAliyunAccessKeySecret("bBpo682QerGLklgRMOUvXasbc1CG8G");
        config.setAliyunSignName("RedCloud");
        config.setAliyunTemplateCode("SMS_12950669");

        AliyunSmsService aliyunSmsService = new AliyunSmsService(config);

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("code", "123456");

        aliyunSmsService.sendSms("13908495187,18774035187", params);
    }
}
