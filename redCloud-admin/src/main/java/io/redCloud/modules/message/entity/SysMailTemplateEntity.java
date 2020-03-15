package io.redCloud.modules.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件模板
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-07-23 12:35:17
 */
@Data
@TableName("sys_mail_template")
public class SysMailTemplateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 模板ID
	 */
	@TableId
	private Long id;
	/**
	 * 模板名称
	 */
	private String name;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件正文
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Date createDate;

}
