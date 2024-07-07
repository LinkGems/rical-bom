package org.linkgems.rical.common.eve.aspect;

import cn.hutool.core.util.StrUtil;
import org.apache.dubbo.rpc.RpcContext;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.linkgems.rical.common.eve.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/21 2:13 PM
 */
@Aspect
@Configuration
public class ProviderAspect {

    @Value("${dubbo.application.id}")
    private String appKey;

    /**
     * RPC包拦截
     * <p>
     * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)
     * <p>
     * 修饰符匹配（modifier-pattern?）
     * 返回值匹配（ret-type-pattern）可以为*表示任何返回值,全路径的类名等
     * 类路径匹配（declaring-type-pattern?）
     * 方法名匹配（name-pattern）可以指定方法名 或者 *代表所有, set* 代表以set开头的所有方法
     * 参数匹配（(param-pattern)）可以指定具体的参数类型，多个参数间用“,”隔开，各个参数也可以用“*”来表示匹配任意类型的参数，如(String)表示匹配一个String参数的方法；(*,String) 表示匹配有两个参数的方法，第一个参数可以是任意类型，而第二个参数是String类型；可以用(..)表示零个或多个任意参数
     * 异常类型匹配（throws-pattern?）
     * 其中后面跟着“?”的是可选项
     */
    @Pointcut("execution(public * *.linkgems..*.provider.*.*(..)) || execution(public * *.linkgems..*.controller.*.*(..))")
    public void providerMethod() {
    }

    @Before("providerMethod()")
    public void before() {
        String consumerAppKey = RpcContext.getContext().getAttachment("providerAppKey");
        if (StrUtil.isNotEmpty(consumerAppKey)) {
            RpcContext.getContext().setAttachment("consumerAppKey", consumerAppKey);
        }
        RpcContext.getContext().setAttachment("providerAppKey", this.appKey);
        ThreadLocalUtil.set("appKey", this.appKey);
    }

    @AfterReturning("providerMethod()")
    public void afterReturning() {
        ThreadLocalUtil.removeThreadLocal();
    }
}
