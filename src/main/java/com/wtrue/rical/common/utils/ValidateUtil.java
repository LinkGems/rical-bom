package com.wtrue.rical.common.utils;

import com.wtrue.rical.common.domain.BaseError;
import com.wtrue.rical.common.enums.ErrorEnum;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/3/21 8:24 PM
 */
public class ValidateUtil {

    private boolean valid = true;
    private BaseError error = new BaseError();
    private String baseObjectName;
    private Object baseObject;
    private Map<String, Object> localValues = new HashMap();

    /**
     * 无参构造方法，满足简单判断
     *
     * 无基础对象，不能用于对象属性（field）判断
     * 可搭配表达式校验使用
     */
    public ValidateUtil(){
    }

    /**
     * 有参构造函数，传入对象用于初始化
     * @param objName
     * @param sup 传入对象或者获取对象过程
     */
    public ValidateUtil(String objName, Supplier sup){
        try {
            baseObject = sup.get();
            if(baseObject == null){
                populateError("'"+objName+"' is null");
            }
            baseObjectName = objName;
        }catch (NullPointerException npe){
            populateError("NPE in the process of getting '"+objName+"'");
        }
    }

    /**
     * 对象属性非空校验
     *
     * 1、属性对象非空校验
     * 2、String 校验null和""
     * 3、List 校验null和size==0
     *
     * @param fieldName
     * @return
     */
    public ValidateUtil notNull(String fieldName){
        // assert
        if(baseObject == null || !this.valid){
            return this;
        }
        // main
        try {
            Field field = baseObject.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object fieldValue = field.get(baseObject);
            if(fieldValue == null){
                populateError("'"+baseObjectName+"#"+fieldName+"' should not be null");
            }else if(fieldValue instanceof String && StringUtil.isEmpty((String) fieldValue)){
                populateError("'"+baseObjectName+"#"+fieldName+"' should not be empty");
            }else if(fieldValue instanceof List && ((List<?>) fieldValue).size() <= 0){
                populateError("'"+baseObjectName+"#"+fieldName+"' should not be empty");
            }else{
                this.localValues.put(fieldName, fieldValue);
            }
        } catch (NoSuchFieldException e) {
            populateError("there is not a filed named '"+baseObjectName+"#"+fieldName+"'");
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 对象属性字符长度校验
     * @param fieldName
     * @param max
     * @return
     */
    public ValidateUtil stringMaxLength(String fieldName, long max){
        // assert
        if(baseObject == null || !this.valid || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        if(localValues.get(fieldName) instanceof String){
            String toValidate = localValues.get(fieldName).toString();
            if(toValidate.length() > max){
                populateError("length of '"+baseObjectName+"#"+fieldName+"' should less then '"+max+"'");
            }
        }else{
            populateError("'"+baseObjectName+"#"+fieldName+"' is not a string");
        }

        return this;
    }

    /**
     * 对象属性最大值校验
     * @param fieldName
     * @param max
     * @return
     */
    public ValidateUtil maxLong(String fieldName, long max){
        // assert
        if(baseObject == null || !this.valid || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        try {
            Long toValidate = Long.valueOf(localValues.get(fieldName).toString());
            if(toValidate > max){
                populateError("value of '"+baseObjectName+"#"+fieldName+"' should less then '"+max+"'");
            }
        }catch (NumberFormatException e){
            populateError("'"+baseObjectName+"#"+fieldName+"' can not cast to Long");
        }
        return this;
    }

    /**
     * 对象属性最小值校验
     * @param fieldName
     * @param min
     * @return
     */
    public ValidateUtil minLong(String fieldName, long min){
        // assert
        if(baseObject == null || !this.valid || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        try {
            Long toValidate = Long.valueOf(localValues.get(fieldName).toString());
            if(toValidate < min){
                populateError("value of '"+baseObjectName+"#"+fieldName+"' should more then '"+min+"'");
            }
        }catch (NumberFormatException e){
            populateError("'"+baseObjectName+"#"+fieldName+"' can not cast to Long");
        }
        return this;
    }

    /**
     * 对象属性在两值之间校验
     * @param fieldName
     * @param min
     * @param max
     * @return
     */
    public ValidateUtil betweenLong(String fieldName, long min, long max){
        // assert
        if(baseObject == null || !this.valid){
            return this;
        }
        // main
        minLong(fieldName, min);
        maxLong(fieldName, max);
        return this;
    }

    /**
     * 集合最大数量
     * @param fieldName
     * @param max
     * @return
     */
    public ValidateUtil listMaxSize(String fieldName, long max){
        // assert
        if(baseObject == null || !this.valid || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        try{
            List list = (List) localValues.get(fieldName);
            if(list.size()>max){
                populateError("size of '"+baseObjectName+"#"+fieldName+"' should less then '"+max+"'");
            }
        }catch (Exception e){
            populateError("'"+baseObjectName+"#"+fieldName+"' can not cast to List");
        }
        return this;
    }

    /**
     * offset和Limit校验，并赋默认值
     * @param defaultOffset
     * @param defaultLimit
     * @return
     */
    public ValidateUtil offsetAndLimit(int defaultOffset, int defaultLimit){
        // assert
        if(baseObject == null || !this.valid){
            return this;
        }
        try {
            Field field = baseObject.getClass().getDeclaredField("offset");
            field.setAccessible(true);
            Object offsetObj = field.get(baseObject);
            if(offsetObj == null){
                field.set(baseObject, defaultOffset);
            }else{
                Long offset = Long.valueOf(offsetObj.toString());
                if(offset < 0){
                    populateError("value of '"+baseObjectName+"#offset' should more then '0'");
                    return this;
                }
            }
        } catch (NoSuchFieldException e) {
            populateError("there is not a filed named '"+baseObjectName+"#offset'");
            return this;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        try {
            Field field = baseObject.getClass().getDeclaredField("limit");
            field.setAccessible(true);
            Object limitObj = field.get(baseObject);
            if(limitObj == null){
                field.set(baseObject, defaultLimit);
            }else{
                Integer limit = Integer.valueOf(limitObj.toString());
                if(limit < 1 || limit > 100){
                    populateError("value of '"+baseObjectName+"#limit' should between '1' and '100', [1,100]");
                    return this;
                }
            }
        } catch (NoSuchFieldException e) {
            populateError("there is not a filed named '"+baseObjectName+"#limit'");
            return this;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * 表达式校验，支持无参构造函数校验
     * @param expressionName
     * @param sup
     * @return
     */
    public ValidateUtil expression(String expressionName, Supplier<Boolean> sup){
        // assert
        if(!this.valid){
            return this;
        }
        try {
            Boolean obj = sup.get();
            if(obj == null){
                populateError("'"+baseObjectName+"#"+expressionName+"' expression is null");
            }
            if(!obj){
                populateError("'"+baseObjectName+"#"+expressionName+"' expression is illegal");
            }
        }catch (NullPointerException npe){
            populateError("NPE in the process of getting '"+baseObjectName+"#"+expressionName+"' expression");
        }
        return this;
    }

    /**
     * 填充BaseError异常
     * @param msg
     */
    private void populateError(String msg){
        this.valid = false;
        this.error.setCode(ErrorEnum.PARAM_ERROR.getCode());
        this.error.setMessage(msg);
    }

    public boolean isValid() {
        return valid;
    }

    public BaseError getError() {
        return error;
    }
}
