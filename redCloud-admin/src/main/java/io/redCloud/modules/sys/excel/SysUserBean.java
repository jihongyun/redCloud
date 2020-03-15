package io.redCloud.modules.sys.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户Excel Bean
 */
@Data
public class SysUserBean {
    @Excel(name = "用户ID")
    private Long userId;

    @Excel(name = "用户名")
    private String username;

    @Excel(name = "邮箱")
    private String email;

    @Excel(name = "手机号")
    private String mobile;

    @Excel(name = "部门ID")
    private Long deptId;

    @Excel(name = "部门名称")
    private String deptName;

    @Excel(name = "状态", replace = { "禁用_0", "正常_1" })
    private Integer status;

    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
