package com.ims.common.matatype.impl;

import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.ims.common.matatype.Tail;

/**
 * 
 * 类名:com.toonan.common.matatype.impl.BaseModel 描述: 编写者:陈骑元 创建时间:2018年12月17日
 * 上午10:21:39 修改说明:
 */
@SuppressWarnings("rawtypes")
public abstract class BaseModel<T extends Model> extends Model implements Tail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	protected Map<String, Object> extMap = new HashMap<String, Object>();

	public Object get(String key) {

		return extMap.get(key);

	}

	public void set(String key, Object value) {

		this.extMap.put(key, value);

	}
	
	@JsonAnyGetter
	public Map<String, Object> getExtMap() {
        
		return extMap;

	}

}
