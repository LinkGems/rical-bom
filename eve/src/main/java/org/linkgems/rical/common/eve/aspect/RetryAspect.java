package org.linkgems.rical.common.eve.aspect;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.linkgems.rical.common.adam.domain.BaseException;
import org.linkgems.rical.common.eve.domain.annotation.Retry;
import org.linkgems.rical.common.eve.domain.constant.AnnotationConstant;
import org.linkgems.rical.common.eve.domain.constant.LogMarkConstant;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @description:重试切片
 * @author: meidanlong
 * @date: 2022/11/27 5:05 PM
 */
@Slf4j
@Order(2)
@Aspect
@Component
public class RetryAspect {

    @Pointcut("@annotation(org.linkgems.rical.common.eve.domain.annotation.Retry)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object pointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        RetryDetail retryDetail = populateRetryDetail(joinPoint);
        String classMethodName = String.format(AnnotationConstant.CLASS_METHOD_NAME, retryDetail.getClazz(), retryDetail.getMethod());
        int totalRetryTimes = retryDetail.getTotalRetryTimes();
        int retryTimes = 0;
        while (retryTimes++ <= totalRetryTimes) {
            try {
                // 执行业务逻辑
                Object result = joinPoint.proceed();
                return result;
            } catch (Exception e) {
                if (totalRetryTimes == 0) {
                    throw e;
                }
                if (retryTimes == totalRetryTimes) {
                    log.error("{}{} still got exception after retry {} times", LogMarkConstant.LOG_ERROR_MARK, classMethodName, retryTimes);
                    throw new BaseException("retry with exception: " + e.getMessage(), e.getCause());
                }
                log.info("{}{} sth. wrong, attempt to retry {} times", LogMarkConstant.LOG_ERROR_MARK, classMethodName, retryTimes);
            }
            emptyWindow(retryDetail.getEmptyWindow());
        }
        throw new BaseException("retry with exception");
    }

    @SneakyThrows
    private void emptyWindow(long emptyWindow) {
        TimeUnit.MILLISECONDS.sleep(emptyWindow);
    }

    private RetryDetail populateRetryDetail(ProceedingJoinPoint joinPoint) {
        RetryDetail retryDetail = new RetryDetail();
        try {
            // 通过joinPoint获取被注解方法
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            // 1、获取注解参数值
            Retry retry = methodSignature.getMethod().getAnnotation(Retry.class);
            // 2、获取方法/类
            Method method = methodSignature.getMethod();
            retryDetail.setMethod(method.getName());
            retryDetail.setClazz(method.getDeclaringClass().getSimpleName());
            // 3、获取重试次数
            int retryTimes = Math.max(retry.times(), 0);
            if (retryTimes == 0) {
                retryTimes = Math.max(retry.value(), 0);
            }
            retryDetail.setTotalRetryTimes(Math.min(retryTimes, 10));
            // 4、空窗期
            retryDetail.setEmptyWindow(Math.min(Math.max(retry.emptyWindow(), 0), 60000));
        } catch (Exception ex) {
            log.info("{}[RetryAspect#populateRetryDetail] - exception={}, check the error.log", LogMarkConstant.LOG_ERROR_MARK, ex.getMessage());
            log.error("{}[RetryAspect#populateRetryDetail] - exception={}", LogMarkConstant.LOG_ERROR_MARK, ex.getMessage(), ex);
        }
        return retryDetail;
    }

    @Getter
    @Setter
    class RetryDetail {
        private String clazz = "unknownClass";
        private String method = "unknownMethod";
        private int totalRetryTimes = 1;
        private long emptyWindow = 0L;
    }
}
