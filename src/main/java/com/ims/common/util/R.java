package com.ims.common.util;

import java.util.Map;

import com.ims.common.constant.IMSCons;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.impl.HashDto;
/**
 * 
 * 类名:com.ims.common.util.R
 * 描述:返回值封装
 * 编写者:陈骑元
 * 创建时间:2018年4月5日 下午10:49:20
 * 修改说明:
 */
public class R extends HashDto {
	private static final long serialVersionUID = 1L;

	public R() {
		put(IMSCons.APPCODE_KEY, IMSCons.SUCCESS);
		put(IMSCons.APPMSG_KEY, "操作成功");
	}

	public static R error() {
		return error(IMSCons.ERROR, "操作失败");
	}

	public static R error(String msg) {
		return error(IMSCons.ERROR, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put(IMSCons.APPCODE_KEY, code);
		r.put(IMSCons.APPMSG_KEY, msg);
		return r;
	}

	public static R warn() {
		return warn(IMSCons.WARN, "操作失败");
	}

	public static R warn(String msg) {
		return warn(IMSCons.WARN, msg);
	}

	public static R warn(int code, String msg) {
		R r = new R();
		r.put(IMSCons.APPCODE_KEY, code);
		r.put(IMSCons.APPMSG_KEY, msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put(IMSCons.APPMSG_KEY, msg);
		return r;
	}

	public static R ok(Dto dataDto) {
		R r = new R();
		r.putAll(dataDto);
		return r;
	}
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
