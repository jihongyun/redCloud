

package io.redCloud.modules.message.sms;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import io.redCloud.common.exception.RRException;
import io.redCloud.common.utils.Constant;
import io.redCloud.common.utils.SpringContextUtils;
import io.redCloud.modules.message.service.SysSmsService;
import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 腾讯云短信服务
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-22
 */
public class QcloudSmsService extends SmsService {
    public QcloudSmsService(SmsConfig config){
        this.config = config;
    }

    @Override
    public void sendSms(String mobile, LinkedHashMap<String, String> params) {
        this.sendSms(mobile, params, config.getQcloudSignName(), config.getQcloudTemplateId());
    }

    @Override
    public void sendSms(String mobile, LinkedHashMap<String, String> params, String signName, String template) {
        SmsSingleSender sender = new SmsSingleSender(config.getQcloudAppId(), config.getQcloudAppKey());

        //短信参数
        ArrayList<String> paramsList = new ArrayList<>();
        if(MapUtils.isNotEmpty(params)){
            for(String value : params.values()){
                paramsList.add(value);
            }
        }
        SmsSingleSenderResult result;
        try {
            result = sender.sendWithParam("86", mobile, Integer.parseInt(template), paramsList, signName, null, null);
        } catch (Exception e) {
            throw new RRException("发送腾讯云短信失败", e);
        }

        int status = SmsService.SUCCESS;
        if(result.result != 0){
            status = SmsService.FAIL;
        }

        //保存短信记录
        SysSmsService sysSmsService = (SysSmsService) SpringContextUtils.getBean("sysSmsService");
        sysSmsService.insert(Constant.SmsService.QCLOUD.getValue(), mobile, params, status);

        if(status == SmsService.FAIL){
            throw new RRException("发送腾讯云短信失败，" + result.errMsg);
        }
    }
}
