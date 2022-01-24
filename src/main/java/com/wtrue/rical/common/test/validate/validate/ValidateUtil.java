package com.wtrue.rical.common.test.validate.validate;

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

    protected IObjectValidate objectValidate;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args){
        String methodName = method.getName();
        if("valid".equals(methodName)){
            return valid();
        }
        try{
            if(isValid()){
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
        } catch (ValidateException | NoSuchMethodException e) {
            populateError(e.getMessage());
        } catch (InvocationTargetException e) {
            populateError(e.getTargetException().getMessage());
        } catch (IllegalAccessException e) {
            populateError(e.getMessage());
        }
        return objectValidate;
    }

    public IObjectValidate obj(String name, Supplier getObj){
        Object obj = getObj.get();
        List<ObjectValidateImpl> list = getObjListOfField(name, obj);
        pushValidateObjectList(list);
        objectValidate = (IObjectValidate) Proxy.newProxyInstance(
                        ObjectValidateImpl.class.getClassLoader(),
                        ObjectValidateImpl.class.getInterfaces(),
                        this);
        return objectValidate;
    }

    private ValidateUtil valid(){
        return this;
    }

    private List<ObjectValidateImpl> getObjListOfField(String name, Object obj){
        List<ObjectValidateImpl> list = new ArrayList<>();
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

    private IObjectValidate sub(String fieldName){
        List<ObjectValidateImpl> validateObjectList = peekValidateObjectList();
        List<ObjectValidateImpl> subValidateObjectList = reflectGetValueList(validateObjectList, fieldName);
        pushValidateObjectList(subValidateObjectList);
        return objectValidate;
    }

    private IObjectValidate sup(){
        popValidateObjectList();
        if(stackIsEmpty()){
            populateError("there is no super object, please check call stack");
        }
        return objectValidate;
    }

    private IObjectValidate supSub(String fieldName){
        sup().sub(fieldName);
        return objectValidate;
    }

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
