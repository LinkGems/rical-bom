package com.wtrue.rical.common.utils.validate;

import com.alibaba.fastjson.JSON;
import com.wtrue.rical.common.domain.BaseError;
import com.wtrue.rical.common.enums.ErrorEnum;
import com.wtrue.rical.common.utils.ValidCgLibUtil;
import com.wtrue.rical.common.utils.test.Goal;
import com.wtrue.rical.common.utils.test.Kobe;
import com.wtrue.rical.common.utils.validate.domain.BaseValidObj;
import com.wtrue.rical.common.utils.StringUtil;
import com.wtrue.rical.common.utils.validate.exception.ValidException;
import com.wtrue.rical.common.utils.validate.thread.ValidThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import javax.xml.bind.ValidationException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/19 10:33 AM
 */

@Slf4j
public class FuncValid implements MethodInterceptor {
    private FuncValid funcValid;
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy){
        if(ValidThreadLocal.isValid()){
            try{
                return method.invoke(funcValid, objects);
            }catch (ValidException | IllegalAccessException | InvocationTargetException e){
//                isValid = false;
//                error.setCode(ErrorEnum.PARAM_ERROR.getCode());
//                error.setMessage(e.getMessage());
            }

        }
        return null;
    }

    public FuncValid getProxy(String objName, Supplier getObj){
        funcValid = new FuncValid(objName, getObj);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(FuncValid.class);
        enhancer.setCallback(this);
        return  (FuncValid)enhancer.create(new Class[]{String.class, Supplier.class}, new Object[]{objName, getObj});
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

    /**
     * 有参构造函数，传入对象用于初始化
     * @param objName
     * @param getObj 传入对象或者获取对象过程
     */
    public FuncValid(String objName, Supplier getObj){
        try {
            ValidThreadLocal.removeValidThreadLocal();
            if(StringUtil.isEmpty(objName)){
                ValidThreadLocal.setError("baseObjName is empty");
            }
            if(getObj == null){
                ValidThreadLocal.setError("function getObj is null");
            }
            Object curObj = getObj.get();
            if(curObj == null){
                ValidThreadLocal.setError("'"+ objName +"' is null");
            }
            BaseValidObj baseValidObj = new BaseValidObj(objName, curObj);
            ValidThreadLocal.initTL(baseValidObj);
        }catch (NullPointerException npe){
            ValidThreadLocal.setError("NPE in the process of getting '%s'", objName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取子对象
     * @param fieldName
     * @return
     */
    public FuncValid sub(String fieldName){
        // assert
        if(!this.isValid() || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        BaseValidObj subBaseValidObj = new BaseValidObj(fieldName, getTopData(fieldName));
        ValidThreadLocal.push(subBaseValidObj);
        return this;
    }

    /**
     * 获取父对象
     * @return
     */
    public FuncValid sup(){
        // assert
        if(!this.isValid()){
            return this;
        }
        // main
        ValidThreadLocal.pop();
        return this;
    }

    /**
     * 获取同级对象
     * @return
     */
    public FuncValid supSub(String fieldName){
        // assert
        if(!this.isValid()){
            return this;
        }
        // main
        sup();
        sub(fieldName);
        return this;
    }

    /**
     * 批量判空
     * @param fieldNames
     * @return
     */
    public FuncValid notNull(String... fieldNames){
        for (String fieldName : fieldNames) {
            this.notNull(fieldName);
        }
        return this;
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
    public FuncValid notNull(String fieldName){

        // assert
        if(!this.isValid()){
            return this;
        }
        // main
        Object fieldValue = ValidThreadLocal.getTopData(fieldName);
        if(fieldValue != null){
            return this;
        }
        try {
            Object curObj = ValidThreadLocal.peekObj();
            Field field = curObj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            fieldValue = field.get(curObj);
            // assert
            if(fieldValue == null){
                ValidThreadLocal.setError("'%s#%s' should not be null", ValidThreadLocal.peekObjName(), fieldName);
            }else if(fieldValue instanceof String && StringUtil.isEmpty((String) fieldValue)){
                ValidThreadLocal.setError("'%s#%s' should not be empty", ValidThreadLocal.peekObjName(), fieldName);
            }else if(fieldValue instanceof List && ((List<?>) fieldValue).size() <= 0){
                ValidThreadLocal.setError("'%s#%s' should not be empty", ValidThreadLocal.peekObjName(), fieldName);
            }else{
                ValidThreadLocal.setTopData(fieldName, fieldValue);
            }
        } catch (NoSuchFieldException e) {
//            throw new ValidException("there is not a filed named '%s#%s'", ValidThreadLocal.peekObjName(), fieldName);
            ValidThreadLocal.setError("there is not a filed named '%s#%s'", ValidThreadLocal.peekObjName(), fieldName);
        } catch (IllegalAccessException e) {
            ValidThreadLocal.setError("'%s#%s' access illegal", ValidThreadLocal.peekObjName(), fieldName);
        }
        return this;
    }

    // common

    /**
     * 如果A的值为B，那么C的值必须为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public FuncValid ifAIsBThenCMustD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        // assert
        if(!this.isValid()){
            return this;
        }
        // main
        if(JSON.toJSONString(getTopData(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && !JSON.toJSONString(getTopData(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            ValidThreadLocal.setError("%s is %s, but %s is not %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }

    /**
     * 如果A的值不为B，那么C的值必须为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public FuncValid ifAIsNotBThenCMustD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        // assert
        if(!this.isValid()){
            return this;
        }
        // main
        if(!JSON.toJSONString(getTopData(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && !JSON.toJSONString(getTopData(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            ValidThreadLocal.setError("%s is not %s, but %s is not %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }

    /**
     * 如果A的值为B，那么C的值必须不能为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public FuncValid ifAIsBThenCMustNotD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        // assert
        if(!this.isValid()){
            return this;
        }
        // main
        if(JSON.toJSONString(getTopData(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && JSON.toJSONString(getTopData(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            ValidThreadLocal.setError("%s is %s, but %s is %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }

    /**
     * 如果A的值为B，那么C的值必须不能为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public FuncValid ifAIsNotBThenCMustNotD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        // assert
        if(!this.isValid()){
            return this;
        }
        // main
        if(!JSON.toJSONString(getTopData(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && JSON.toJSONString(getTopData(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            ValidThreadLocal.setError("%s is not %s, but %s is %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }

    // Special

    /**
     * 对象属性字符长度校验
     * @param fieldName
     * @param max
     * @return
     */
    public FuncValid stringMaxLength(String fieldName, long max){
        // assert
        if(!this.isValid() || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        Object value = getTopData(fieldName);
        if(value instanceof String){
            String toValidate = value.toString();
            if(toValidate.length() > max){
                ValidThreadLocal.setError("length of '%s#%s' should less then '%s'", ValidThreadLocal.peekObjName(), fieldName, String.valueOf(max));
            }
        }else{
            ValidThreadLocal.setError("'%s#%s' is not a string", ValidThreadLocal.peekObjName(), fieldName);
        }

        return this;
    }

    /**
     * 对象属性最大值校验
     * @param fieldName
     * @param max
     * @return
     */
    public FuncValid maxLong(String fieldName, long max){
        // assert
        if(!this.isValid() || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        try {
            Long toValidate = Long.valueOf(getTopData(fieldName).toString());
            if(toValidate > max){
                ValidThreadLocal.setError("value of '%s#%s' should less then '%s'", ValidThreadLocal.peekObjName(), fieldName, String.valueOf(max));
            }
        }catch (NumberFormatException e){
            ValidThreadLocal.setError("'%s#%s' can not cast to Long", ValidThreadLocal.peekObjName(), fieldName);
        }
        return this;
    }

    /**
     * 对象属性最小值校验
     * @param fieldName
     * @param min
     * @return
     */
    public FuncValid minLong(String fieldName, long min){
        // assert
        if(!this.isValid() || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        try {
            Long toValidate = Long.valueOf(getTopData(fieldName).toString());
            if(toValidate < min){
                ValidThreadLocal.setError("value of '%s#%s' should more then '%s'", ValidThreadLocal.peekObjName(), fieldName, String.valueOf(min));
            }
        }catch (NumberFormatException e){
            ValidThreadLocal.setError("'%s#%s' can not cast to Long", ValidThreadLocal.peekObjName(), fieldName);
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
    public FuncValid betweenLong(String fieldName, long min, long max){
        // assert
        if(!this.isValid()){
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
    public FuncValid listMaxSize(String fieldName, long max){
        // assert
        if(!this.isValid() || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        try{
            List list = (List) getTopData(fieldName);
            if(list.size()>max){
                ValidThreadLocal.setError("size of '%s#%s' should less then '%s'", ValidThreadLocal.peekObjName(), fieldName, String.valueOf(max));
            }
        }catch (Exception e){
            ValidThreadLocal.setError("'%s#%s' can not cast to List", ValidThreadLocal.peekObjName(), fieldName);
        }
        return this;
    }

    /**
     * 先判空，再取值
     * @param fieldName
     * @return
     */
    private Object getTopData(String fieldName){
        notNull(fieldName);
        if(ValidThreadLocal.isValid()){
            return ValidThreadLocal.getTopData(fieldName);
        }else{
            return null;
        }
    }
    /**
     * 获取属性值
     * @param fieldName
     * @return
     */
    private Object getFieldValue(String fieldName){
        try {
            Object curObj = ValidThreadLocal.peekObj();
            Field field = curObj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(curObj);
        } catch (NoSuchFieldException e) {
            ValidThreadLocal.setError("there is not a filed named '%s#%s'", ValidThreadLocal.peekObjName(), fieldName);
        } catch (IllegalAccessException e) {
            ValidThreadLocal.setError("'%s#%s' access illegal", ValidThreadLocal.peekObjName(), fieldName);
        }
        return null;
    }

    // getter setter
    public Boolean isValid() {
        return ValidThreadLocal.isValid();
    }

    public void setError(Integer code, String message) {
        ValidThreadLocal.setError(code, message);
        throw new ValidException(message);
    }

    public BaseError getError(){
        return ValidThreadLocal.getError();
    }

}
