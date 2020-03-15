package io.redCloud.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-07-19 22:57:30
 */
@Data
@TableName("sys_login_log")
public class SysLoginLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户操作
	 */
	private String operation;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 状态  0：登录成功  1：登录失败  2：账号已锁定
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createDate;

}
