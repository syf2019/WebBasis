package com.ims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
/**
 * 
 * 类名:com.ims.Application
 * 描述:spring boot 启动文件
 * 编写者:陈骑元
 * 创建时间:2017年9月21日 下午3:03:32
 * 修改说明:
 */
@EnableConfigurationProperties
@SpringBootApplication
@EnableAsync 
public class WebplusApplication extends SpringBootServletInitializer {
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebplusApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebplusApplication.class, args);
	}
}