package io.redCloud.modules.sys.controller;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.redCloud.modules.sys.excel.SysLoginLogBean;
import io.redCloud.modules.sys.service.SysLoginLogService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.ExcelUtils;


/**
 * 登录日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-07-19 22:57:30
 */
@RestController
@RequestMapping("sys/loginlog")
public class SysLoginLogController {
    @Autowired
    private SysLoginLogService sysLoginLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:loginlog:list")
    public LayuiPage list(@RequestParam Map<String, Object> params){
        LayuiPage page = sysLoginLogService.queryPage(params);

        return page;
    }

    /**
     * 导出
    */
    @RequestMapping("/export")
    @RequiresPermissions("sys:loginlog:list")
    public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        params.put("page", "1");
        params.put("limit", Integer.MAX_VALUE+"");
        LayuiPage page = sysLoginLogService.queryPage(params);

        ExcelUtils.exportExcelToTarget(response, "登录日志", page.getData(), SysLoginLogBean.class);
    }

}
