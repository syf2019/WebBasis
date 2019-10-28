package com.ims.system.util;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.ims.common.annotation.DictTag;
import com.ims.common.constant.IMSCons;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.matatype.impl.BaseModel;
import com.ims.common.support.redis.JedisHelper;
import com.ims.common.util.IMSFormater;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.IdUtil;
import com.ims.common.util.JsonUtil;
import com.ims.common.util.RegexUtil;
import com.ims.common.util.SpringContextHolder;
import com.ims.common.vo.Item;
import com.ims.common.vo.TreeModel;
import com.ims.system.constant.SystemCons;
import com.ims.system.model.Dict;
import com.ims.system.model.Menu;
import com.ims.system.model.Param;
import com.ims.system.model.User;
import com.ims.system.service.ResourceCacheService;




/**
 * 
 * 类名:com.ims.system.util.CacheCxt 描述:缓存上下文 编写者:陈骑元 创建时间:2018年5月2日 下午10:15:08
 * 修改说明:
 */
public class CacheCxt {
	
	private static Logger logger = LoggerFactory.getLogger(CacheCxt.class);

	private static ResourceCacheService resourceCacheService = SpringContextHolder.getBean("resourceCacheService");
	/**
	 * jedis帮助
	 */
	private static JedisHelper jedisHelper = SpringContextHolder.getBean("jedisHelper");
	/**
	 * 根据参数键获取参数值
	 * 
	 * @param paramPO参数键
	 * 
	 */
	public static String getParamValue(String paramKey) {
		String paramValue = "";
		Param param = getCacheParam(paramKey);
		if (IMSUtil.isNotEmpty(param)) {
			paramValue = param.getParamValue();
		}
		return paramValue;
	}

	/**
	 * 从数据库参数表中根据参数键获取参数值
	 * 
	 * @param paramKey
	 *            参数键
	 * @param defaultValue
	 *            缺省值
	 * @return
	 */
	public static String getParamValue(String paramKey, String defaultValue) {
		String valueString = getParamValue(paramKey);
		if (IMSUtil.isEmpty(valueString)) {
			valueString = defaultValue;
		}
		return valueString;
	}

	/**
	 * 
	 * 简要说明：根据参数键获取键值参数实体 
	 * 编写者：陈骑元
	 * 创建时间：2017年1月24日 上午10:22:18
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static Param getCacheParam(String paramKey) {
		Param param = null;
		if (IMSUtil.isNotEmpty(paramKey)) {
			param = resourceCacheService.getCacheParam(paramKey);
		}
		return param;
	}
	/**
	 * 
	 * 简要说明：根据参数键缓存进行缓存并返回参数实体
	 * 编写者：陈骑元
	 * 创建时间：2017年1月24日 上午10:22:18
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static Param cacheParam(String paramKey) {
		Param param = null;
		if (IMSUtil.isNotEmpty(paramKey)) {
			param = resourceCacheService.cacheParam(paramKey);
		}
		return param;
	}
	/**
	 * 
	 * 简要说明：缓存所有键值参数
	 * 编写者：陈骑元
	 * 创建时间：2017年1月24日 上午10:22:18
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static void cacheAllParam() {
		
		resourceCacheService.cacheAllParam();
		
		
	}
	/**
	 * 
	 * 简要说明：根据参数键移除参数缓存
	 * 编写者：陈骑元
	 * 创建时间：2017年1月24日 上午10:22:18
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static void removeCacheParam(String paramKey) {

       resourceCacheService.removeCacheParam(paramKey);
         
		
	}
	/**
	 * 
	 * 简要说明：根据参数键移除参数缓存
	 * 编写者：陈骑元
	 * 创建时间：2017年1月24日 上午10:22:18
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static void flushParam() {
		
		resourceCacheService.flushParam();
		
	}
	
	/**
	 * 
	 * 简要说明：获取缓存的字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月2日 下午10:57:15
	 * @param 说明
	 * @return 说明
	 */
	public static List<Dict> getCacheDict(String dictKey) {
		
		List<Dict> dictList=resourceCacheService.getCacheDict(dictKey);
		return dictList;
		
	}
	/**
	 * 
	 * 简要说明：根据字典键缓存字典并返回字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月2日 下午10:57:15
	 * @param 说明
	 * @return 说明
	 */
	public static List<Dict> cacheDict(String dictKey) {
		
		List<Dict> dictList=resourceCacheService.cacheDict(dictKey);
		return dictList;
		
	}
	/**
	 * 
	 * 简要说明：缓存所有字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月2日 下午10:57:15
	 * @param 说明
	 * @return 说明
	 */
	public static void cacheAllDict() {
		
		resourceCacheService.cacheAllDict();
		
	}
	/**
	 * 
	 * 简要说明：删除缓存字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月2日 下午10:57:15
	 * @param 说明
	 * @return 说明
	 */
	public static void  removeCacheDict(String dictKey) {
		
		resourceCacheService.removeCacheDict(dictKey);
	}
	/**
	 * 
	 * 简要说明：清空缓存字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月2日 下午10:57:15
	 * @param 说明
	 * @return 说明
	 */
	public static void  flushDict() {
		
		resourceCacheService.flushDict();
	}
	/**
	 * 
	 * 简要说明：获取字典值
	 * 编写者：陈骑元
	 * 创建时间：2018年12月15日 下午5:33:41
	 * @param 说明
	 * @return 说明
	 */
	public static List<Item> getItemList(String typeCode,String filterCodestr) {
		List<Item> itemList=Lists.newArrayList();
		if (typeCode.matches("JZ[0-9]+")) {
		 	
	     }else{
		List<Dict> dictList=getCacheDict(typeCode);
		
		 for(Dict dict:dictList){
			 String itemCode=dict.getDictCode();
			 if(!IMSUtil.contains(itemCode, filterCodestr)){
				 Item item=new Item();
				 item.setTypeCode(dict.getDictKey());
				 item.setItemCode(itemCode);
				 item.setItemName(dict.getDictValue());
				 itemList.add(item);
				 
			 }
			
		 }
	   }
		
		return itemList;
	}
	
	/**
	 * 
	 * 简要说明：获取字典值
	 * 编写者：陈骑元
	 * 创建时间：2018年12月15日 下午5:33:41
	 * @param 说明
	 * @return 说明
	 */
	public static List<Item> getItemList(String typeCode) {
		
		
		return getItemList(typeCode,"");
		
	}
	
	/**
	 * 根据数据字典标识键和字典对照代码获取字典对照值
	 * 
	 * @param dictKey
	 *            数据字典标识键
	 * @param dictCode
	 *            数据字典对照代码
	 * @return
	 */
	public static String getDictValue(String dictKey, String dictCode) {
		String dictValue = "";
		List<Dict> dictList =getCacheDict(dictKey);
		for (Dict dict: dictList) {
			if (dict.getDictCode().equals(dictCode)) {
				dictValue = dict.getDictValue();
				break;
			}
		}
		return dictValue;
	}
	/**
	 * 根据数据字典标识键和字典对照代码获取字典对照值
	 * 
	 * @param dictKey
	 *            数据字典标识键
	 * @param dictCode
	 *            数据字典对照代码
	 * @return
	 */
	public static String getDicValue(List<Dict>  dicList, String dictCode) {
		String dictValue = "";
		for (Dict dict: dicList) {
			if (dict.getDictCode().equals(dictCode)) {
				dictValue = dict.getDictValue();
				break;
			}
		}
		return dictValue;
	}
	
	/**
	 * 
	 * 简要说明：根据字典集合过滤出需要的的字典
	 * 编写者：陈骑元
	 * 创建时间：2018年3月22日 下午3:43:19
	 * @param 说明 dictKey 获取的键， key需要集合
	 * @return 说明
	 */
	public static List<Dict> getFilterDictList(String dictKey,List<String> filterCodeList) {
		List<Dict> filterDictList=Lists.newArrayList();
		List<Dict>  dictList=getCacheDict(dictKey);
		for(Dict dict:dictList){
			String dictCode=dict.getDictCode();
			for(String code:filterCodeList){
				if(dictCode.equals(code)){
					filterDictList.add(dict);
				}
			}
		}
		
		return filterDictList;
	}
	/**
	 * 
	 * 简要说明：根据字典集合过滤出需要的的字典
	 * 编写者：陈骑元
	 * 创建时间：2018年3月22日 下午3:43:19
	 * @param 说明 dic_key 获取的键，keyString 逗号分隔的字典代码串
	 * @return 说明
	 */
	public static List<Dict> getFilterDictList(String dictKey,String filterCodestr) {
		List<Dict> dicList=Lists.newArrayList();
		if(IMSUtil.isNotEmpty(filterCodestr)){
			List<String> keyList=IMSFormater.separatStringToList(filterCodestr);
			dicList=getFilterDictList(dictKey,keyList);
		}else{
			dicList=getCacheDict(dictKey);
		}
		
		return dicList;
	}
	
	/**
	 * 
	 * 简要说明：转换分页
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午8:15:29
	 * @param 说明
	 * @return 说明
	 */
	public static void convertDict(Page<?> page){
		if(IMSUtil.isNotEmpty(page)){
			convertDict(page.getRecords());
		}
		
	}
	/**
	 * 
	 * 简要说明：转换字典
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午7:02:43
	 * @param 说明
	 * @return 说明
	 */
	public static void convertDict(List<?> list){
		
		if(IMSUtil.isNotEmpty(list)){
			Object dictBean=list.get(0);
			Dto dictDto=getConvertDictData(dictBean);
			for (Object bean : list) {
				convertDict(bean,dictDto);
	        }
		}
	}
	/**
	 * 
	 * 简要说明：     
	 * 编写者：陈骑元
	 * 创建时间：2018年12月18日 下午9:48:00
	 * @param 说明
	 * @return 说明
	 */
	public static void convertDict(Object  bean){
		
		Dto dictDto=getConvertDictData(bean);
		convertDict(bean, dictDto);
	}
	
	/**
	 * 
	 * 简要说明：转换map字典
	 * 编写者：陈骑元
	 * 创建时间：2018年12月18日 下午9:47:55
	 * @param 说明
	 * @return 说明
	 */
    @SuppressWarnings("rawtypes")
	public static void convertMapDict(List<? extends Map> listMap,Dto keyDto){
		Dto dictDto=getDictDto(keyDto);
		for(Map<?,?> dataMap:listMap){
			convertDict(dataMap,keyDto,dictDto);
		}
		
	}
	/**
	 * 
	 * 简要说明：转换map字典
	 * 编写者：陈骑元
	 * 创建时间：2018年12月18日 下午9:47:55
	 * @param 说明
	 * @return 说明
	 */
    public static void convertMapDict(Map<?, ?> dataMap,Dto keyDto){
		Dto dictDto=getDictDto(keyDto);
		convertDict(dataMap,keyDto,dictDto);
	}
    /**
     * 
     * 简要说明：转换字典
     * 编写者：陈骑元
     * 创建时间：2018年12月18日 下午10:04:19
     * @param 说明
     * @return 说明
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private  static void convertDict(Map dataMap,Dto keyDto,Dto dictDto){
    	Set<String> keySet=keyDto.keySet();
		for(String key:keySet){
			if(IMSUtil.isNotEmpty(key)){
				String dictValue="";
				Object value=dataMap.get(key);
				if(IMSUtil.isNotEmpty(value)){
					 String typeCode=keyDto.getString(key);
					 dictValue=getItemName(typeCode,value.toString(),dictDto);
				 }
				 dataMap.put(key+"_dict", dictValue);
			}
				
		}
    	
    	
    }
    /**
     * 
     * 简要说明：返回字典
     * 编写者：陈骑元
     * 创建时间：2018年12月18日 下午9:52:50
     * @param 说明
     * @return 说明
     */
    private static Dto getDictDto(Dto keyDto){
    	Dto dictDto=Dtos.newDto();
    	if(IMSUtil.isNotEmpty(keyDto)){
    		Set<String> keySet=keyDto.keySet();
    		for(String key:keySet){
    			String typeCode=keyDto.getString(key);
    			
    			if(IMSUtil.isNotEmpty(typeCode)){
    			   if(!SystemCons.TYPECODE_SYSUSER.equals(typeCode)){
    				   dictDto.putAll(getDictDto(typeCode));
    			   }
    			  
    			}
    				
    		}
    	}
    	return dictDto;
    	
    }
	
	/**
	 * 
	 * 简要说明：转换字典
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午7:02:43
	 * @param 说明
	 * @return 说明
	 */
	@SuppressWarnings("rawtypes")
	public static void convertDict(Object  bean,Dto dictDto){
		  if (IMSUtil.isEmpty(bean)) {
	            return;
	        }
	        
	        if(!(bean instanceof BaseModel)){
	        	throw new RuntimeException("指定的pojo"+bean.getClass()+" 不能获取数据字典，需要继承BaseModel");
	        }
	        
	        BaseModel ext  = (BaseModel)bean;
	        Class c = ext.getClass();
	        do {
	            Field[] fields = c.getDeclaredFields();
	            for (Field field : fields) {
	                if (field.isAnnotationPresent(DictTag.class)) {
	                    field.setAccessible(true);
	                    DictTag dictTag = field.getAnnotation(DictTag.class);
	                    String typeCode=dictTag.type();
	                    if(IMSUtil.isNotEmpty(dictTag)){
	                    	   try {
	   	                        String display = "";
	   	                        Object fieldValue = field.get(ext);
	   	                        if (IMSUtil.isNotEmpty(fieldValue)) {
	   	                           display=getItemName(typeCode,fieldValue.toString(),dictDto);
	   	                        }
	   	                      
	   	                        ext.set(field.getName()+"_" + dictTag.suffix(), display);
	   	                    } catch (Exception e) {
	   	                        e.printStackTrace();
	   	                    }
	   	    
	                    }
	                 
	                }
	            }
	         c = c.getSuperclass();
	        }while(c!=BaseModel.class);
	        
	}
	/**
	 * 
	 * 简要说明：根据字典类型和代码查询字典名称 编写者：陈骑元 创建时间：2018年12月15日 下午5:33:41
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static String getItemName(String typeCode, String itemCode) {
		if (IMSUtil.isNotEmpty(itemCode)) {
			List<Item> itemList = getItemList(typeCode);
			String itemName=getItemName(itemCode,itemList);
			return itemName;
		}
		return "";
	}
	
	/**
	 * 
	 * 简要说明：根据字典类型和代码查询字典名称 
	 * 编写者：陈骑元 
	 * 创建时间：2018年12月15日 下午5:33:41
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static String getItemName(String itemCode,List<Item> itemList) {
		if (IMSUtil.isNotEmpty(itemCode)) {
			
			if (IMSUtil.isNotEmpty(itemList)) {
				for (Item item : itemList) {
					if (itemCode.equals(item.getItemCode())) {
						return item.getItemName();
					}
				}
			}
		}

		return "";
	}
	/**
	 * 
	 * 简要说明：获取字典DICT集合Dto 使用键值typeCode_itemCode 组成
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午7:43:06
	 * @param 说明
	 * @return 说明
	 */
	public static Dto getDictDto(String typeCode){
		Dto dictDto=Dtos.newDto();
		if(!SystemCons.TYPECODE_SYSUSER.equals(typeCode)){
			List<Item> itemList=getItemList(typeCode);
			for(Item item:itemList){
				String key=item.getTypeCode()+"_"+item.getItemCode();
				dictDto.put(key, item.getItemName());
			}
		}
		return dictDto;
	}
	/**
	 * 
	 * 简要说明：解析字典
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午7:53:36
	 * @param 说明
	 * @return 说明
	 */
   private static String getItemName(String typeCode,String itemCode,Dto dictDto){
	   String itemName="";
	   if(IMSUtil.isNotEmpty(itemCode)){
		   String[] codeArray=itemCode.split(",");
		   for(String code:codeArray){
			   String key= typeCode+"_"+code;
			   if(SystemCons.TYPECODE_SYSUSER.equals(typeCode)){
				   if(!dictDto.containsKey(key)){
					   if(RegexUtil.isContainChinese(code)){
						   dictDto.put(key,code);
					   }else{
						   String userName=CacheCxt.getCacheUserName(code);
						   dictDto.put(key, userName);
					   }
				   }
			   }
			 
			   if(dictDto.containsKey(key)){
				   itemName+=dictDto.getString(key)+",";
			   }else{
				   itemName+=itemCode+",";
			   }
		   }
	   }
	   if(IMSUtil.isNotEmpty(itemName)){
		   itemName=itemName.substring(0, itemName.length()-1);
	   }
	   return itemName;
   }
	/**
	 * 
	 * 简要说明：获取转换好的字典数据
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午7:32:59
	 * @param 说明
	 * @return 说明
	 */
	@SuppressWarnings("rawtypes")
	private static Dto getConvertDictData(Object  bean){
		Dto dictDto=Dtos.newDto();
		if (IMSUtil.isEmpty(bean)) {
            return dictDto;
        }
        
        if(!(bean instanceof BaseModel)){
        	return dictDto;
        }
        
        BaseModel ext  = (BaseModel)bean;
        Class c = ext.getClass();
        do {
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(DictTag.class)) {
                    field.setAccessible(true);
                    DictTag dictTag = field.getAnnotation(DictTag.class);
                    String type=dictTag.type();
                    if(IMSUtil.isNotEmpty(type)){
                    	dictDto.putAll(getDictDto(type));
                    }
                }
            }
         c = c.getSuperclass();
        }while(c!=BaseModel.class);
        
        return dictDto;
	}
	/**
	 * 
	 * 简要说明：获取缓存菜单 编写者：陈骑元 创建时间：2018年12月29日 下午11:43:32
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static List<Menu> getCacheRoleMenu(String userId) {
		String roleMenuSwitch = getParamValue(SystemCons.ROLE_MENU_SWITCH_KEY);
		if (SystemCons.SWITCH_ON.equals(roleMenuSwitch)) {
			return resourceCacheService.getCacheRoleMenu(userId);
		} else {
			return getRoleMenuByAllMenu(userId);
		}

	}

	/**
	 * 
	 * 简要说明：获取看片菜单 
	 * 编写者：陈骑元
	 *  创建时间：2019年1月7日 下午8:51:59
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static List<TreeModel> getCardMenu(String userId, String menuType,String wheherSuper) {
		TreeModel rootMode = new TreeModel();
		rootMode.setId(SystemCons.TREE_ROOT_ID);
		List<Menu> menuList =Lists.newArrayList();
		if(IMSCons.WHETHER_YES.equals(wheherSuper)){  //如果是超级管理员具有所有的菜单权限
			
			menuList = getCacheMenu(); 
		}else{
		 menuList = getCacheRoleMenu(userId);
		}
		
		for (Menu menu : menuList) {
			String menuTypeNew = menu.getMenuType();
			if (IMSUtil.isEmpty(menuType)||SystemCons.MENU_TYPE_SYSTEM.equals(menuTypeNew) || menuTypeNew.equals(menuType)) {

				String modelType = menu.getModuleType();
				if (!SystemCons.MODULE_TYPE_BUTTON.equals(modelType)) {
					TreeModel menuMode = new TreeModel();
					menuMode.setId(menu.getMenuId());
					menuMode.setName(menu.getMenuName());
					if (SystemCons.MODULE_TYPE_PARENT.equals(modelType)) {
						menuMode.setParent(true);
					} else {
						menuMode.setParent(false);
					}
					menuMode.setpId(menu.getParentId());
					if(IMSUtil.isNotEmpty(menu.getUrl())&&!menu.getUrl().startsWith("/")&&!menu.getUrl().startsWith("http:")&&!menu.getUrl().startsWith("https:")){
						menu.setUrl("/"+menu.getUrl());//url如果不是以'/'开头的，则加上'/'
					}
					menuMode.setUrl(menu.getUrl());
					menuMode.setIcon(menu.getIconName());
					if (IMSCons.WHETHER_YES.equals(menu.getIsAutoExpand())) {
						menuMode.setOpen(true);// 展开节点
					} else {
						menuMode.setOpen(false);// 展开节点
					}
					rootMode.add(menuMode);
				}
			}
		}

		return rootMode.getChildren();

	}

	/**
	 * 
	 * 简要说明：更具用户编号获取授权菜单 编写者：陈骑元 创建时间：2019年1月8日 上午10:53:34
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static List<String> listRoleMenuId(String userId) {

		return resourceCacheService.listRoleMenuId(userId);
	}

	/**
	 * 
	 * 简要说明：获取所有菜单 编写者：陈骑元 创建时间：2019年1月8日 上午10:53:34
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static List<Menu> getCacheMenu() {

		return resourceCacheService.getCacheMenu();
	}

	/**
	 * 
	 * 简要说明：从所有菜单中过滤 编写者：陈骑元 创建时间：2019年1月8日 上午10:55:49
	 * 
	 * @param 说明
	 * @return 说明
	 */
	@SuppressWarnings("unchecked")
	public static List<Menu> getRoleMenuByAllMenu(String userId) {
		List<Menu> menuList = getCacheMenu();
		List<String> menuIdList = listRoleMenuId(userId);
		List<Menu> roleMenuList = Lists.newArrayList();
		for (Menu menu : menuList) {
			String menuId = menu.getMenuId();
			if (IMSUtil.contains(menuId, menuIdList)) {
				roleMenuList.add(menu);
			}
		}
		roleMenuList = IMSUtil.removeRepeat(roleMenuList);
		return roleMenuList;
	}

	/**
	 * 
	 * 简要说明：刷新角色菜单 编写者：陈骑元 创建时间：2018年5月1日 下午11:09:19
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static void flushRoleMenu() {

		Set<String> keySet = jedisHelper.keys(SystemCons.CACHE_PREFIX.ROLEMENU + "*");
		for (String key : keySet) {
			jedisHelper.delString(key);
		}

	}
	/**
	 * 
	 * 简要说明：获取认证的权限 
	 * 编写者：陈骑元
	 * 创建时间：2019年1月8日 下午1:41:07
	 * @param 说明
	 * @return 说明
	 */
	public static Set<String> getAuthPermissions(String userId,String whetherSuper) {
		
		Set<String> permissionsSet = new HashSet<String>();
		List<Menu> menuList=Lists.newArrayList();
		if(IMSCons.WHETHER_YES.equals(whetherSuper)){  //超级管理员具有所有权限
			menuList =  getCacheMenu();
		}else{
			menuList = getCacheRoleMenu(userId);
		}
		for (Menu menu : menuList) {
			String menuCode = menu.getMenuCode();
			if (IMSUtil.isNotEmpty(menuCode)) {
				if(menuCode.indexOf(":")>-1){  //存在冒号的不需要处理
					permissionsSet.add(menuCode);
				}
				
			}
		}
		return permissionsSet;
	}
	
	/**
	 * 
	 * 简要说明：移除菜单缓存 
	 * 编写者：陈骑元 
	 * 创建时间：2018年5月1日 下午11:09:19
	 * @param 说明
	 * @return 说明
	 */
	public static void romveCacheMenu() {
		jedisHelper.delString(SystemCons.CACHE_PREFIX.MENU);

	}
	
	/**
	 * 
	 * 简要说明：获取缓存的用户编号
	 * 编写者：陈骑元
	 * 创建时间：2018年12月29日 下午11:43:32
	 * @param 说明
	 * @return 说明
	 */
	public static User getCacheUser(String userId){
		
		return resourceCacheService.getCacheUser(userId);
	}
	
	/**
	 * 
	 * 简要说明：获取缓存的用户姓名
	 * 编写者：陈骑元
	 * 创建时间：2018年12月29日 下午11:43:32
	 * @param 说明
	 * @return 说明
	 */
	public static String  getCacheUserName(String userId){
		
		User user=getCacheUser(userId);
		if(IMSUtil.isNotEmpty(user)){
			
			return user.getUsername();
		}
		return "";
	}
	/**
	 * 
	 * 简要说明：清除缓存用户
	 * 编写者：陈骑元
	 * 创建时间：2018年12月29日 下午11:31:27
	 * @param 说明
	 * @return 说明
	 */
	public void removeCacheUser(String userId){
		
		String redisUserKey=SystemCons.CACHE_PREFIX.USER+userId;
		jedisHelper.delString(redisUserKey);
		
	}
	/**
	 * 
	 * 简要说明：创建token，并返回token字符串 
	 * 编写者：陈骑元 
	 * 创建时间：2018年12月22日 下午1:58:54
	 * @param 说明
	 *     tokenJson存储token数据的json数据串
	 * @return 说明
	 */
	public static String createToken(String tokenJson) {
		String token = IdUtil.uuid();
		String tokenKey = SystemCons.CACHE_PREFIX.TOKEN + token;
		jedisHelper.setString(tokenKey, tokenJson, SystemCons.DEFAULT_TIMECOUT);
		return token;
	}

	/**
	 * 
	 * 简要说明：创建token，并返回token字符串 编写者：陈骑元 创建时间：2018年12月22日 下午1:58:54
	 * 
	 * @param 说明
	 *            tokenJson存储token数据的json数据串
	 * @return 说明
	 */
	public static String createToken(User user) {
		String tokenJson = JsonUtil.toJson(user);
		return createToken( tokenJson);
	}

	/**
	 * 
	 * 简要说明：根据token 获取token 存储值 编写者：陈骑元 创建时间：2018年12月22日 下午1:58:54
	 * 
	 * @param
	 * @return 说明
	 */
	public static String getToken(String token) {
		String tokenKey = SystemCons.CACHE_PREFIX.TOKEN + token;
		String jsonStr = jedisHelper.getString(tokenKey);
		logger.info("当前token：" + token + ",返回值：" + jsonStr);
		return jsonStr;
	}

	/**
	 * 
	 * 简要说明：创建token，并返回token字符串 编写者：陈骑元 创建时间：2018年12月22日 下午1:58:54
	 * 
	 * @param 说明
	 *            tokenJson存储token数据的json数据串
	 * @return 说明
	 */
	public static User getUserToken(String token) {
		User user = null;
		String tokenJson = getToken(token);

		if (IMSUtil.isNotEmpty(tokenJson)) {
			user = JsonUtil.fromJson(tokenJson, User.class);

		} else {
			logger.error("当前token：" + token + ",无法获取用户信息");
		}
		return user;
	}

	/**
	 * 
	 * 简要说明：刷新token的值 编写者：陈骑元 创建时间：2018年12月22日 下午2:32:48
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static boolean checkAndRefreshToken(String token) {
		boolean result = false;

		String tokenKey = SystemCons.CACHE_PREFIX.TOKEN + token;
		if (jedisHelper.exists(tokenKey)) {

			jedisHelper.expire(tokenKey, SystemCons.DEFAULT_TIMECOUT);
			result = true;
		}

		return result;
	}

	/**
	 * 
	 * 简要说明：清空token 编写者：陈骑元 创建时间：2018年12月24日 下午9:22:15
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static void removeToken(String token) {
		String tokenKey = SystemCons.CACHE_PREFIX.TOKEN + token;
		jedisHelper.delString(tokenKey);
	}

	/**
	 * 
	 * 简要说明：获取redis自增序列 编写者：陈骑元 创建时间：2018年12月24日 下午3:13:50
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static String getRedisSeqNum(String prefix) {
		if (IMSUtil.isEmpty(prefix)) {
			prefix = "1";
		}
		String middle = IMSUtil.getDateStr("yyMMddHHmm");
		try {

			String redisKey = SystemCons.CACHE_PREFIX.SEQ + prefix;
			Long incr = jedisHelper.incr(redisKey);
			if (incr >= 99999) {
				jedisHelper.getSet(redisKey, "0");
			}
			String end = StringUtils.leftPad(incr + "", 5, "0");
			String seqNumStr = prefix + middle + end;
			return seqNumStr;
		} catch (Exception e) {
			long random = IMSUtil.randomBetween(0, 99999);
			return prefix + middle + StringUtils.leftPad(random + "", 5, "0");

		}

	}

	/**
	 * 
	 * 简要说明：获取rediskey 
	 * 编写者：陈骑元
	 * 创建时间：2018年12月24日 下午4:15:24
	 * @param 说明
	 * @return 说明
	 */
	public static String getRedisSeqNum() {

		return getRedisSeqNum("1");
	}
}
