package spring.boot.demo.aop;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

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
	
    /** 
     * 前置通知，方法调用前被调用 
     * @param joinPoint 
     */  
    @Before("webLog()")  
    public void doBeforeAdvice(JoinPoint joinPoint){  
    	try{
    		System.out.println("我是前置通知!!!");  
    		//获取目标方法的参数信息  
    		Object[] obj = joinPoint.getArgs();  
    		//AOP代理类的信息  
    		joinPoint.getThis();  
    		//代理的目标对象  
    		joinPoint.getTarget();  
    		//用的最多 通知的签名  
    		Signature signature = joinPoint.getSignature();  
    		//代理的是哪一个方法  
    		System.out.println(signature.getName());  
    		//AOP代理类的名字  
    		System.out.println(signature.getDeclaringTypeName());  
    		//AOP代理类的类（class）信息  
    		signature.getDeclaringType();  
    		//获取RequestAttributes  
    		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();  
    		//从获取RequestAttributes中获取HttpServletRequest的信息  
    		HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);  
    		//如果要获取Session信息的话，可以这样写：  
    		//HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);  
    		Enumeration<String> enumeration = request.getParameterNames();  
    		Map<String,String> parameterMap = new HashMap<String, String>();  
    		while (enumeration.hasMoreElements()){  
    			String parameter = enumeration.nextElement();  
    			parameterMap.put(parameter,request.getParameter(parameter));  
    		}  
    		String str = JSON.toJSONString(parameterMap);  
    		if(obj.length > 0) {  
    			System.out.println("请求的参数信息为："+str);  
    		}
    		for (Iterator<String> it = parameterMap.keySet().iterator(); it.hasNext();) {
    			String key = (String) it.next();
    			System.out.println(parameterMap.get(key));
    			
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }  

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		logger.info("RESPONSE : " + ret);
		logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
	}
}