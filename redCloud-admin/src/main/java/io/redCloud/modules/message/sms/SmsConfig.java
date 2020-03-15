

package io.redCloud.modules.message.sms;

import io.redCloud.common.validator.group.AliyunGroup;
import io.redCloud.common.validator.group.QcloudGroup;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 短信配置信息
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-22
 */
public class SmsConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 平台 1：阿里云  2：腾讯云
     */
    @Range(min=1, max=2, message = "平台类型错误")
    private Integer platform;
    /**
     * 阿里云AccessKeyId
     */
    @NotBlank(message="阿里云AccessKeyId不能为空", groups = AliyunGroup.class)
    private String aliyunAccessKeyId;
    /**
     * 阿里云AccessKeySecret
     */
    @NotBlank(message="阿里云AccessKeySecret不能为空", groups = AliyunGroup.class)
    private String aliyunAccessKeySecret;
    /**
     * 阿里云短信签名
     */
    @NotBlank(message="阿里云短信签名不能为空", groups = AliyunGroup.class)
    private String aliyunSignName;
    /**
     * 阿里云短信模板
     */
    @NotBlank(message="阿里云短信模板不能为空", groups = AliyunGroup.class)
    private String aliyunTemplateCode;

    /**
     * 腾讯云AppId
     */
    @NotNull(message="腾讯云AppId不能为空", groups = QcloudGroup.class)
    private Integer qcloudAppId;
    /**
     * 腾讯云AppKey
     */
    @NotBlank(message="腾讯云AppKey不能为空", groups = QcloudGroup.class)
    private String qcloudAppKey;
    /**
     * 腾讯云短信签名
     */
    @NotBlank(message="腾讯云短信签名不能为空", groups = QcloudGroup.class)
    private String qcloudSignName;
    /**
     * 腾讯云短信模板ID
     */
    @NotBlank(message="腾讯云短信模板ID不能为空", groups = QcloudGroup.class)
    private String qcloudTemplateId;

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getAliyunAccessKeyId() {
        return aliyunAccessKeyId;
    }

    public void setAliyunAccessKeyId(String aliyunAccessKeyId) {
        this.aliyunAccessKeyId = aliyunAccessKeyId;
    }

    public String getAliyunAccessKeySecret() {
        return aliyunAccessKeySecret;
    }

    public void setAliyunAccessKeySecret(String aliyunAccessKeySecret) {
        this.aliyunAccessKeySecret = aliyunAccessKeySecret;
    }

    public String getAliyunSignName() {
        return aliyunSignName;
    }

    public void setAliyunSignName(String aliyunSignName) {
        this.aliyunSignName = aliyunSignName;
    }

    public String getAliyunTemplateCode() {
        return aliyunTemplateCode;
    }

    public void setAliyunTemplateCode(String aliyunTemplateCode) {
        this.aliyunTemplateCode = aliyunTemplateCode;
    }

    public String getQcloudAppKey() {
        return qcloudAppKey;
    }

    public void setQcloudAppKey(String qcloudAppKey) {
        this.qcloudAppKey = qcloudAppKey;
    }

    public String getQcloudSignName() {
        return qcloudSignName;
    }

    public void setQcloudSignName(String qcloudSignName) {
        this.qcloudSignName = qcloudSignName;
    }

    public Integer getQcloudAppId() {
        return qcloudAppId;
    }

    public void setQcloudAppId(Integer qcloudAppId) {
        this.qcloudAppId = qcloudAppId;
    }

    public String getQcloudTemplateId() {
        return qcloudTemplateId;
    }

    public void setQcloudTemplateId(String qcloudTemplateId) {
        this.qcloudTemplateId = qcloudTemplateId;
    }
}
