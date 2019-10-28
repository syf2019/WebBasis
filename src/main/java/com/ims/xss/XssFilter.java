package com.ims.xss;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
/**
 * 
 * 类名:com.ims.xss.XssFilter
 * 描述:xss拦截器
 * 编写者:陈骑元
 * 创建时间:2019年3月1日 下午10:30:10
 * 修改说明:
 */
@WebFilter
@Component
public class XssFilter implements Filter {
	 @Override
	    public void init(FilterConfig filterConfig) throws ServletException {

	    }

	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {
	        HttpServletRequest req = (HttpServletRequest) request;
	        XssAndSqlHttpServletRequestWrapper xssRequestWrapper = new XssAndSqlHttpServletRequestWrapper(req);
	        chain.doFilter(xssRequestWrapper, response);
	    }

	    @Override
	    public void destroy() {

	    }

	     /**
	     * 过滤json类型的
	     * @param builder
	     * @return
	     */
	    @Bean
	    @Primary
	    public ObjectMapper xssObjectMapper(Jackson2ObjectMapperBuilder builder) {
	        //解析器
	        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
	        //注册xss解析器
	        SimpleModule xssModule = new SimpleModule("XssStringJsonSerializer");
	        xssModule.addSerializer(new XssStringJsonSerializer());
	        objectMapper.registerModule(xssModule);
	        //返回
	        return objectMapper;
	    }

}
