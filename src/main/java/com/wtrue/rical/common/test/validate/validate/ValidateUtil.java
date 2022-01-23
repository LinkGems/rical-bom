package com.wtrue.rical.common.test.validate.validate;

import com.wtrue.rical.common.test.validate.validate.exception.ValidateException;
import com.wtrue.rical.common.test.validate.validate.impl.ObjectValidateImpl;
import com.wtrue.rical.common.test.validate.validate.interceptor.ValidateStruct;

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

    private final String[] invokeThis = {"sub", "sup", "supSub", "valid"};

    private IObjectValidate objectValidate;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args){
        if(isValid()){
            try{
                if(Arrays.asList(invokeThis).contains(method.getName())){
                    method.invoke(this, args);
                    return objectValidate;
                }
                List<ObjectValidateImpl> list = peekValidateObjectList();
                for(ObjectValidateImpl obj : list){
                    method.invoke(obj, args);
                }
            } catch (InvocationTargetException e) {
                populateError(e.getMessage());
            } catch (IllegalAccessException e) {
                populateError(e.getMessage());
            } catch (ValidateException e){
                populateError(e.getMessage());
            }
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
        return objectValidate;
    }

    private IObjectValidate supSub(String fieldName){
        sup();
        sub(fieldName);
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

    private ValidateUtil valid(){
        return this;
    }
}
