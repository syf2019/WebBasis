package com.ims.system.controller;

import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.constant.IMSCons;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.util.IMSFormater;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.R;
import com.ims.common.util.SqlHelpUtil;
import com.ims.common.vo.PageDto;
import com.ims.common.vo.TreeModel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ims.system.constant.SystemCons;
import com.ims.system.model.Dept;
import com.ims.system.service.DeptService;
import com.ims.system.util.CacheCxt;

import org.springframework.stereotype.Controller;
import com.ims.common.web.BaseController;

/**
 * <p>
 * 组织机构 前端控制器
 * </p>
 *
 * @author 陈骑元
 * @since 2018-05-14
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    private String prefix = "system/dept/"; 
    @Autowired
    private DeptService deptService;
	/**
	 * 
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:dept:dept")
	@GetMapping("init")
	public String init() {

		return prefix + "deptList";
	}
	/**
	 * 
	 * 加载组织机构树
	 * @param request
	 * @param response
	 */
    
	@RequestMapping(value = "loadDeptTree")
	@ResponseBody
	public List<TreeModel> loadDeptTree(HttpServletRequest request, HttpServletResponse response) {
		Dto pDto=Dtos.newDto(request);
		List<TreeModel> treeModelList=deptService.loadDeptTree(pDto);
		
		return treeModelList;
	}
	
	/**
	 * 
	 * 简要说明：分页查询 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:dept:dept")
	@RequestMapping("list")
	@ResponseBody
	public PageDto list() {
		Dto pDto = Dtos.newDto(request);
		pDto.put("isDel", IMSCons.IS.NO);
		pDto.setOrder(" LENGTH(cascade_id) ASC,sort_no ASC ");
		Page<Dept> page =deptService.likePage(pDto);
		CacheCxt.convertDict(page);
		return new PageDto(page);
	}

	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:dept:add")
	@GetMapping("add")
	public String add(String parentId,Model model) {
		model.addAttribute("parentId", parentId);
		return prefix + "addDept";
	}

	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:dept:add")
	@PostMapping("save")
	@ResponseBody
	public R save(Dept dept) {
		Dto calcDto = Dtos.newCalcDto("MAX(cascade_id)");
		calcDto.put("parentId", dept.getParentId());
		String maxCascadeId =deptService.calc(calcDto);
		if(IMSUtil.isEmpty(maxCascadeId)){
			Dept parentDept=deptService.selectById(dept.getParentId());
			if(IMSUtil.isEmpty(parentDept)){
					maxCascadeId="0.0000";
			}else{
				maxCascadeId=parentDept.getCascadeId()+".0000";
			}
				
		}
		String curCascadeId=IMSUtil.createCascadeId(maxCascadeId, 9999);
		dept.setCascadeId(curCascadeId);
		dept.setCreateTime(IMSUtil.getDateTime());
		dept.setUpdateTime(IMSUtil.getDateTime());
		boolean result = deptService.insert(dept);
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
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:dept:edit")
	@GetMapping("edit")
	public String edit(String id,Model model) {
		Dept dept=deptService.selectById(id);
		String parentId=dept.getParentId();
		if(SystemCons.TREE_ROOT_ID.equals(id)){
			model.addAttribute("parentName", "顶级结构");
		}else{
			Dept parentDept=deptService.selectById(parentId);
			model.addAttribute("parentName", parentDept.getDeptName());
			
		}
		model.addAttribute("dept", dept);
		return prefix + "editDept";
	}
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:dept:edit")
	@PostMapping("update")
	@ResponseBody
	public R update(Dept dept) {
		dept.setUpdateTime(IMSUtil.getDateTime());
		boolean result = deptService.updateById(dept);
		if (result) {
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	/**
	 * 
	 * 简要说明： 移动组织机构
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:dept:move")
	@GetMapping("move")
	public String move(String deptId,Model model) {
		Dept dept=deptService.selectById(deptId);
		model.addAttribute("dept", dept);
		return prefix + "moveDept";
	}
	/**
	 * 
	 * 简要说明：保存移动机构信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	 @RequiresPermissions("system:dept:move")
	@PostMapping("saveMoveDept")
	@ResponseBody
	public R saveMoveDept(Dept dept) {
		boolean result = deptService.updateDept(dept);
		if (result) {
			return R.ok("移动机构成功");
		} else {
			return R.error("移动机构失败");
		}
		
	}
	
	
	/**
	 * 
	 * 简要说明： 展示详情
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("showDetail")
	public String showDetail(String id,Model model) {
		Dept dept=deptService.selectById(id);
		model.addAttribute("dept",dept);
		return prefix + "showDept";
	}
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:dept:remove")
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		EntityWrapper<Dept> wrapper = new EntityWrapper<Dept>();
		SqlHelpUtil.eq(wrapper, "parent_id", id);
		SqlHelpUtil.eq(wrapper, "is_del", IMSCons.IS.NO);
		int row=deptService.selectCount(wrapper);
		if(row>0){
			return R.warn("操作失败，当前组织机构下存在子机构，不允许删除，请先删除子机构然后再删除。");
		}
		Dept dept=new Dept();
		dept.setDeptId(id);
		dept.setIsDel(IMSCons.IS.YES);
		dept.setUpdateTime(IMSUtil.getDateTime());
		boolean result = deptService.updateById(dept);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明：批量删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("batchRemove")
	@ResponseBody
	public R batchRemove(String ids) {
		List<String> idList=IMSFormater.separatStringToList(ids);
		boolean result = deptService.deleteBatchIds(idList);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明：展示机构树
	 * 编写者：陈骑元 
	 * 创建时间：2018-05-14
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("showDeptTree")
	public String showDeptTree(String hiddenId,String showName,Model model) {
        model.addAttribute("hiddenId", hiddenId);
        model.addAttribute("showName", showName);
		return prefix + "deptTree";
	}
	
}

