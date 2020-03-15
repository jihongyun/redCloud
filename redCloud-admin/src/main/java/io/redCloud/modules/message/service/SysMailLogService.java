package io.redCloud.modules.message.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.modules.message.entity.SysMailLogEntity;

import java.util.Map;

/**
 * 邮件发送记录
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-07-23 12:35:17
 */
public interface SysMailLogService extends IService<SysMailLogEntity> {

    LayuiPage queryPage(Map<String, Object> params);

    /**
     * 保存邮件发送记录
     * @param templateId  模板ID
     * @param from        发送者
     * @param to          收件人
     * @param cc          抄送
     * @param subject     主题
     * @param content     邮件正文
     * @param status      状态
     */
    void insert(Long templateId, String from, String[] to, String[] cc, String subject, String content, Integer status);
}

