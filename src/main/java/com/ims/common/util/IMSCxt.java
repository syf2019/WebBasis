package com.ims.common.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.ims.common.constant.IMSCons;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.matatype.impl.HashDto;



/**
 * 
 * 类描述： <b>应用系统上下文</b> 
 * 创建人：陈骑元
 * 邮箱：240823329@qq.com
 * 创建时间：2016-6-10 上午10:36:52
 * 修改人：
 * 修改时间：
 * 修改备注： 
 * @version 1.0
 */
public class IMSCxt {

	
	/**
	 * 将Request请求参数封装为Dto对象
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("all")
	public static Dto getParamAsDto(HttpServletRequest request) {
		Dto dto = new HashDto();
		Map<String, String[]> map = request.getParameterMap();
		Iterator<String> keyIterator = (Iterator) map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = "";
			if(map.get(key).length<=1){
				value=map.get(key)[0];
			}else{
				value=StringUtils.join(map.get(key), ",");
			}
			dto.put(key, value);
		}
		
		return dto;
	}

	/**
	 * 向Response流输出字符串
	 * <p/>
	 * <p>
	 * 输出字符串不做任何加工，原样输出。
	 * 
	 * @param response
	 * @param outString
	 *            输出字符串
	 */
	public static void writeRaw(HttpServletResponse response, String outString) {
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			response.getWriter().write(outString == null ? "" : outString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 向Response流输出业务数据字符串
	 * <p/>
	 * 如果传入的参数是一个Json字符串则直接输出。若为一个常规字符串则将其自动作为Key为appmsg的json属性值输出。(缺省追加参数：
	 * appcode=1 success=true)
	 * 
	 * @param response
	 * @param outString
	 *            输出符合Json格式的业务数据字符串 / 提示信息常规字符串
	 */
	public static void write(HttpServletResponse response,Object outObject) {
		
		String outString=JsonUtil.toJson(outObject);
		write(response,outString);
	}
	/**
	 * 向Response流输出业务数据字符串
	 * <p/>
	 * 如果传入的参数是一个Json字符串则直接输出。若为一个常规字符串则将其自动作为Key为appmsg的json属性值输出。(缺省追加参数：
	 * appcode=1 success=true)
	 * 
	 * @param response
	 * @param outString
	 *            输出符合Json格式的业务数据字符串 / 提示信息常规字符串
	 */
	public static void write(HttpServletResponse response, String outString) {
		
		if (StringUtils.indexOf(outString, "{") == -1 && !outString.equals("[]")) {
			outString = writeMsg(outString);
		}
		try {
			response.getWriter().write(outString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向Response流输出提示信息字符串
	 * 
	 * <p>
	 * 缺省追加参数：appcode=1 success=true appmsg
	 * </p>
	 * 
	 * @param msg
	 *            提示信息，这个提示信息被转换为Json的key为:appmsg。前台根据appmsg取出。
	 */
	private static String writeMsg(String msg) {
		Dto dto = Dtos.newOutDto();
		dto.put(IMSCons.APPCODE_KEY, IMSCons.SUCCESS);
		dto.put(IMSCons.APPMSG_KEY, msg);
		String outString = JsonUtil.toJson(dto);
		return outString;
	}

	/**
	 * 向Response流输出业务数据字符串
	 * <p/>
	 * 如果传入的参数是一个Json字符串则直接输出。若为一个常规字符串则将其自动作为Key为appmsg的json属性值输出。(缺省追加参数：
	 * appcode=1 success=true)
	 * 
	 * @param response
	 * @param appMsg
	 *            提示信息常规字符串
	 * @param appCode
	 *            状态码
	 */
	public static void write(HttpServletResponse response, int appCode, String appMsg) {
		Dto dto = Dtos.newOutDto();
		dto.put(IMSCons.APPCODE_KEY, appCode);
		dto.put(IMSCons.APPMSG_KEY, appMsg);
		String outString = JsonUtil.toJson(dto);
		try {
			response.getWriter().write(outString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    
	
	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-real-ip");
		if (IMSUtil.isEmpty(ip)) {
			ip = request.getHeader("X-Forward-For");
		}
		if (IMSUtil.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (IMSUtil.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (IMSUtil.isEmpty(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.indexOf(ip, "0:0") != -1) {
			ip = "127.0.0.1";
		}
		return ip;
	}
	
	
	
}
