package com.wtrue.rical.common.test.validate;

import com.wtrue.rical.common.test.validate.jdk.Jdk;
import com.wtrue.rical.common.utils.StringUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/22 1:07 PM
 */
public class ValidateUtil implements IValidate{


    /**
     * 判断对象属性非空
     * @param fieldName
     * @return
     */
    public IValidate notNull(String fieldName){
        System.out.println(this.toString());
        return this;
    }

    @Override
    public Jdk valid() {
        return null;
    }

    /**
     * 反射获取属性值
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws VException
     */
    /*private Object reflectValue(String fieldName) throws NoSuchFieldException, IllegalAccessException, VException {
        if(!isValid()){
            throw new VException(getMessage());
        }
        Object curObj = peekObj();
        Field field = curObj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(curObj);
    }*/
}
