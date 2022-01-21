package com.wtrue.rical.common.utils.validate;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/21 5:47 PM
 */
public class ValidUtil implements MethodInterceptor {

    private List<ValidObjUtil> validObjUtils;
    private ValidExpUtil validExpUtil;

    @Override
    public Object intercept(Object sub, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        return methodProxy.invokeSuper(sub, objects);
        for(ValidObjUtil validObjUtil : validObjUtils){
            ValidObjUtil result = (ValidObjUtil)method.invoke(validObjUtil, objects);
        }
//        return method.invoke(validExpUtil, objects);
        return null;
    }

    public ValidObjUtil objProxy(String objName, Supplier getObj){
        Object obj = getObj.get();
        if(obj != null && obj instanceof List){
            List objList = (List) obj;
            validObjUtils = new ArrayList<>();
            for(int i=0; i<objList.size(); i++){
                int objIndex = i;
                String newObjName = String.format("%s_%d", objName, objIndex);
                validObjUtils.add(new ValidObjUtil(newObjName, ()->objList.get(objIndex)));
            }
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ValidObjUtil.class);
        enhancer.setCallback(new ValidUtil());
        return (ValidObjUtil)enhancer.create(new Class[]{String.class, Supplier.class}, new Object[]{objName, getObj});
    }

    public ValidExpUtil expProxy(){
        validExpUtil = new ValidExpUtil();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ValidExpUtil.class);
        enhancer.setCallback(this);
        return (ValidExpUtil)enhancer.create();
    }

    public static void main(String[] args) {
//        new ValidUtil().expProxy()
//                .expression("1", ()->1==1)
//                .expression("2", ()->1!=1)
//                .expression("3", ()->1==1);
        List<kobe> list = new ArrayList<>();
        kobe kobe = new kobe();
        kobe.setRun("run");
        kobe.setGoal("goal");
        list.add(kobe);
        list.add(new kobe());
        kobe.setRun(null);
        list.add(new kobe());
        new ValidUtil()
                .objProxy("kobe", ()->list)
                .notNull("run","goal");
    }
}
class kobe{
    private String run;
    private String goal;

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
