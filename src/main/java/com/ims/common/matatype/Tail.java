package com.ims.common.matatype;
/**
 * 
 * 类名:com.toonan.common.matatype.Tail
 * 描述:
 * 编写者:陈骑元
 * 创建时间:2018年12月17日 上午9:57:32
 * 修改说明:
 */
public interface Tail extends java.io.Serializable {
	public Object get(String key);
	public void set(String key,Object value);
}
