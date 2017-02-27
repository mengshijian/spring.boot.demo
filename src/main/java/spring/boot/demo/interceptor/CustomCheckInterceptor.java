package spring.boot.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * ClassName: CustomChekInterceptor 
 * @Description: 用户授权拦截器
 * @author mengsj
 * @date 2016年11月24日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
@Configuration 
public class CustomCheckInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomCheckInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		// 1. 用于过滤区分 mvc:resources
		if ((handler == null) || (!HandlerMethod.class.isAssignableFrom(handler.getClass()))) {
			return true;
		}
		String loginURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		logger.info(loginURL + request.getRequestURI());
		return true;
	}
}