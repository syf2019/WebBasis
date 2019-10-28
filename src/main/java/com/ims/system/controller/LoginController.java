package com.ims.system.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ims.common.constant.IMSCons;
import com.ims.common.util.R;
import com.ims.common.web.BaseController;
import com.ims.system.constant.SystemCons;
import com.ims.system.service.UserService;
import com.ims.system.util.CacheCxt;



/**
 * 
 * 类名:com.toonan.system.controller.LoginController
 * 描述:登录控制类
 * 编写者:陈骑元
 * 创建时间:2018年12月15日 下午1:57:09
 * 修改说明:
 */
@Controller
public class LoginController extends BaseController{
	
	
	 @Autowired
	 private UserService userService;
	
	 /**
	  * 
	  * 简要说明：登录首页
	  * 编写者：陈骑元
	  * 创建时间：2019年1月9日 上午9:45:40
	  * @param 说明
	  * @return 说明
	  */
	 @GetMapping({ "/", "" })
     public String initLogin(Model model) {
			return "redirect:login";
     }
	  
	 /**
	  * 
	  * 简要说明：get方法跳转首页
	  * 编写者：陈骑元
	  * 创建时间：2019年1月9日 上午9:45:51
	  * @param 说明
	  * @return 说明
	  */
	 @GetMapping("login")
	 public String login() {
		
	     return "login";
	  }
	
	
	/**
	 *
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("login")
	@ResponseBody
	public R login(String account,String password,HttpSession session) {
		
		R r=userService.doLogin(account, password,IMSCons.WHETHER_YES);
		session.setAttribute("token", r.get("token"));
		return r;
	}
	/**
	 * 
	 * 简要说明：跳转到首页
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午8:07:15
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("index")
	public String index() 
	{
		return "index";
	}

	
	/**注销并安全退出系统
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	
	@PostMapping("logout")
	@ResponseBody
	R logout(String token,HttpSession session) {
		CacheCxt.removeToken(token);//移除缓存的token
		session.removeAttribute(SystemCons.TOKEN_PARAM);//移除session中的token
		return R.ok();
	}

	/**
	 * 
	 * 简要说明：403未授权
	 * 编写者：陈骑元
	 * 创建时间：2019年1月9日 上午11:27:43
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("/403")
	String error403() {
		return "403";
	}
	
}
