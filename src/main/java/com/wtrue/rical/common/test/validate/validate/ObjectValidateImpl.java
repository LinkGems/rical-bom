package com.wtrue.rical.common.test.validate.validate;

import com.wtrue.rical.common.utils.StringUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:33 PM
 */
class ObjectValidateImpl extends ValidateObject implements IObjectValidate {

    public ObjectValidateImpl(String objName, Object object) {
        super(objName, object);
    }

    @Override
    public IObjectValidate sub(String fieldName) {
        return this;
    }

    @Override
    public IObjectValidate sup() {
        return this;
    }

    @Override
    public IObjectValidate supSub(String fieldName) {
        return this;
    }

    @Override
    public ValidateUtil valid() {
        return new ValidateUtil();
    }

    @Override
    public IObjectValidate notNull(String fieldName) throws ValidateException {
        Object fieldValue = getFieldMapData(fieldName);
        if(fieldValue != null){
            return this;
        }
        fieldValue = reflectGetValue(fieldName);
        if(fieldValue == null){
            throw new ValidateException(
                    String.format("'%s#%s' should not be null", super.getObjName(), fieldName));
        }else if(fieldValue instanceof String && StringUtil.isEmpty((String) fieldValue)){
            throw new ValidateException(
                    String.format("'%s#%s' should not be empty", super.getObjName(), fieldName));
        }else if(fieldValue instanceof List && ((List<?>) fieldValue).size() <= 0){
            throw new ValidateException(
                    String.format("'%s#%s' should not be empty", super.getObjName(), fieldName));
        }else{
            setFieldMapData(fieldName, fieldValue);
        }
        return this;
    }

    public Object reflectGetValue(String fieldName) throws ValidateException {
        try{
            Object obj = super.getCurObj();
            if(obj instanceof Map){
                return ((Map<String, Object>) obj).get(fieldName);
            }
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            throw new ValidateException(
                    String.format("'%s' does not have field named '%s'", super.getObjName(), fieldName));
        } catch (IllegalAccessException e) {
            throw new ValidateException(
                    String.format("can not access field '%s#%s'", super.getObjName(), fieldName));
        }
    }
}
