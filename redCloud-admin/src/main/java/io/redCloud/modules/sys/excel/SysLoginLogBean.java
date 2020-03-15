package io.redCloud.modules.sys.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 登录日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-07-19 22:57:30
 */
@Data
public class SysLoginLogBean {
    @Excel(name = "ID")
    private Long id;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "用户操作")
    private String operation;
    @Excel(name = "IP地址")
    private String ip;
    @Excel(name = "状态", replace = { "登录成功_0", "登录失败_1", "账号已锁定_2"})
    private Integer status;
    @Excel(name = "登录时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
}
