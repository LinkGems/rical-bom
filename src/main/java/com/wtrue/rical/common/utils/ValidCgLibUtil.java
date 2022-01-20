package com.wtrue.rical.common.utils;

import com.alibaba.fastjson.JSON;
import com.wtrue.rical.common.domain.BaseError;
import com.wtrue.rical.common.enums.ErrorEnum;
import com.wtrue.rical.common.utils.validate.exception.ValidException;
import com.wtrue.rical.common.utils.validate.thread.ValidThreadLocal;
import com.wtrue.rical.common.utils.test.Goal;
import com.wtrue.rical.common.utils.test.Kobe;
import com.wtrue.rical.common.utils.validate.FuncValid;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/19 2:58 PM
 */
public class ValidCgLibUtil implements MethodInterceptor {

    private FuncValid funcValid;
    private FuncValid funcValidProxy;

    private Boolean isValid = true;

    private BaseError error = new BaseError();

    //重写拦截方法

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy){
        if(ValidThreadLocal.isValid()){
            try{
                return method.invoke(funcValid, objects);
            }catch (ValidException | IllegalAccessException | InvocationTargetException e){
                isValid = false;
                error.setCode(ErrorEnum.PARAM_ERROR.getCode());
                error.setMessage(e.getMessage());
            }

        }
        return null;
    }

    private Boolean b(){
        return ValidThreadLocal.isValid();
    }

    public FuncValid getProxy(String objName, Supplier getObj){
        funcValid = new FuncValid(objName, getObj);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(FuncValid.class);
        enhancer.setCallback(this);
        funcValidProxy = (FuncValid)enhancer.create(new Class[]{String.class, Supplier.class}, new Object[]{objName, getObj});
        return funcValidProxy;
    }


    public static void main(String[] args) {
        Kobe kobe = new Kobe();
        kobe.setRun("mamba");
        Goal goal = new Goal();
        goal.setType(2);
        goal.setStep(2);
        kobe.setGoal(goal);
        ValidCgLibUtil validJdkUtil = new ValidCgLibUtil();
        FuncValid k = validJdkUtil
                .getProxy("k", () -> kobe)
                .notNull("")
                .notNull("goal")
                .sub("goal")
                .ifAIsBThenCMustD("type", 2, "step", 3);
        System.out.println(k.isValid());;
        System.out.println(JSON.toJSONString(k.getError()));;
    }
}
