package spring.boot.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import spring.boot.demo.interceptor.CustomCheckInterceptor;


@Configuration   //标注此文件为一个配置项，spring boot才会扫描到该配置。该注解类似于之前使用xml进行配置
public class CustomInterceptorConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private CustomCheckInterceptor customCheckInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(customCheckInterceptor).addPathPatterns("/**")
				.excludePathPatterns("/wx/index",
									 "/wx/authwx",
									 "/wx/getOpenId",
									 "/wx/getCtx",
									 "/**.html"); // 对来自/user/** 这个链接来的请求进行拦截
    }
}