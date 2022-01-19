package com.wtrue.rical.common.utils.validate;

import com.wtrue.rical.common.utils.StringUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/19 10:33 AM
 */
public class ValidUtil extends SpecialValid implements InvocationHandler {

    /**
     * 有参构造函数，传入对象用于初始化
     * @param objName
     * @param getObj 传入对象或者获取对象过程
     */
    public ValidUtil(String objName, Supplier getObj){
        try {
            if(StringUtil.isEmpty(objName)){
                populateError("baseObjName is empty");
            }
            Object curObj = getObj.get();
            if(curObj == null){
                populateError("'"+ objName +"' is null");
            }
            ValidObj validObj = new ValidObj(objName, curObj);
            this.objStack.push(validObj);
            this.curValidObj = validObj;
        }catch (NullPointerException npe){
            populateError("NPE in the process of getting '%s'", objName);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(this.valid){
            return method.invoke(this, args);
        }
        return this;
    }
}
