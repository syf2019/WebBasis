package com.ims.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.support.redis.JedisHelper;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.JsonUtil;
import com.ims.system.constant.SystemCons;
import com.ims.system.mapper.DictIndexMapper;
import com.ims.system.mapper.DictMapper;
import com.ims.system.mapper.MenuMapper;
import com.ims.system.mapper.ParamMapper;
import com.ims.system.mapper.SystemCommonMapper;
import com.ims.system.mapper.UserMapper;
import com.ims.system.model.Dict;
import com.ims.system.model.DictIndex;
import com.ims.system.model.Menu;
import com.ims.system.model.Param;
import com.ims.system.model.User;



/**
 * 
 * 类名:com.ims.system.service.impl.ResourceCacheServiceImpl
 * 描述:缓存资源服务处理
 * 编写者:陈骑元
 * 创建时间:2018年5月1日 下午4:57:07
 * 修改说明:
 */
@Service("resourceCacheService")
public class ResourceCacheService {
	
	private static Log log = LogFactory.getLog(ResourceCacheService.class);
	 /**
	  * redis缓存服务
	  */
	@Autowired
	private JedisHelper jedisHelper;
	/**
	 * 键值参数数据库接口
	 */
	@Autowired
	private ParamMapper paramMapper;
	/**
	 * 字典数据库接口
	 */
	 @Autowired
	 private DictMapper dictMapper;
	/**
	 * 字典数据库接口
	 */
	 @Autowired
	 private DictIndexMapper dictIndexMapper;
	
	
	 /**
	  * 用户
	  */
	 @Autowired
	 private UserMapper userMapper;
	 
	 /**
	  * 缓存菜单
	  */
	 @Autowired
	 private MenuMapper menuMapper;
	 
	 /**
	  * 通用查询方法
	  */
	 @Autowired
	 private SystemCommonMapper systemCommonMapper;
	 
	
	 
    /**
     * 
     * 简要说明：获取缓存中的键值参数，没有数据库中查找
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午9:22:38
     * @param 说明
     * @return 说明
     */

	public Param getCacheParam(String paramKey) {
		Param param=null;
		if(IMSUtil.isNotEmpty(paramKey)){
			
			try {
				String paramJson = jedisHelper.hget(SystemCons.CACHE_PREFIX.PARAM, paramKey);
				if (IMSUtil.isNotEmpty(paramJson)) {
					param = (Param) JsonUtil.fromJson(paramJson, Param.class);
				} else { // 如果redis 找不到就去数据库中查找，并存放redis中
					param = cacheParam(paramKey);

				} 
			} catch (Exception e) {
				param = getParam(paramKey);
			}
			
			
		}else{
			log.error("获取键值参数失败：参数键为空");
		}
		return param;
	}
	/**
	 * 
	 * 简要说明：根据参数键进行键值参数缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午9:23:28
	 * @param 说明
	 * @return 说明
	 */

	public Param cacheParam(String paramKey) {
		Param param=getParam(paramKey);
		if(IMSUtil.isNotEmpty(param)){
			jedisHelper.hset(SystemCons.CACHE_PREFIX.PARAM, paramKey, JsonUtil.toJson(param));
		}
		return param;
	}
	/**
	 * 
	 * 简要说明：
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午9:10:34
	 * @param 说明
	 * @return 说明
	 */
    private Param getParam(String paramKey){
    	Param param=null;
    	if(IMSUtil.isNotEmpty(paramKey)){
    		Param entity=new Param();
    		entity.setParamKey(paramKey);
    		entity.setStatus(SystemCons.ENABLED_YES);  //只查询启用
    		param=paramMapper.selectOne(entity);
    	}
    	return param;
    }
    /**
     * 
     * 简要说明：根据实体进行键值参数缓存
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午9:24:46
     * @param 说明
     * @return 说明
     */

	public Param cacheParam(Param param) {
		if(IMSUtil.isNotEmpty(param)){
		
			jedisHelper.hset(SystemCons.CACHE_PREFIX.PARAM, param.getParamKey(), JsonUtil.toJson(param));
		
		}
		return param;
	}
    /**
     * 
     * 简要说明：
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午9:25:42
     * @param 说明
     * @return 说明
     */

	public void removeCacheParam(String paramKey) {
		if(IMSUtil.isNotEmpty(paramKey)){
		
			jedisHelper.hdel(SystemCons.CACHE_PREFIX.PARAM, paramKey);
		}

	}


	public void cacheAllParam() {
		
		List<Param> paramList=paramMapper.selectList(new EntityWrapper<Param>().eq("status", SystemCons.ENABLED_YES));
		Map<String, String> cacheMap = Maps.newHashMap();
		for (Param param : paramList) {
			cacheMap.put(param.getParamKey(), JsonUtil.toJson(param));
		}
		if (IMSUtil.isNotEmpty(cacheMap)) {
			
			flushParam() ;//先清空在插入
			jedisHelper.hmset(SystemCons.CACHE_PREFIX.PARAM, cacheMap);
			
		}	
		
	
	}
	


	public void flushParam() {
		
			
		Set<String> keySet=jedisHelper.keys(SystemCons.CACHE_PREFIX.PARAM+"*");
		for(String key:keySet){
			jedisHelper.delString(key);
		}
		

	}
    /**
     * 
     * 简要说明：获取缓存中的字典
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午11:06:13
     * @param 说明
     * @return 说明
     */

	@SuppressWarnings("unchecked")
	public List<Dict> getCacheDict(String dictKey) {
		List<Dict> dictList=Lists.newArrayList();
		if(IMSUtil.isNotEmpty(dictKey)){
			
			try {
				List<String> dictRedisList = jedisHelper.lrange(SystemCons.CACHE_PREFIX.DICT + dictKey, 0, -1);
				if (IMSUtil.isNotEmpty(dictRedisList)) {
					for (String dicString : dictRedisList) {
						dictList.add((Dict) JsonUtil.fromJson(dicString, Dict.class));
					}
					dictList= IMSUtil.removeRepeat(dictList);
				} else {
					dictList = cacheDict(dictKey);
				} 
			} catch (Exception e) {
				e.printStackTrace();
				dictList=getDict(dictKey);  //从数据库中获取
			}
		
		}else{
			
			log.error("获取字典失败：字典标识键[" + dictKey + "]为空");
		}
		return dictList;
	}
    /**
     * 
     * 简要说明：缓存字典
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午10:48:09
     * @param 说明
     * @return 说明
     */

	public List<Dict> cacheDict(String dictKey) {
		List<Dict> dictList=Lists.newArrayList();
		if(IMSUtil.isNotEmpty(dictKey)){
			    dictList=getDict(dictKey);
				jedisHelper.delString(SystemCons.CACHE_PREFIX.DICT + dictKey);
				if (IMSUtil.isNotEmpty(dictList)) {
					
					jedisHelper.rpush(SystemCons.CACHE_PREFIX.DICT + dictKey, dictList);
					

				} else {
					log.error("字典数据信息刷新到Redis中失败，字典标识键[" + dictKey + "]在系统中没有字典对照集合或者已经被停用");
				}
				
			
			
		}
		return dictList;
	}
    /**
     * 
     * 简要说明：根据键删除缓存字典
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午11:08:34
     * @param 说明
     * @return 说明
     */
	public void removeCacheDict(String dictKey) {
		if(IMSUtil.isNotEmpty(dictKey)){
			
				jedisHelper.delString(SystemCons.CACHE_PREFIX.DICT + dictKey);
			
		}

	}
    /**
     * 
     * 简要说明：根据字典键从数据库中获取字典
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午10:24:07
     * @param 说明
     * @return 说明
     */
	private List<Dict> getDict(String dictKey){
		List<Dict> dictList=Lists.newArrayList();
		if(IMSUtil.isNotEmpty(dictKey)){
			DictIndex entity=new DictIndex();
			entity.setDictKey(dictKey);
			DictIndex dictIndex=dictIndexMapper.selectOne(entity);
			if(IMSUtil.isNotEmpty(dictIndex)){
				String dictIndexId=dictIndex.getDictIndexId();
				EntityWrapper<Dict> wrapper =new EntityWrapper<Dict>();
				wrapper.eq("status", SystemCons.ENABLED_YES);
				wrapper.eq("dict_index_id", dictIndexId);
				wrapper.orderBy("sort_no", true);
				dictList=dictMapper.selectList(wrapper);
				for(Dict dict:dictList){
					dict.setDictKey(dictKey);
				}

				
			}
			if (dictList.size() == 0) {
				log.error("字典标识键[" + dictKey + "]在系统中没有字典对照集合或者已经被停用");
			}
			
		}else{
			log.error("获取字典失败：字典标识键[" + dictKey + "]为空");
		}
		
		
		return dictList;
		
	}
	/**
	 * 
	 * 简要说明：缓存所有字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午11:09:55
	 * @param 说明
	 * @return 说明
	 */
	public void cacheAllDict() {
		
			
		flushDict();//先清空，在缓存
		List<DictIndex> dictIndexList=dictIndexMapper.list(null);
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("status", SystemCons.ENABLED_YES);
		List<Dict> dictList=dictMapper.selectByMap(paramMap);
		for(Dict dict:dictList){
			String dictKey=getDictKey(dictIndexList,dict.getDictIndexId());
			if(IMSUtil.isNotEmpty(dictKey)){
				
				jedisHelper.rpush(SystemCons.CACHE_PREFIX.DICT + dictKey, JsonUtil.toJson(dict));
			}
		}	
			
		
		

	}
	/**
	 * 
	 * 简要说明：返回键值
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午11:20:57
	 * @param 说明
	 * @return 说明
	 */
	private String getDictKey(List<DictIndex> dictIndexList,String dictIndexId){
		
		for(DictIndex dictIndex:dictIndexList){
			String tmpIndexId=dictIndex.getDictIndexId();
			if(dictIndexId.equals(tmpIndexId)){
				
				return dictIndex.getDictKey();
			}
		}
		return "";
	}
    /**
     * 
     * 简要说明：清空缓存字典
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午11:09:19
     * @param 说明
     * @return 说明
     */
	public void flushDict() {
		
		    Set<String> keySet=jedisHelper.keys(SystemCons.CACHE_PREFIX.DICT+"*");
		    for(String key:keySet){
		    	jedisHelper.delString(key);
		    }
			
		
	}
    
	/**
	 * 
	 * 简要说明：
	 * 编写者：陈骑元
	 * 创建时间：2018年12月29日 下午11:13:50
	 * @param 说明
	 * @return 说明
	 */
	public User getCacheUser(String userId){
		User user=null;
		String redisUserKey=SystemCons.CACHE_PREFIX.USER+userId;
		String userJson=jedisHelper.getString(redisUserKey);
		if(IMSUtil.isNotEmpty(userJson)){
			 user=JsonUtil.fromJson(userJson, User.class);
		}else{
			user=userMapper.selectById(userId);
			if(IMSUtil.isNotEmpty(user)){
			  jedisHelper.setString(redisUserKey,JsonUtil.toJson(user), 24*3*3600);
			}
		}
		return user;
	}
	
	/**
	 * 
	 * 简要说明：获取用户授权菜单
	 * 编写者：陈骑元
	 * 创建时间：2019年1月7日 下午8:51:59
	 * @param 说明
	 * @return 说明
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getCacheRoleMenu(String userId){
		List<Menu> menuList=Lists.newArrayList();
		try {
			List<String> dictRedisList = jedisHelper.lrange(SystemCons.CACHE_PREFIX.ROLEMENU + userId, 0, -1);
			if (IMSUtil.isNotEmpty(dictRedisList)) {
				for (String dicString : dictRedisList) {
					menuList.add(JsonUtil.fromJson(dicString, Menu.class));
				}
			} else {
				menuList = getRoleMenuByDB(userId);
				if (IMSUtil.isNotEmpty(menuList)) {
					// 再插入redis 中
					jedisHelper.rpush(SystemCons.CACHE_PREFIX.ROLEMENU +userId, menuList);
				}
			} 
			menuList=IMSUtil.removeRepeat(menuList);
		} catch (Exception e) {
			menuList = getRoleMenuByDB(userId);
		}
         
		return menuList;
	}
	
	
	/**
	 * 
	 * 简要说明：从数据库中获取授权菜单
	 * 编写者：陈骑元
	 * 创建时间：2019年1月7日 下午8:51:59
	 * @param 说明
	 * @return 说明
	 */
	public List<Menu> getRoleMenuByDB(String userId){
		Dto pDto=Dtos.newDto();
		pDto.put("userId", userId);
		pDto.put("status", SystemCons.ENABLED_YES);
		List<Menu> menuList=systemCommonMapper.listRoleMenu(pDto);
		convertMenu(menuList);
		return menuList;
	}
	/**
	 * 
	 * 简要说明：获取用户授权菜单
	 * 编写者：陈骑元
	 * 创建时间：2019年1月7日 下午8:51:59
	 * @param 说明
	 * @return 说明
	 */
	public List<Menu> getCacheMenu(){
		List<Menu> menuList=Lists.newArrayList();
		try {
			List<String> dictRedisList = jedisHelper.lrange(SystemCons.CACHE_PREFIX.MENU , 0, -1);
			if (IMSUtil.isNotEmpty(dictRedisList)) {
				for (String dicString : dictRedisList) {
					menuList.add(JsonUtil.fromJson(dicString, Menu.class));
				}
			} else {
				menuList =getAllMenuByDB();
				if (IMSUtil.isNotEmpty(menuList)) {
					// 再插入redis 中
					jedisHelper.rpush(SystemCons.CACHE_PREFIX.MENU, menuList);
				}
			} 
			
		} catch (Exception e) {
			menuList = getAllMenuByDB();
		}
         
		return menuList;
	}
	
	
	/**
	 * 
	 * 简要说明：从数据库中获取所有菜单
	 * 编写者：陈骑元
	 * 创建时间：2019年1月7日 下午8:51:59
	 * @param 说明
	 * @return 说明
	 */
	public List<Menu> getAllMenuByDB(){
		Dto pDto=Dtos.newDto();
		pDto.setOrder("LENGTH(cascade_id) ASC,sort_no ASC ");
		pDto.put("status", SystemCons.ENABLED_YES);
		List<Menu> menuList=menuMapper.list(pDto);
		convertMenu(menuList);
		return menuList;
	}
	 /**
	  * 
	  * 简要说明：查询角色授权编号
	  * 编写者：陈骑元
	  * 创建时间：2019年1月8日 上午10:43:49
	  * @param 说明
	  * @return 说明
	  */
   public List<String> listRoleMenuId(String userId){
   	
   	return systemCommonMapper.listRoleMenuId(userId);
   
   }
   
   /**
	 * 
	 * 简要说明：
	 * 编写者：陈骑元
	 * 创建时间：2019年2月2日 下午6:45:23
	 * @param 说明
	 * @return 说明
	 */
	private  void convertMenu(List<Menu> menuList){
		Map<String,Menu> menuMap=new HashMap<String,Menu>();
		for(Menu m:menuList){
			Menu newMenu=new Menu();
			IMSUtil.copyProperties(m, newMenu);  //引用传递被污染，所以重新 创建
			menuMap.put(m.getMenuId(), newMenu);
		}
		for(Menu m:menuList){
			String menuCode=convertMenuCode(m,menuMap);
			m.setMenuCode(menuCode);
		}
	}
	/**
	 * 
	 * 简要说明：获取菜单编码
	 * 编写者：陈骑元
	 * 创建时间：2019年2月2日 下午7:21:18
	 * @param 说明
	 * @return 说明
	 */
	private  String convertMenuCode(Menu menu,Map<String,Menu> menuMap){
		  String menuCode=menu.getMenuCode();
		  String parentId=menu.getParentId();
		  
	      while(true){
	    	  if(SystemCons.TREE_ROOT_ID.equals(parentId)){
	    		 break;
	    	  }else{
	    		  Menu parentMenu=menuMap.get(parentId);
	    		  if(IMSUtil.isEmpty(parentMenu)){
	    			  break;
	    		  }
	    		  menuCode=parentMenu.getMenuCode()+":"+menuCode;
	    		  parentId=parentMenu.getParentId();
	    	  }
	      }
	      if(SystemCons.MODULE_TYPE_SUB.equals(menu.getModuleType())) {//模块是子菜单自动加上权限后缀
	    	 String menuId=menu.getMenuId();
	    	 Menu m=menuMap.get(menuId);//引用传递被污染，所以重新 获取编码
	    	 menuCode+=":"+m.getMenuCode();
	      }
	      return menuCode;
	}
	
}
