

package io.redCloud.modules.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.redCloud.common.exception.RRException;
import io.redCloud.common.utils.Constant;
import io.redCloud.common.utils.IPUtils;
import io.redCloud.common.utils.R;
import io.redCloud.modules.sys.entity.SysLoginLogEntity;
import io.redCloud.modules.sys.service.SysLoginLogService;
import io.redCloud.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

/**
 * 登录相关
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
public class SysLoginController {
	@Autowired
	private Producer producer;
	@Autowired
	private SysLoginLogService sysLoginLogService;

	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);

        //保存到session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}

	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public R login(HttpServletRequest request, String username, String password, String captcha) {
		String kaptcha = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(StrUtil.isBlank(kaptcha)){
			throw new RRException("验证码已失效", 501);
		}

		if(!captcha.equalsIgnoreCase(kaptcha)){
			request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
			return R.error(501, "验证码不正确");
		}

		SysLoginLogEntity log = new SysLoginLogEntity();
		log.setCreateDate(new Date());
		log.setIp(IPUtils.getIpAddr(request));
		log.setOperation("用户登录");
		log.setUsername(username);

		R result = R.ok();
		try{
			Subject subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);

			log.setStatus(Constant.LoginLogStatus.SUCCESS.getValue());
		}catch (UnknownAccountException e) {
			log.setStatus(Constant.LoginLogStatus.FAIL.getValue());
			result = R.error(500,e.getMessage());
		}catch (IncorrectCredentialsException e) {
			log.setStatus(Constant.LoginLogStatus.FAIL.getValue());
			result = R.error(502,"账号或密码不正确");
		}catch (LockedAccountException e) {
			log.setStatus(Constant.LoginLogStatus.LOCK.getValue());
			result = R.error(503,"账号已被锁定,请联系管理员");
		}catch (AuthenticationException e) {
			log.setStatus(Constant.LoginLogStatus.FAIL.getValue());
			result = R.error(504,"账户验证失败");
		}

		//保存登录日志
		sysLoginLogService.save(log);

		return result;
	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}

}
