package io.redCloud.modules.message.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.redCloud.common.exception.RRException;
import io.redCloud.modules.message.sms.SmsFactory;
import io.redCloud.modules.message.sms.SmsService;
import org.apache.commons.collections.MapUtils;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.Query;

import io.redCloud.modules.message.dao.SysSmsDao;
import io.redCloud.modules.message.entity.SysSmsEntity;
import io.redCloud.modules.message.service.SysSmsService;


@Service("sysSmsService")
public class SysSmsServiceImpl extends ServiceImpl<SysSmsDao, SysSmsEntity> implements SysSmsService {

    @Override
    public LayuiPage queryPage(Map<String, Object> params) {
        String mobile = (String)params.get("mobile");
        String status = (String)params.get("status");

        Page<SysSmsEntity> page = this.page(
            new Query<SysSmsEntity>(params).getPage(),
            new QueryWrapper<SysSmsEntity>()
                .like(StrUtil.isNotBlank(mobile),"mobile", mobile)
                .eq(StrUtil.isNotBlank(status),"status", status)
        );

        return new LayuiPage(page.getRecords(), page.getTotal());
    }

    @Override
    public void send(String mobile, String params) {
        LinkedHashMap<String, String> map;
        try {
            map = JSON.parseObject(params, LinkedHashMap.class);
        }catch (Exception e){
            throw new RRException("参数格式不正确，请使用JSON格式");
        }

        //短信服务
        SmsService service = SmsFactory.build();
        if(service == null){
            throw new RRException("请先完成短信配置");
        }

        //发送短信
        service.sendSms(mobile, map);
    }

    @Override
    public void insert(Integer platform, String mobile, LinkedHashMap<String, String> params, Integer status) {
        SysSmsEntity sms = new SysSmsEntity();
        sms.setPlatform(platform);
        sms.setMobile(mobile);

        //设置短信参数
        if(MapUtils.isNotEmpty(params)){
            int index = 1;
            for(String value : params.values()){
                if(index == 1){
                    sms.setParams1(value);
                }else if(index == 2){
                    sms.setParams2(value);
                }else if(index == 3){
                    sms.setParams3(value);
                }else if(index == 4){
                    sms.setParams4(value);
                }
                index++;
            }
        }

        sms.setStatus(status);
        sms.setSendTime(new Date());

        this.save(sms);
    }
}
