package com.wtrue.rical.common.test.validate.jdk;

import com.alibaba.fastjson.JSON;
import com.wtrue.rical.common.test.validate.IValidate;
import com.wtrue.rical.common.test.validate.ValidateUtil;
import com.wtrue.rical.common.test.validate.cg.Cg;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 12:58 PM
 */
public class Jdk extends JdkSup implements InvocationHandler {
    private IValidate obj;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("valid")){
            return valid();
        }
        if(valid){

            for(ValidateUtil v : list){
                ValidateUtil result = (ValidateUtil)method.invoke(v, args);
//                valid = false;
            }
        }
        return obj;
    }

    public IValidate obj(List<ValidateUtil> list){
        super.list = list;
        obj = (IValidate)Proxy.newProxyInstance(ValidateUtil.class.getClassLoader(), ValidateUtil.class.getInterfaces(),this);
        return obj;
    }

    private Jdk valid(){
        return this;
    }

    public static void main(String[] args) {
        List<ValidateUtil> list = new ArrayList<>();
        list.add(new ValidateUtil());
        list.add(new ValidateUtil());
        Jdk valid = new Jdk()
                .obj(list)
                .notNull("1")
                .notNull("2")
                .valid();
        System.out.println(JSON.toJSONString(valid));

    }
}
