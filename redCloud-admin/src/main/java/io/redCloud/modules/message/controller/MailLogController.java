package io.redCloud.modules.message.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.redCloud.modules.message.service.SysMailLogService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.R;


/**
 * 邮件发送记录
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-07-23 12:35:17
 */
@RestController
@RequestMapping("sys/maillog")
public class MailLogController {
    @Autowired
    private SysMailLogService sysMailLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:mail:all")
    public LayuiPage list(@RequestParam Map<String, Object> params){
        LayuiPage page = sysMailLogService.queryPage(params);

        return page;
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:mail:all")
    public R delete(@RequestBody Long[] ids){
        sysMailLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}