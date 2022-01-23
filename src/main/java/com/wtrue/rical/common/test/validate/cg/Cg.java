package com.wtrue.rical.common.test.validate.cg;

import com.wtrue.rical.common.test.validate.ValidateUtil;
import com.wtrue.rical.common.utils.validate.ValidUtil;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 12:09 PM
 */
public class Cg extends CgSup implements MethodInterceptor {

    private ValidateUtil use;

    @Override
    public Object intercept(Object sub, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(valid){
            Method[] methods = ValidateUtil.class.getMethods();
            Method subMethod = Arrays.stream(methods).filter(m -> method.getName().equals(m.getName()) && method.getParameterCount() == m.getParameterCount()).findFirst().orElse(null);
            if(subMethod != null){
                for(ValidateUtil v : list){
                    ValidateUtil result = (ValidateUtil)subMethod.invoke(v, objects);
//                    valid = false;
                }
            }
        }

        return null;
    }

    public ValidateUtil obj(List<ValidateUtil> list){
        super.list = list;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ValidateUtil.class);
        enhancer.setCallback(this);
        use = (ValidateUtil)enhancer.create();
        return use;
    }

    public static void main(String[] args) {
        List<ValidateUtil> list = new ArrayList<>();
        list.add(new ValidateUtil());
        list.add(new ValidateUtil());
        new Cg()
                .obj(list)
                .notNull("1")
                .notNull("2");
    }
}
