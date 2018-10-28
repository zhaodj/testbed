package io.github.zhaodj.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAspect {

    @Pointcut("execution(* io.github.zhaodj.aop.aspectj.App.say(..))")
    public void jointPoint() {
    }

    @Before("jointPoint()")
    public void before() {
        System.out.println("LogAspect before say");
    }

    @Around("jointPoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            System.out.println("LogAspect around say begin");
            return joinPoint.proceed();
        } finally {
            System.out.println("LogAspect around say end");
        }
    }

    @After("jointPoint()")
    public void after() {
        System.out.println("LogAspect after say");
    }

}
