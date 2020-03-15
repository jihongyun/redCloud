

package io.redCloud.modules.message.email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 邮件配置信息
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-22
 */
public class EmailConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * SMTP
     */
    @NotBlank(message="SMTP不能为空")
    private String smtp;
    /**
     * 端口号
     */
    @NotNull(message="端口号不能为空")
    private Integer port;
    /**
     * 邮箱账号
     */
    @NotBlank(message="邮箱账号不能为空")
    private String username;
    /**
     * 邮箱密码
     */
    @NotBlank(message="邮箱密码不能为空")
    private String password;

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
