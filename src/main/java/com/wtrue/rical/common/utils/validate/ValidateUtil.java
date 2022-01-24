package com.wtrue.rical.common.utils.validate;

import com.wtrue.rical.common.utils.StringUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:45 PM
 */
public class ValidateUtil extends ValidateStruct implements InvocationHandler {

    private final String[] invokeThis = {"sub", "sup", "supSub"};

    private IObjectValidate objectValidate;
    private IExpressionValidate expressionValidate;
    private ValidateEnum validateType;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args){
        // 返回方法
        String methodName = method.getName();
        if("valid".equals(methodName)){
            return valid();
        }
        // 代理逻辑
        try{
            if(isValid()){
                // assert args
                validateArgs(methodName, args);
                if(ValidateEnum.OBJECT == validateType){
                    if(Arrays.asList(invokeThis).contains(method.getName())){
                        Method thisMethod = ValidateUtil.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
                        thisMethod.setAccessible(true);
                        return thisMethod.invoke(this, args);
                    }
                    List<ObjectValidateImpl> list = peekValidateObjectList();
                    for(ObjectValidateImpl obj : list){
                        method.invoke(obj, args);
                    }
                }
                if(ValidateEnum.EXPRESSION == validateType){
                    method.invoke(proxy, args);
                }
            }
        } catch (ValidateException | NoSuchMethodException e) {
            populateError(e.getMessage());
        } catch (InvocationTargetException e) {
            populateError(e.getTargetException().getMessage());
        } catch (IllegalAccessException e) {
            populateError(e.getMessage());
        }
        return ValidateEnum.OBJECT == validateType ? objectValidate : expressionValidate;
    }

    /**
     * 获取对象校验方法
     * @param objName
     * @param getObj
     * @return
     */
    public IObjectValidate object(String objName, Supplier getObj){
        validateType = ValidateEnum.OBJECT;
        objectValidate = (IObjectValidate) Proxy.newProxyInstance(
                ObjectValidateImpl.class.getClassLoader(),
                ObjectValidateImpl.class.getInterfaces(),
                this);
        if(StringUtil.isEmpty(objName)){
            populateError("objName is empty");
            return objectValidate;
        }
        if(getObj == null){
            populateError("function getObj is null");
            return objectValidate;
        }
        Object curObj = getObj.get();
        if(curObj == null){
            populateError("%s is null", objName);
            return objectValidate;
        }
        List<ObjectValidateImpl> list = getObjListOfField(objName, curObj);
        pushValidateObjectList(list);
        return objectValidate;
    }

    /**
     * 获取表达式校验方法
     * @return
     */
    public IExpressionValidate expression(){
        validateType = ValidateEnum.EXPRESSION;
        expressionValidate = (IExpressionValidate) Proxy.newProxyInstance(
                ExpressionValidateImpl.class.getClassLoader(),
                ExpressionValidateImpl.class.getInterfaces(),
                this);
        return expressionValidate;
    }

    /**
     * 构建校验类对象方法
     * @return
     */
    private ValidateUtil valid(){
        return this;
    }

    /**
     * 校验方法调用参数
     * @param methodName
     * @param args
     */
    private void validateArgs(String methodName, Object[] args) throws ValidateException {
        for(Object arg : args){
            if(arg == null){
                throw new ValidateException("there has null arg, when call method '%s'", methodName);
            }
            if(arg instanceof String && StringUtil.isEmpty((String)arg)){
                throw new ValidateException("there has null arg, when call method '%s'", methodName);
            }
            if(arg.getClass().isArray()){
                validateArgs(methodName, (Object[])arg);
            }
        }
    }

    /**
     * 获取属性对象列表
     * @param name
     * @param obj
     * @return
     */
    private List<ObjectValidateImpl> getObjListOfField(String name, Object obj){
        List<ObjectValidateImpl> list = new ArrayList<>();
        // 单独转化数组类型
        if(obj.getClass().isArray()){
            obj = Arrays.asList(obj);
        }
        if(obj instanceof Collection){
            Collection collectionObj = (Collection) obj;
            Iterator iterator = collectionObj.iterator();
            int index = 0;
            while (iterator.hasNext()){
                Object next = iterator.next();
                list.add(new ObjectValidateImpl(String.format("%s_%d", name, index++), next));
            }
        }else{
            list.add(new ObjectValidateImpl(name, obj));
        }
        return list;
    }

    /**
     * 获取下一层级对象
     * @param fieldName
     * @return
     */
    private IObjectValidate sub(String fieldName){
        List<ObjectValidateImpl> validateObjectList = peekValidateObjectList();
        List<ObjectValidateImpl> subValidateObjectList = reflectGetValueList(validateObjectList, fieldName);
        pushValidateObjectList(subValidateObjectList);
        return objectValidate;
    }

    /**
     * 获取上一层级对象
     * @return
     */
    private IObjectValidate sup(){
        popValidateObjectList();
        if(stackIsEmpty()){
            populateError("there is no super object, please check call stack");
        }
        return objectValidate;
    }

    /**
     * 获取同级对象
     * @param fieldName
     * @return
     */
    private IObjectValidate supSub(String fieldName){
        sup().sub(fieldName);
        return objectValidate;
    }

    /**
     * 反射获取对象属性下一级对象列表
     * @param validateObjectList
     * @param fieldName
     * @return
     */
    private List<ObjectValidateImpl> reflectGetValueList(List<ObjectValidateImpl> validateObjectList, String fieldName){
        List<ObjectValidateImpl> subList = new ArrayList<>();
        for(ObjectValidateImpl objectValidate : validateObjectList){
            objectValidate.notNull(fieldName);
            Object obj = objectValidate.getFieldMapData(fieldName);
            String objName = String.format("%s#%s", objectValidate.getObjName(), fieldName);
            List<ObjectValidateImpl> addList = getObjListOfField(objName, obj);
            subList.addAll(addList);
        }
        return subList;
    }
}
