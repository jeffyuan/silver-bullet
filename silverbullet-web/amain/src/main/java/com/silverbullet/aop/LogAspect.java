package com.silverbullet.aop;

import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.log.service.ISysAppLogService;
import com.silverbullet.utils.ToolUtil;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 记录日志AOP，对所有controller中非.do的方法
 * Created by jeffyuan on 2018/3/12.
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.silverbullet.controller.*.*(..))")
    public void appLog(){}

    /**
     * 调用方法之前，记录请求的内容
     * @param joinPoint
     * @throws Throwable
     */
    @Before("appLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 获取请求servlet
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        MDC.put("url", ToolUtil.getRelativeUrl(request.getRequestURL().toString()));
        MDC.put("username", ((UserInfo)SecurityUtils.getSubject().getPrincipal()).getName());
        MDC.put("ip", ToolUtil.getIpAddr(request));
        MDC.put("class_method", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        MDC.put("http_method", request.getMethod());

        // 将内容放入MDC中
        System.out.println("url : " + request.getRequestURL().toString());
        System.out.println("http_method : " + request.getMethod());
        System.out.println("ip : " + ToolUtil.getIpAddr(request));
        System.out.println("class_method : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("args : " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 请求完成后，返回的内容
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "appLog()")
    public void doAfterReturning(Object ret) throws Throwable {

        String url = (String)MDC.get("url");
        if (url.indexOf(".do") == -1) {
            MDC.put("ret", ret);
            logger.info("[APP] " + MDC.get("url"));
        }

        MDC.clear();
        System.out.println("返回值 : " + ret);
    }

    /**
     * 球球中如果存在异常
     * @param joinPoint
     */
    @AfterThrowing("appLog()")
    public void throwss(JoinPoint joinPoint){
        System.out.println("方法异常时执行.....");
    }

    /**
     * 最后执行
     * @param joinPoint，类似final，不管抛出异常或者正常退出都会执行
     */
    @After("appLog()")
    public void after(JoinPoint joinPoint){
        System.out.println("方法最后执行.....");
    }

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("appLog()")
    public Object arround(ProceedingJoinPoint pjp) {
        System.out.println("start.....");
        try {
            Object o =  pjp.proceed();

            System.out.println("方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
