package com.ims.system.controller;

import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.util.IMSFormater;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.R;
import com.ims.common.util.SqlHelpUtil;
import com.ims.common.vo.PageDto;

import java.util.List;

import com.ims.system.constant.SystemCons;
import com.ims.system.model.Param;
import com.ims.system.service.ParamService;
import com.ims.system.util.CacheCxt;

import org.springframework.stereotype.Controller;
import com.ims.common.web.BaseController;

/**
 * <p>
 * 键值参数 前端控制器
 * </p>
 *
 * @author 陈骑元
 * @since 2018-04-12
 */
@Controller
@RequestMapping("/system/param")
public class ParamController extends BaseController {
	
    private String prefix = "system/param/"; 
    @Autowired
    private ParamService paramService;
	/**
	 * 
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:param:param")
	@GetMapping("init")
	public String init() {
		return prefix + "paramList";
	}

	/**
	 * 
	 * 简要说明：分页查询 
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:param:param")
	@RequestMapping("list")
	@ResponseBody
	public PageDto list() {
		Dto pDto = Dtos.newDto(request);
		pDto.setOrder("create_time DESC");
		Page<Param> page =paramService.likePage(pDto);
		CacheCxt.convertDict(page);
		return new PageDto(page);
	}

	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:param:add")
	@GetMapping("add")
	public String add() {

		return prefix + "addParam";
	}

	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:param:add")
	@PostMapping("save")
	@ResponseBody
	public R save(Param param) {
		EntityWrapper<Param> countWrapper = new EntityWrapper<Param>();
		SqlHelpUtil.eq(countWrapper, "param_key", param.getParamKey());
		int count=paramService.selectCount(countWrapper);
		if(count>0){
			return R.warn("参数键已被占用，请修改其它参数键再保存。");
		}
		param.setCreateTime(IMSUtil.getDateTime());
		param.setUpdateTime(IMSUtil.getDateTime());
		boolean result = paramService.insert(param);
		if (result) {
			return R.ok();
		} else {
			return R.error("保存失败");
		}

	}
	/**
	 * 
	 * 简要说明： 跳转到编辑页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:param:edit")
	@GetMapping("edit")
	public String edit(String id,Model model) {
		Param param=paramService.selectById(id);
		model.addAttribute("paramModel", param);
		return prefix + "editParam";
	}
	
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:param:edit")
	@PostMapping("update")
	@ResponseBody
	public R update(Param param,String oldParamKey) {
		
		if(IMSUtil.isNotEmpty(oldParamKey)){
			if(!oldParamKey.equals(param.getParamKey())){
				EntityWrapper<Param> countWrapper = new EntityWrapper<Param>();
				SqlHelpUtil.eq(countWrapper, "param_key", param.getParamKey());
				int count=paramService.selectCount(countWrapper);
				if(count>0){
					return R.warn("参数键已被占用，请修改其它参数键再保存。");
				}
				
			}
		}
		param.setUpdateTime(IMSUtil.getDateTime());
		boolean result = paramService.updateById(param);
		if (result) {
			CacheCxt.flushParam();
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明： 展示详情
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("showDetail")
	public String showDetail(String id,Model model) {
		Param param=paramService.selectById(id);
		model.addAttribute("param",param);
		return prefix + "showparam";
	}
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:param:remove")
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		Param param=paramService.selectById(id);
		if(SystemCons.EDITMODE_READ.equals(param.getEditMode())	){
			
			return R.warn("当前删除的键值参数数据为只读，只读的数据不能修改和删除");
		}
		boolean result = paramService.deleteById(id);
		if (result) {
			CacheCxt.flushParam();
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明：批量删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("batchRemove")
	@ResponseBody
	public R batchRemove(String ids) {
		List<String> idList=IMSFormater.separatStringToList(ids);
		boolean result = paramService.deleteBatchIds(idList);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明：刷新键值参数缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月13日 下午11:09:04
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:param:refreshCache")
	@PostMapping("refreshCache")
	@ResponseBody
	public R refreshParam() {
		CacheCxt.flushParam();
	    
		return R.ok("刷新键值参数缓存操作成功");
	}
	/**
	 * 
	 * 简要说明：清空键值参数缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月13日 下午11:09:04
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("flushParam")
	@ResponseBody
	public R flushParam() {
		
		CacheCxt.flushParam();
		
		return R.ok("清空键值参数缓存操作成功");
	}
	
}

