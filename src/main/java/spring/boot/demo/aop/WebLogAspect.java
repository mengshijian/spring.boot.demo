package spring.boot.demo.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class WebLogAspect {
	private Logger logger = Logger.getLogger(getClass());
	ThreadLocal<Long> startTime = new ThreadLocal<>();

	@Pointcut("execution(public * spring.boot.demo.web..*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		
		//ServletRequestAttributes may be null, check it first.
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse res = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		res.setCharacterEncoding("UTF-8");
		
		System.out.println("before" + request.getParameter("param"));
		startTime.set(System.currentTimeMillis());
		// 省略日志记录内容
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		logger.info("RESPONSE : " + ret);
		logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
	}
	public static void main(String[] args) {
		System.out.println(Boolean.valueOf("true") == true);
	}
}