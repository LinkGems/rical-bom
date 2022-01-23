package com.wtrue.rical.common.test.validate;

import com.wtrue.rical.common.domain.BaseError;
import com.wtrue.rical.common.enums.ErrorEnum;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/22 12:14 PM
 */
public class ValidJdkUtil implements InvocationHandler {

    List<ValidObjUtil>  validObjUtilList = new ArrayList<>();

    private boolean valid = true;
    private BaseError error = new BaseError();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }

    public void obj(String objName, Supplier getObj){
        Object obj = getObj.get();
        if(obj instanceof Collection){
            Collection collectionObj = (Collection) obj;
            Iterator iterator = collectionObj.iterator();
            int index = 0;
            while (iterator.hasNext()){
                Object next = iterator.next();
                validObjUtilList.add(new ValidObjUtil(String.format("%s_%d", objName, index++), ()->next));
            }
        }
        Proxy.newProxyInstance(ValidObjUtil.class.getClassLoader(), IValidate.class.getInterfaces(),this);
    }

//    /**
//     * 校验参数是否合法
//     * @param valid
//     */
//    public void setValid(boolean valid) {
//        this.valid = valid;
//    }
//
//    /**
//     * 设置校验参数是否合法
//     * @return
//     */
//    public boolean isValid() {
//        return valid;
//    }
//
//    /**
//     * 填充错误信息
//     * @param error
//     */
//    public void setError(BaseError error) {
//        this.setValid(false);
//        this.error = error;
//    }
//
//    /**
//     * 填充错误信息
//     * @param code
//     * @param message
//     */
//    public void setError(Integer code, String message) {
//        this.setValid(false);
//        this.error.setCode(code);
//        this.error.setMessage(message);
//    }
//
//    /**
//     * 填充错误信息
//     * @param message
//     * @param params
//     */
//    public void setError(String message, String... params){
//        message = String.format(message, params);
//        setError(ErrorEnum.PARAM_ERROR.getCode(), message);
//    }
//
//    /**
//     * 追加报错信息，如校验之前已有报错则不再填充错误信息
//     * @param message
//     * @param params
//     */
//    public void setFollowError(String message, String... params){
//        message = String.format(message, params);
//        setFollowError(ErrorEnum.PARAM_ERROR.getCode(), message);
//    }
//
//    /**
//     * 追加报错信息，如校验之前已有报错则不再填充错误信息
//     * @param code
//     * @param message
//     */
//    public void setFollowError(Integer code, String message){
//        if(!valid){
//            setError(code, message);
//        }
//    }
//
//    /**
//     * 填充错误信息，并抛出异常
//     * @param message
//     * @param params
//     */
//    public void throwError(String message, String... params){
//        setError(message, params);
//        throw new VException(error.getMessage());
//    }
//
//    /**
//     * 获取错误对象
//     * @return
//     */
//    public BaseError getError() {
//        return error;
//    }
//
//    /**
//     * 获取错误信息
//     * @return
//     */
//    public String getMessage(){
//        return this.error.getMessage();
//    }
}
