package io.redCloud.modules.message.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.modules.message.entity.SysMailTemplateEntity;

import java.util.Map;

/**
 * 邮件模板
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-07-23 12:35:17
 */
public interface SysMailTemplateService extends IService<SysMailTemplateEntity> {

    LayuiPage queryPage(Map<String, Object> params);

    /**
     * 发送邮件
     * @param templateId   邮件模板ID
     * @param mailTo       收件人
     * @param mailCc       抄送
     * @param params       模板参数
     */
    boolean sendMail(Long templateId, String mailTo, String mailCc, String params) throws Exception;
}

