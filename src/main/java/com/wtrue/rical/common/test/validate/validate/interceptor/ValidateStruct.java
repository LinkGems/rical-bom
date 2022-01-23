package com.wtrue.rical.common.test.validate.validate.interceptor;

import com.wtrue.rical.common.test.validate.IValidate;
import com.wtrue.rical.common.test.validate.validate.impl.ObjectValidateImpl;
import com.wtrue.rical.common.test.validate.validate.pojo.ValidateObject;

import java.util.List;
import java.util.Stack;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:36 PM
 */
public class ValidateStruct extends ValidateMsg {

    private Stack<List<ObjectValidateImpl>> stack = new Stack<>();


    public List<ObjectValidateImpl> peekValidateObjectList(){
        return stack.peek();
    }

    public List<ObjectValidateImpl> popValidateObjectList(){
        return stack.pop();
    }

    public void pushValidateObjectList(List<ObjectValidateImpl> validateObjectList){
        stack.push(validateObjectList);
    }

    public void addValidateObjectList(List<ObjectValidateImpl> validateObjectList){
        List<ObjectValidateImpl> oriValidateObjectList = peekValidateObjectList();
        if(oriValidateObjectList == null){
            pushValidateObjectList(validateObjectList);
        }else{
            oriValidateObjectList.addAll(validateObjectList);
        }
    }

}
