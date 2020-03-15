package io.redCloud.modules.message.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import io.redCloud.common.utils.ConfigConstant;
import io.redCloud.common.validator.ValidatorUtils;
import io.redCloud.common.xss.XssHttpServletRequestWrapper;
import io.redCloud.modules.message.email.EmailConfig;
import io.redCloud.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.redCloud.modules.message.entity.SysMailTemplateEntity;
import io.redCloud.modules.message.service.SysMailTemplateService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 邮件模板
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-07-23 12:35:17
 */
@RestController
@RequestMapping("sys/mailtemplate")
public class MailTemplateController {
    @Autowired
    private SysMailTemplateService sysMailTemplateService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.MAIL_CONFIG_KEY;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:mail:all")
    public LayuiPage list(@RequestParam Map<String, Object> params){
        LayuiPage page = sysMailTemplateService.queryPage(params);

        return page;
    }

    /**
     * 邮件模板配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:sms:all")
    public R<EmailConfig> config(){
        EmailConfig config = sysConfigService.getConfigObject(KEY, EmailConfig.class);

        return R.ok(config);
    }

    /**
     * 保存邮件模板配置信息
     */
    @RequestMapping("/saveConfig")
    @RequiresPermissions("sys:mail:all")
    public R saveConfig(@RequestBody EmailConfig config){
        //校验数据
        ValidatorUtils.validateEntity(config);

        sysConfigService.updateValueByKey(KEY, JSON.toJSONString(config));

        return R.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:mail:all")
    public R<SysMailTemplateEntity> info(@PathVariable("id") Long id){
        SysMailTemplateEntity sysMailTemplate = sysMailTemplateService.getById(id);

        return R.ok(sysMailTemplate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:mail:all")
    public R save(HttpServletRequest request){
        HttpServletRequest orgRequest = XssHttpServletRequestWrapper.getOrgRequest(request);
        String name = orgRequest.getParameter("name");
        String subject = orgRequest.getParameter("subject");
        String content = orgRequest.getParameter("content");

        SysMailTemplateEntity template = new SysMailTemplateEntity();
        template.setName(name);
        template.setSubject(subject);
        template.setContent(content);
        template.setCreateDate(new Date());

        sysMailTemplateService.save(template);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:mail:all")
    public R update(HttpServletRequest request){
        HttpServletRequest orgRequest = XssHttpServletRequestWrapper.getOrgRequest(request);
        String name = orgRequest.getParameter("name");
        String subject = orgRequest.getParameter("subject");
        String content = orgRequest.getParameter("content");
        String id = orgRequest.getParameter("id");

        SysMailTemplateEntity template = new SysMailTemplateEntity();
        template.setId(Long.parseLong(id));
        template.setName(name);
        template.setSubject(subject);
        template.setContent(content);

        sysMailTemplateService.updateById(template);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:mail:all")
    public R delete(@RequestBody Long[] ids){
        sysMailTemplateService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 发送邮件
     */
    @RequestMapping("/send")
    @RequiresPermissions("sys:mail:all")
    public R send(Long templateId, String mailTo, String mailCc, String params) throws Exception{
        boolean flag = sysMailTemplateService.sendMail(templateId, mailTo, mailCc, params);
        if(flag){
            return R.ok();
        }

        return R.error(500,"邮件发送失败");
    }

}
