

package io.redCloud.modules.sys.controller;

import io.redCloud.common.utils.ExcelUtils;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.modules.sys.excel.SysLogBean;
import io.redCloud.modules.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * 系统日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:log:list")
	public LayuiPage list(@RequestParam Map<String, Object> params){
		LayuiPage page = sysLogService.queryPage(params);

		return page;
	}

	/**
	 * 导出
	 */
	@RequestMapping("/export")
	@RequiresPermissions("sys:log:list")
	public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
		LayuiPage page = sysLogService.queryPage(params);

		ExcelUtils.exportExcelToTarget(response, "系统日志", page.getData(), SysLogBean.class);
	}
}
