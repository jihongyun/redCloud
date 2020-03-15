package io.redCloud.modules.message.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.redCloud.common.exception.RRException;
import io.redCloud.modules.message.email.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.Query;

import io.redCloud.modules.message.dao.SysMailTemplateDao;
import io.redCloud.modules.message.entity.SysMailTemplateEntity;
import io.redCloud.modules.message.service.SysMailTemplateService;


@Service("sysMailTemplateService")
public class SysMailTemplateServiceImpl extends ServiceImpl<SysMailTemplateDao, SysMailTemplateEntity> implements SysMailTemplateService {
    @Autowired
    private EmailUtils emailUtils;

    @Override
    public LayuiPage queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        Page<SysMailTemplateEntity> page = this.page(
            new Query<SysMailTemplateEntity>(params).getPage(),
            new QueryWrapper<SysMailTemplateEntity>()
                .like(StrUtil.isNotBlank(name),"name", name).orderByDesc("create_date")
        );

        return new LayuiPage(page.getRecords(), page.getTotal());
    }

    @Override
    public boolean sendMail(Long templateId, String mailTo, String mailCc, String params) throws Exception{
        Map<String, Object> map = null;
        try {
            if(StrUtil.isNotEmpty(params)){
                map = JSON.parseObject(params, Map.class);
            }
        }catch (Exception e){
            throw new RRException("参数格式不正确，请使用JSON格式");
        }
        String[] to = new String[]{mailTo};
        String[] cc = StrUtil.isBlank(mailCc) ? null : new String[]{mailCc};

        return emailUtils.sendMail(templateId, to, cc, map);
    }
}
