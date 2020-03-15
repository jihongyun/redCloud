package io.redCloud.modules.sys.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 系统日志Excel Bean
 */
@Data
public class SysLogBean {
    @Excel(name = "ID")
    private Long id;

    @Excel(name = "用户名")
    private String username;

    @Excel(name = "用户操作")
    private String operation;

    @Excel(name = "请求方法")
    private String method;

    @Excel(name = "请求参数")
    private String params;

    @Excel(name = "执行时长(毫秒)")
    private Long time;

    @Excel(name = "IP地址")
    private String ip;

    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

}
