/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.aop;

import com.mmc.flink.lidea.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 统一异常处理.
 *
 * @author Joey
 * @date 2019/3/12 15:50
 */
@Component
@Aspect
@Slf4j
public class ExceptionHander {

    public ExceptionHander() {
        log.info("ExceptionHander.ExceptionHander");
    }

    @Pointcut("execution(* com.mmc.flink.lidea.controller.DetailController.*(..))")
    public void hander() {

    }

    @Around("hander()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result;
        try {
            result = pjp.proceed();
            return result;

        } catch (Throwable t) {
            //如果没有统一日志拦截，打印堆栈信息
            log.error("an uncaught exception occur, e:", t);
            return ResultDTO.handleException(t.getMessage(), null, t);
        }
    }
}
