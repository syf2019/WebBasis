package com.ims.system.controller;

import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.util.IMSFormater;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.R;
import com.ims.common.vo.PageDto;
import com.ims.common.vo.TreeModel;

import java.util.List;

import com.ims.system.constant.SystemCons;
import com.ims.system.model.Role;
import com.ims.system.model.User;
import com.ims.system.service.RoleService;
import com.ims.system.util.CacheCxt;

import org.springframework.stereotype.Controller;
import com.ims.common.web.BaseController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 陈骑元
 * @since 2018-10-02
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    private String prefix = "system/role/"; 
    @Autowired
    private RoleService roleService;
	/**
	 * 
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:role:role")
	@GetMapping("init")
	public String init() {

		return prefix + "roleList";
	}

	/**
	 * 
	 * 简要说明：分页查询 
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:role:role")
	@RequestMapping("list")
	@ResponseBody
	public PageDto list() {
		Dto pDto = Dtos.newDto(request);
		pDto.setOrder(" create_time DESC ");
		User user=this.getTokenUser();
		if(!SystemCons.SUPER_ADMIN.equals(user.getAccount())){  //如果不是超级管理员，只能查看自己创建角色
			pDto.put("createBy", user.getUserId());
		}
		Page<Role> page =roleService.likePage(pDto);
		CacheCxt.convertDict(page);
		return new PageDto(page);
	}

	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:role:add")
	@GetMapping("add")
	public String add() {

		return prefix + "addRole";
	}

	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:role:add")
	@PostMapping("save")
	@ResponseBody
	public R save(Role role) {
    	role.setCreateBy(this.getTokenUserId());
    	role.setUpdateBy(this.getTokenUserId());
		role.setCreateTime(IMSUtil.getDateTime());
		role.setUpdateTime(IMSUtil.getDateTime());
		boolean result = roleService.insert(role);
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
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:role:edit")
	@GetMapping("edit")
	public String edit(String id,Model model) {
		Role role=roleService.selectById(id);
		model.addAttribute("role", role);
		return prefix + "editRole";
	}
	
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
    @RequiresPermissions("system:role:edit")
	@PostMapping("update")
	@ResponseBody
	public R update(Role role) {
    	role.setUpdateBy(this.getTokenUserId());
		role.setUpdateTime(IMSUtil.getDateTime());
		boolean result = roleService.updateById(role);
		if (result) {
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明： 展示详情
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("showDetail")
	public String showDetail(String id,Model model) {
		Role role=roleService.selectById(id);
		model.addAttribute("role",role);
		return prefix + "showRole";
	}
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:role:remove")
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		boolean result = roleService.removeRole(id);
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
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("batchRemove")
	@ResponseBody
	public R batchRemove(String ids) {
		List<String> idList=IMSFormater.separatStringToList(ids);
		boolean result = roleService.deleteBatchIds(idList);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	/**
	 * 
	 * 简要说明： 跳转授权菜单页面
	 * 编写者：陈骑元
	 * 创建时间：2018-12-18
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:role:roleMenu")
	@GetMapping("roleMenu")
	public String roleMenu(String roleId,Model model) {
		model.addAttribute("roleId", roleId);
		return prefix + "roleMenu";
	}
	
	/**
	 * 
	 * 简要说明：展示权限菜单树
	 * 编写者：陈骑元
	 * 创建时间：2018-12-18
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:role:roleMenu")
	@PostMapping("listRoleMenu")
	@ResponseBody
	public List<TreeModel> listRoleMenu(String roleId) {
		User user=this.getTokenUser();
		List<TreeModel> roleMenuList=  roleService.listRoleMenu(roleId,user);
		return roleMenuList;
		
	}
	/**
	 * 
	 * 简要说明： 保存授权菜单
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:role:roleMenu")
	@PostMapping("saveRoleMenu")
	@ResponseBody
	public R saveRoleMenu(String roleId,String menuIds) {
		List<String> menuIdList=IMSFormater.separatStringToList(menuIds);
		boolean result=roleService.batchSaveRoleMenu(roleId, menuIdList);
		if (result) {
			return R.ok("授权菜单成功");
		} else {
			return R.error("授权菜单失败");
		}
	}
	/**
	 * 
	 * 简要说明： 授权用户
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:role:roleUser")
	@GetMapping("roleUser")
	public String roleUser(String id,Model model) {
		model.addAttribute("roleId",id);
		return prefix + "roleUser";
	}
	/**
	 * 
	 * 简要说明： 保存授权用户
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:role:roleUser")
	@PostMapping("saveRoleUser")
	@ResponseBody
	public R saveRoleUser(String roleId,String userIds) {
		List<String> userIdList=IMSFormater.separatStringToList(userIds);
		boolean result=roleService.batchSaveRoleUser(roleId, userIdList);
		if (result) {
			return R.ok("用户授权成功");
		} else {
			return R.error("用户授权失败");
		}
	}
	/**
	 * 
	 * 简要说明： 撤销授权用户
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("removeRoleUser")
	@ResponseBody
	public R removeRoleUser(String roleId,String userIds) {
		List<String> userIdList=IMSFormater.separatStringToList(userIds);
		boolean result=roleService.batchRemoveRoleUser(roleId, userIdList);
		if (result) {
			return R.ok("撤销用户授权成功");
		} else {
			return R.error("撤销用户授权失败");
		}
	}
	/**
	 * 
	 * 简要说明：清空字典缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月13日 下午11:09:04
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:role:refreshCache")
	@PostMapping("refreshCache")
	@ResponseBody
	public R refreshCache() {
		
		CacheCxt.flushRoleMenu();
		
		return R.ok("清空缓存操作成功");
	}
}

