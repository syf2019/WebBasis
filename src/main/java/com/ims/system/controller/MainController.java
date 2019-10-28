package com.ims.system.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ims.common.constant.IMSCons;
import com.ims.common.util.R;
import com.ims.common.vo.TreeModel;
import com.ims.common.web.BaseController;
import com.ims.system.constant.SystemCons;
import com.ims.system.model.User;
import com.ims.system.util.CacheCxt;


/**
 * 
 * 类名:com.ims.system.controller.SystemController
 * 描述:系统控制类
 * 编写者:陈骑元
 * 创建时间:2018年5月4日 下午9:45:59
 * 修改说明:
 */
@Controller
@RequestMapping("/system/main")
public class MainController extends BaseController {
	
	 private String prefix = "system/main/"; 
	

	 
	 
	 /**
		 * 
		 * 简要说明：初始化主页面
		 * 编写者：陈骑元 
		 * 创建时间：2018-05-01
		 * @param 说明
		 * @return 说明
		 */
		@RequestMapping("initMain")
		public String initMain(String menuType,Model model) {
			User user=this.getTokenUser();
			String whetherSuper=IMSCons.WHETHER_NO;
			if(SystemCons.SUPER_ADMIN.equals(user.getAccount())){
				whetherSuper=IMSCons.WHETHER_YES;
			}
			List<TreeModel> cardMenuList=CacheCxt.getCardMenu(user.getUserId(),menuType,whetherSuper);
			model.addAttribute("user", user);
			model.addAttribute("menuList", cardMenuList);//获取菜单树
			return prefix+"main";
		}
		
		
		/**
		 * 
		 * 简要说明：初始化主页面
		 * 编写者：陈骑元 
		 * 创建时间：2018-05-01
		 * @param 说明
		 * @return 说明
		 */
		@GetMapping("mainIndex")
		public String mainIndex() {
			
			return prefix + "index";
		}
		
		/**
		 * 
		 * 简要说明：解锁屏幕
		 * 编写者：陈骑元 
		 * 创建时间：2018-05-01
		 * @param 说明
		 * @return 说明
		 */
		@PostMapping("unlockScreen")
		@ResponseBody
		public R unlockScreen(String password) {
			
			User user=this.getTokenUser();
			if(user.getPassword().equals(password)){
				
				return R.ok();
			}
			return R.warn("登陆密码不正确，无法进行解锁");
		}


}
