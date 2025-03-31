package com.gt.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * @author rainbow
 */
@Aspect
@Component
@Slf4j
public class SpSecretAop {

    
    @Pointcut("execution(@com.gt.common.aop.SpSecretAnnotation * *(..))")
    private void checkSecret() {
        // do nothing
    }

    @Before("checkSecret()")
    public void authDeviceBefore(JoinPoint joinPoint) {
        log.info("前置");
    }

    @After("checkSecret()")
    public void checkSecretAfter(JoinPoint joinPoint) {
        log.info("后置");
    }

    public static void main(String[] args) {
        long timestamp = System.currentTimeMillis();
        String digest = DigestUtils.md5Hex("1JKRG2QBFE42LF712VUE6IPRYAQ50H7Q" + "NH0QZ6LT3HU3HD08VULT0ARLCK69T8KB" + timestamp);
        System.out.println(timestamp);
        System.out.println(digest);
    }
}
