package io.redCloud.modules.message.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.Query;

import io.redCloud.modules.message.dao.SysMailLogDao;
import io.redCloud.modules.message.entity.SysMailLogEntity;
import io.redCloud.modules.message.service.SysMailLogService;


@Service("sysMailLogService")
public class SysMailLogServiceImpl extends ServiceImpl<SysMailLogDao, SysMailLogEntity> implements SysMailLogService {

    @Override
    public LayuiPage queryPage(Map<String, Object> params) {
        String templateId = (String)params.get("templateId");
        String mailTo = (String)params.get("mailTo");
        String status = (String)params.get("status");

        Page<SysMailLogEntity> page = this.page(
            new Query<SysMailLogEntity>(params).getPage(),
            new QueryWrapper<SysMailLogEntity>()
                .eq(StrUtil.isNotBlank(templateId),"template_id", templateId)
                .eq(StrUtil.isNotBlank(status),"status", status)
                .like(StrUtil.isNotBlank(mailTo),"mail_to", mailTo).orderByDesc("send_time")
        );

        return new LayuiPage(page.getRecords(), page.getTotal());
    }

    @Override
    public void insert(Long templateId, String from, String[] to, String[] cc, String subject, String content, Integer status) {
        SysMailLogEntity log = new SysMailLogEntity();
        log.setTemplateId(templateId);
        log.setMailFrom(from);
        log.setMailTo(JSON.toJSONString(to));
        if(cc != null){
            log.setMailCc(JSON.toJSONString(cc));
        }
        log.setSubject(subject);
        log.setContent(content);
        log.setStatus(status);
        log.setSendTime(new Date());
        this.save(log);
    }

}
