package com.itheima.ssm.controller;

import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {


    private Date startTime;
    private Class clazz;
    private Method executionMethod;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysLogService sysLogService;

    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        startTime = new Date();
        clazz = jp.getTarget().getClass();
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        if(args==null||args.length==0){
            executionMethod = clazz.getMethod(methodName);
        }else{
            Class[] classArgs = new Class[args.length];
            for(int i =0 ;i<args.length;i++){
                classArgs[i] = args[i].getClass();
            }
            executionMethod = clazz.getMethod(methodName,classArgs);
        }
    }

    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp){
        long time = new Date().getTime() - startTime.getTime();
        String url = "";
        if(clazz!=null&&executionMethod!=null&&clazz!=LogAop.class){
            RequestMapping classMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classMapping!=null){
                String[] classValue = classMapping.value();
                RequestMapping methodMapping = executionMethod.getAnnotation(RequestMapping.class);
                if(methodMapping!=null){
                    String[] methodValue = methodMapping.value();
                    url = classValue[0] + methodValue[0];

                    String ip = request.getRemoteAddr();

                    SecurityContext context = SecurityContextHolder.getContext();
                    User user = (User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();

                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time); //执行时长
                    sysLog.setIp(ip);
                    sysLog.setMethod("[类名] " + clazz.getName() + "[方法名] " + executionMethod.getName());
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setVisitTime(startTime);

                    //调用Service完成操作
                    sysLogService.save(sysLog);
                }
            }
        }
    }
}
