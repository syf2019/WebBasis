package com.ims.system.controller;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ims.common.vo.Item;
import com.ims.common.web.BaseController;
import com.ims.system.util.CacheCxt;


@Controller
@RequestMapping("/system/common")
public class CommonController extends BaseController {
	

	/**
	 * 
	 * 简要说明：通过字典键或者字典参数
	 * 编写者：陈骑元
	 * 创建时间：2018年12月16日 上午12:38:30
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping("listItem")
	@ResponseBody
	public List<Item> listItem(String typeCode,String filterCode) {
		 List<Item> itemList= CacheCxt.getItemList(typeCode,filterCode);
		 return itemList;
		
	}

}
