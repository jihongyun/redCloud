

package io.redCloud.modules.message.email;

import freemarker.template.Template;
import io.redCloud.common.exception.RRException;
import io.redCloud.common.utils.ConfigConstant;
import io.redCloud.modules.message.entity.SysMailTemplateEntity;
import io.redCloud.modules.message.service.SysMailLogService;
import io.redCloud.modules.message.service.SysMailTemplateService;
import io.redCloud.modules.sys.service.SysConfigService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件工具类
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-23
 */
@Component
public class EmailUtils {
    /**
     * 发送成功
     */
    public final static int SUCCESS = 0;
    /**
     * 发送失败
     */
    public final static int FAIL = 1;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    @Lazy
    private SysMailTemplateService sysMailTemplateService;
    @Autowired
    private SysMailLogService sysMailLogService;

    private final static String KEY = ConfigConstant.MAIL_CONFIG_KEY;

    private JavaMailSenderImpl createMailSender(EmailConfig config) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(config.getSmtp());
        sender.setPort(config.getPort());
        sender.setUsername(config.getUsername());
        sender.setPassword(config.getPassword());
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "10000");
        p.setProperty("mail.smtp.auth", "false");
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 发送邮件
     * @param templateId   模板ID
     * @param to        收件人
     * @param cc        抄送
     * @param params   模板参数
     * @return true：成功   false：失败
     */
    public boolean sendMail(Long templateId, String[] to, String[] cc, Map<String, Object> params) throws Exception {
        SysMailTemplateEntity template = sysMailTemplateService.getById(templateId);
        if(template == null){
            throw new RRException("邮件模板不存在");
        }

        EmailConfig config = sysConfigService.getConfigObject(KEY, EmailConfig.class);
        JavaMailSenderImpl mailSender = createMailSender(config);
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        //设置utf-8编码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(config.getUsername());

        //收件人
        messageHelper.setTo(to);
        //抄送
        if(cc != null && cc.length > 0){
            messageHelper.setCc(cc);
        }
        //主题
        messageHelper.setSubject(template.getSubject());

        //邮件正文
        String content = getFreemarkerContent(template.getContent(), params);
        messageHelper.setText(content, true);

        int status = SUCCESS;
        //发送邮件
        try {
            mailSender.send(mimeMessage);
        }catch (Exception e){
            status = FAIL;
            logger.error("send error", e);
        }

        sysMailLogService.insert(templateId, config.getUsername(), to, cc, template.getSubject(), content, status);

        return status == SUCCESS;
    }

    /**
     * 获取Freemarker渲染后的内容
     * @param content   模板内容
     * @param params    参数
     */
    private String getFreemarkerContent(String content, Map<String, Object> params) throws Exception {
        if(MapUtils.isEmpty(params)){
            return content;
        }

        //模板
        StringReader reader = new StringReader(content);
        Template template = new Template("mail", reader, null, "utf-8");

        //渲染模板
        StringWriter sw = new StringWriter();
        template.process(params, sw);

        content = sw.toString();
        IOUtils.closeQuietly(sw);

        return content;
    }

    /**
     * 发送邮件
     * @param to        收件人
     * @param cc        抄送
     * @param subject   主题
     * @param content   邮件正文
     * @return true：成功   false：失败
     */
    public boolean sendMail(String[] to, String[] cc, String subject, String content) throws Exception {
        EmailConfig config = sysConfigService.getConfigObject(KEY, EmailConfig.class);
        JavaMailSenderImpl mailSender = createMailSender(config);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //设置utf-8编码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(config.getUsername());

        //收件人
        messageHelper.setTo(to);
        //抄送
        if(cc != null && cc.length > 0){
            messageHelper.setCc(cc);
        }
        //主题
        messageHelper.setSubject(subject);
        //邮件正文
        messageHelper.setText(content, true);

        int status = SUCCESS;
        //发送邮件
        try {
            mailSender.send(mimeMessage);
        }catch (Exception e){
            status = FAIL;
            logger.error("send error", e);
        }

        sysMailLogService.insert(0L, config.getUsername(), to, cc, subject, content, status);

        return status == SUCCESS;
    }

}
