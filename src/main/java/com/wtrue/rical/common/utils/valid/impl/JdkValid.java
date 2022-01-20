package com.wtrue.rical.common.utils.valid.impl;

import com.alibaba.fastjson.JSON;
import com.wtrue.rical.common.utils.ValidCgLibUtil;
import com.wtrue.rical.common.utils.test.Goal;
import com.wtrue.rical.common.utils.test.Kobe;
import com.wtrue.rical.common.utils.valid.IValid2;
import com.wtrue.rical.common.utils.validate.FuncValid;
import lombok.Setter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/20 3:57 PM
 */
//@Setter
public class JdkValid implements InvocationHandler {
    private RealValid realValid;
    private IValid2 pxy;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入代理");
        Object result = new Object();
        if(realValid.getValid()){
            result = method.invoke(realValid, args);
            return pxy;
//            if(result != null){
//                realValid = (RealValid)result;
//            }
        }
        return result;
    }

    public IValid2 getProxy(){
        realValid = new RealValid();
//        realValid = (RealValid)Proxy.newProxyInstance(realValid.getClass().getClassLoader(), realValid.getClass().getInterfaces(), this);
//        realValidProxy = (JdkValid)Proxy.newProxyInstance(RealValid.class.getClassLoader(), RealValid.class.getInterfaces(), this);
        pxy = (IValid2)Proxy.newProxyInstance(
                RealValid.class.getClassLoader(),
                RealValid.class.getInterfaces(),
                this);
        return pxy;
    }

    public static void main(String[] args) {
        Kobe kobe = new Kobe();
        kobe.setRun("mamba");
        Goal goal = new Goal();
        goal.setType(2);
        goal.setStep(2);
        kobe.setGoal(goal);
        IValid2 realValid = new JdkValid()
                .getProxy()
                .notNull("run")
                .notNull("goal");
//        k.notNull("");
//        k.notNull("goal");
//        k.sub("goal");
//        k.ifAIsBThenCMustD("type", 2, "step", 3);
//        System.out.println(realValid.getValid());;
//        System.out.println(JSON.toJSONString(k.getError()));;
    }
}
