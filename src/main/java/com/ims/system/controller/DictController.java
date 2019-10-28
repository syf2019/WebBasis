package com.ims.system.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.util.IMSFormater;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.Query;
import com.ims.common.util.R;
import com.ims.common.util.SqlHelpUtil;
import com.ims.common.vo.PageDto;

import java.util.List;

import com.ims.system.constant.SystemCons;
import com.ims.system.model.Dict;
import com.ims.system.model.DictIndex;
import com.ims.system.service.DictIndexService;
import com.ims.system.service.DictService;
import com.ims.system.util.CacheCxt;

import org.springframework.stereotype.Controller;
import com.ims.common.web.BaseController;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author 陈骑元
 * @since 2018-05-01
 */
@Controller
@RequestMapping("/system/dict")
public class DictController extends BaseController {
	
    private String prefix = "system/dict/"; 
    @Autowired
    private DictService dictService;
    @Autowired
    private DictIndexService dictIndexService;
	/**
	 * 
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("init")
	public String init() {

		return prefix + "dictList";
	}

	/**
	 * 
	 * 简要说明：分页查询 字典类型
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping("listDictIndex")
	@ResponseBody
	public PageDto listDictIndex() {
		Dto pDto = Dtos.newDto(request);
		Query<DictIndex> query=new Query<DictIndex>(pDto);
		EntityWrapper<DictIndex> wrapper = new EntityWrapper<DictIndex>();
	    SqlHelpUtil.like(wrapper, "dict_key", pDto,"dictKey");
	    SqlHelpUtil.like(wrapper, "dict_name", pDto,"dictName");
		wrapper.orderBy("create_time", false);
		Page<DictIndex> page=dictIndexService.selectPage( query,wrapper);
		CacheCxt.convertDict(page);
		return new PageDto(page);
	}
	
	/**
	 * 
	 * 简要说明：分页查询 字典类型
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping("listDict")
	@ResponseBody
	public PageDto listDict() {
		Dto pDto = Dtos.newDto(request);
		Query<Dict> query=new Query<Dict>(pDto);
		EntityWrapper<Dict> wrapper = new EntityWrapper<Dict>();
		SqlHelpUtil.eq(wrapper, "dict_index_id", pDto,"dictIndexId");
		SqlHelpUtil.like(wrapper, "dict_code", pDto,"dictCode");
		SqlHelpUtil.like(wrapper, "dict_value", pDto,"dictValue");
		wrapper.orderBy("sort_no", true);
		Page<Dict> page =dictService.selectPage(query,wrapper);
		//转换字典标签
		CacheCxt.convertDict(page);
		return new PageDto(page);
	}

	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("addDictIndex")
	public String addDictIndex() {

		return prefix + "addDictIndex";
	}

	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("saveDictIndex")
	@ResponseBody
	public R saveDictIndex(DictIndex dictIndex) {
		EntityWrapper<DictIndex> countWrapper = new EntityWrapper<DictIndex>();
		SqlHelpUtil.eq(countWrapper, "dict_key", dictIndex.getDictKey());
		int count=dictIndexService.selectCount(countWrapper);
		if(count>0){
			return R.warn("字典标识已被占用，请修改其它字典标识再保存。");
		}
		dictIndex.setCreateTime(IMSUtil.getDateTime());
		dictIndex.setUpdateTime(IMSUtil.getDateTime());
		boolean result = dictIndexService.insert(dictIndex);
		if (result) {
			return R.ok();
		} else {
			return R.error("保存失败");
		}

	}
	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("addDict")
	public ModelAndView addDict(String id ) {
		ModelAndView modelAndView=new ModelAndView();
		DictIndex dictIndex=dictIndexService.selectById(id);
		modelAndView.addObject("dictIndex", dictIndex);
		modelAndView.setViewName(prefix + "addDict");
		return modelAndView;
	}
	
	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("saveDict")
	@ResponseBody
	public R saveDict(Dict dict) {
		EntityWrapper<Dict> countWrapper = new EntityWrapper<Dict>();
		SqlHelpUtil.eq(countWrapper, "dict_index_id", dict.getDictIndexId());
		SqlHelpUtil.eq(countWrapper, "dict_code", dict.getDictCode());
		int count=dictService.selectCount(countWrapper);
		if(count>0){
			return R.warn("字典对照码已被占用，请修改其它字典对照码再保存。");
		}
		dict.setCreateTime(IMSUtil.getDateTime());
		dict.setUpdateTime(IMSUtil.getDateTime());
		boolean result = dictService.insert(dict);
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
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("editDictIndex")
	public String editDictIndex(String id,Model model) {
		DictIndex dictIndex=dictIndexService.selectById(id);
		model.addAttribute("dictIndex", dictIndex);
		return prefix + "editDictIndex";
	}
	/**
	 * 
	 * 简要说明： 跳转到编辑页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("editDict")
	public String editDict(String id,Model model) {
		Dict dict=dictService.selectById(id);
		DictIndex dictIndex=dictIndexService.selectById(dict.getDictIndexId());
		model.addAttribute("dictIndex", dictIndex);
		model.addAttribute("dict", dict);
		return prefix + "editDict";
	}
	
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("updateDictIndex")
	@ResponseBody
	public R updateDictIndex(DictIndex dictIndex,String oldDictKey) {
		if(!oldDictKey.equals(dictIndex.getDictKey())){ //如果新的字典对照码改变，则进行校验
			EntityWrapper<DictIndex> countWrapper = new EntityWrapper<DictIndex>();
			SqlHelpUtil.eq(countWrapper, "dict_key", dictIndex.getDictKey());
			int count=dictIndexService.selectCount(countWrapper);
			if(count>0){
				return R.warn("字典对照码已被占用，请修改其它字典对照码再保存。");
			}
			CacheCxt.removeCacheDict(oldDictKey);  //移除旧的字典缓存
		}
		dictIndex.setUpdateTime(IMSUtil.getDateTime());
		boolean result = dictIndexService.updateById(dictIndex);
		if (result) {
			CacheCxt.flushDict();
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("updateDict")
	@ResponseBody
	public R updateDict(Dict dict,String oldDictCode) {
		String dictCode=dict.getDictCode();
		if(IMSUtil.isNotEmpty(dictCode)){
			if(!oldDictCode.equals(dictCode)){  //新的字典对照码不等于旧的对照码，则校验重复
				EntityWrapper<Dict> countWrapper = new EntityWrapper<Dict>();
				SqlHelpUtil.eq(countWrapper, "dict_index_id", dict.getDictIndexId());
				SqlHelpUtil.eq(countWrapper, "dict_code", dict.getDictCode());
				int count=dictService.selectCount(countWrapper);
				if(count>0){
					return R.warn("字典对照码已被占用，请修改其它字典对照码再保存。");
				}
			}	
		}
		dict.setUpdateTime(IMSUtil.getDateTime());
		boolean result = dictService.updateById(dict);
		if (result) {
			CacheCxt.flushDict();
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明： 展示详情
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("showDetail")
	public String showDetail(String id,Model model) {
		Dict dict=dictService.selectById(id);
		model.addAttribute("dict",dict);
		return prefix + "showdict";
	}
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("removeDictIndex")
	@ResponseBody
	public R removeDictIndex(String id) {
		EntityWrapper<Dict> wrapper =new EntityWrapper<Dict>();
		wrapper.eq("edit_mode", SystemCons.EDITMODE_READ);
		wrapper.eq("dict_index_id",id);
		int count=dictService.selectCount(wrapper);
		if(count>0){
			
			return R.warn("当前字典标识下存在只读的字典对照数据，只读的数据不允许修改和删除。");
		}
		Dto delDto=Dtos.newDto("dict_index_id", id);
		dictService.deleteByMap(delDto);
		boolean result = dictIndexService.deleteById(id);
		if (result) {
			CacheCxt.flushDict();
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	@PostMapping("removeDict")
	@ResponseBody
	public R removeDict(String id) {
	
	    Dict dict=dictService.selectById(id);
		if(SystemCons.EDITMODE_READ.equals(dict.getEditMode())){
			
			return R.warn("删除字典对照码中存在只读的字典对照数据，只读的数据不允许修改和删除。");
		}
		boolean result = dictService.deleteById(id);
		if (result) {
			CacheCxt.flushDict();
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明：批量删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-01
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("batchRemoveDict")
	@ResponseBody
	public R batchRemoveDict(String ids) {
	
		List<String> idList=IMSFormater.separatStringToList(ids);
		List<Dict> dictList=dictService.selectBatchIds(idList);
		for(Dict dict:dictList){
			if(SystemCons.EDITMODE_READ.equals(dict.getEditMode())){
				
				return R.warn("删除字典对照码中存在只读的字典对照数据，只读的数据不允许修改和删除。");
			}
		}
		boolean result = dictService.deleteBatchIds(idList);
		if (result) {
			CacheCxt.flushDict();
			return R.ok();
		} else {
			return R.error("删除失败");
		}
	}
	/**
	 * 
	 * 简要说明：刷新字典缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月13日 下午11:09:04
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("refreshDict")
	@ResponseBody
	public R refreshDict() {
		CacheCxt.cacheAllDict();
	    
		return R.ok("刷新字典缓存操作成功");
	}
	/**
	 * 
	 * 简要说明：清空字典缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月13日 下午11:09:04
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("flushDict")
	@ResponseBody
	public R flushDict() {
		
		CacheCxt.flushDict();
		
		return R.ok("刷新字典缓存操作成功");
	}
}

