package com.ims.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.StringUtils;
/**
 * 
 * 类名:com.ims.xss.XssAndSqlHttpServletRequestWrapper
 * 描述:xss攻击处理
 * 编写者:陈骑元
 * 创建时间:2019年3月1日 下午10:27:10
 * 修改说明:
 */
public class XssAndSqlHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private HttpServletRequest request;

    public XssAndSqlHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String name) {
        String value = request.getParameter(name);
        if (!StringUtils.isEmpty(value)) {
            value = StringEscapeUtils.escapeHtml4(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        }
        for (int i = 0; i < parameterValues.length; i++) {
            String value = parameterValues[i];
            parameterValues[i] = StringEscapeUtils.escapeHtml4(value);
        }
        return parameterValues;
    }

}
