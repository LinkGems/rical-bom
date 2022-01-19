package com.wtrue.rical.common.utils.validate.thread;

import com.wtrue.rical.common.domain.BaseError;
import com.wtrue.rical.common.enums.ErrorEnum;
import com.wtrue.rical.common.utils.validate.domain.BaseValidObj;
import com.wtrue.rical.common.utils.validate.domain.BaseValidState;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/19 9:06 PM
 */
public class ValidThreadLocal {

    private static final ThreadLocal<Stack<BaseValidObj>> validObjTL = ThreadLocal.withInitial(Stack::new);
    private static final ThreadLocal<List<BaseValidState>> validStateTL = ThreadLocal.withInitial(ArrayList::new);

    private static AtomicBoolean valid = new AtomicBoolean(true);


    public static void removeValidThreadLocal(){
        validObjTL.remove();
        validStateTL.remove();
    }

    public static void initTL(BaseValidObj obj){
        push(obj);
        List<BaseValidState> states = new ArrayList<>();
        states.add(new BaseValidState());
        validStateTL.set(states);
    }

    public static Boolean isValid(){
        List<BaseValidState> states = validStateTL.get();
        return states.get(states.size()-1).isValid();
    }

    public static AtomicBoolean isValidA(){
        return valid;
    }
    public static void noValidA(){
        valid.set(false);
    }

    public static BaseError getError(){
        return validStateTL.get().get(0).getError();
    }

    public static void setError(int code, String msg){
        BaseValidState baseValidState = validStateTL.get().get(0);
        baseValidState.setValid(false);
        baseValidState.setError(code, msg);
        validStateTL.get().add(baseValidState);
    }

    public static void setError(String msg, String... params){
        msg = String.format(msg, params);
        setError(ErrorEnum.PARAM_ERROR.getCode(), msg);
    }

    public static Stack<BaseValidObj> getStack(){
        return validObjTL.get();
    }

    public static void setStack(Stack<BaseValidObj> stack){
        validObjTL.set(stack);
    }

    public static BaseValidObj peek(){
        Stack<BaseValidObj> stack = getStack();
        if(stack.isEmpty()){
            // 不会走到这
            return null;
        }else{
            return stack.peek();
        }
    }

    public static BaseValidObj pop(){
        Stack<BaseValidObj> stack = getStack();
        if(stack.isEmpty()){
            // 不会走到这
            return null;
        }else{
            return stack.pop();
        }
    }

    public static void push(BaseValidObj baseValidObj){
        Stack<BaseValidObj> stack = getStack();
        stack.push(baseValidObj);
        setStack(stack);
    }

    public static Object peekObj(){
        BaseValidObj peek = peek();
        if(peek != null){
            return peek.getCurObj();
        }else{
            return null;
        }
    }

    public static String peekObjName(){
        BaseValidObj peek = peek();
        if(peek != null){
            return peek.getCurObjName();
        }else{
            return null;
        }
    }

    public static void setTopData(String k, Object v){
        Stack<BaseValidObj> stack = getStack();
        if(stack.isEmpty()){
            return;
        }else{
            BaseValidObj top = stack.peek();
            top.getFieldsData().put(k, v);
        }
    }

    public static Object getTopData(String k){
        Stack<BaseValidObj> stack = getStack();
        if(stack.isEmpty()){
            return null;
        }else{
            BaseValidObj top = stack.peek();
            return top.getFieldsData().get(k);
        }
    }

}
