package io.redCloud.modules.sys.controller;

import com.sun.management.OperatingSystemMXBean;
import io.redCloud.common.utils.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * 系统页面视图
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月24日 下午11:05:27
 */
@Controller
public class SysPageController {

	@RequestMapping("modules/{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url){
		return "modules/" + module + "/" + url;
	}

	@RequestMapping(value = {"/", "index.html"})
	public String index(){
		return "index";
	}

	@RequestMapping("login.html")
	public String login(){
		return "login";
	}

	@RequestMapping("home.html")
	public String main(Model model){
		OperatingSystemMXBean osmx = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();

		model.addAttribute("sysTime", DateUtils.format(new Date(), "yyyy年MM月dd日 HH:mm:ss"));
		model.addAttribute("osName", System.getProperty("os.name"));
		model.addAttribute("osArch", System.getProperty("os.arch"));
		model.addAttribute("osVersion", System.getProperty("os.version"));
		model.addAttribute("userLanguage", System.getProperty("user.language"));
		model.addAttribute("userDir", System.getProperty("user.dir"));
		model.addAttribute("totalPhysical", osmx.getTotalPhysicalMemorySize()/1024/1024);
		model.addAttribute("freePhysical", osmx.getFreePhysicalMemorySize()/1024/1024);
		model.addAttribute("memoryRate", (1-osmx.getFreePhysicalMemorySize()*1.0/osmx.getTotalPhysicalMemorySize())*100);
		model.addAttribute("processors", osmx.getAvailableProcessors());
		model.addAttribute("jvmName", System.getProperty("java.vm.name"));
		model.addAttribute("javaVersion", System.getProperty("java.version"));
		model.addAttribute("javaHome", System.getProperty("java.home"));
		model.addAttribute("javaTotalMemory", Runtime.getRuntime().totalMemory()/1024/1024);
		model.addAttribute("javaFreeMemory", Runtime.getRuntime().freeMemory()/1024/1024);
		model.addAttribute("javaMaxMemory", Runtime.getRuntime().maxMemory()/1024/1024);
		model.addAttribute("userName", System.getProperty("user.name"));
		model.addAttribute("systemCpuLoad", osmx.getSystemCpuLoad()*100);
		model.addAttribute("userTimezone", System.getProperty("user.timezone"));

		return "home";
	}

	@RequestMapping("404.html")
	public String notFound(){
		return "404";
	}

}
