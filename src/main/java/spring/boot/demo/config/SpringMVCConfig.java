package spring.boot.demo.config;

import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

//@Configuration
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

	/*
	 * 解决转码问题Spring @responseBody 问题
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #configureMessageConverters(java.util.List)
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(getConverter());
	}

	public StringHttpMessageConverter getConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter();
		converter.setDefaultCharset(Charset.forName("UTF-8"));
		return converter;
	}

	// 局部时间配置
	// @InitBinder("date")
	// public void initBinder(WebDataBinder binder){
	// binder.registerCustomEditor(Date.class,new CustomDateEditor(new
	// SimpleDateFormat("yyyyMMdd"), true, 8));
	// }

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	/**
	 * 增加字符串转日期的功能
	 */
	@PostConstruct
	public void initEditableValidation() {
		ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
				.getWebBindingInitializer();
		if (initializer.getConversionService() != null) {
			GenericConversionService genericConversionService = (GenericConversionService) initializer
					.getConversionService();
			genericConversionService.addConverter(new StringToDateConverter());
		}

	}
}
