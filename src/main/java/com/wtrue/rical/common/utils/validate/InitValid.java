package com.wtrue.rical.common.utils.validate;


import java.util.Stack;

/**
 * @description:初始化校验类数据结构。使用栈类型寄存各个层级的校验对象
 * @author: meidanlong
 * @date: 2022/1/20 6:59 PM
 */
class InitValid extends BaseValid{
    
    protected Stack<CurObj> stack = new Stack<>();

    protected void init(String objName, Object obj){
        CurObj curObj = new CurObj(objName, obj);
        stack.push(curObj);
    }

    protected CurObj peek() throws VException{
        if(!isValid()){
            throw new VException(getMessage());
        }
        if(stack.isEmpty()){
            // 不会走到这
            return null;
        }else{
            return stack.peek();
        }
    }

    protected CurObj pop() throws VException{
        if(!isValid()){
            throw new VException(getMessage());
        }
        if(stack.isEmpty()){
            // 不会走到这
            return null;
        }else{
            return stack.pop();
        }
    }

    protected void push(CurObj CurObj) throws VException{
        if(!isValid()){
            throw new VException(getMessage());
        }
        stack.push(CurObj);
    }

    protected Object peekObj() throws VException{
        if(!isValid()){
            throw new VException(getMessage());
        }
        CurObj peek = peek();
        if(peek != null){
            return peek.getCurObj();
        }else{
            return null;
        }
    }

    protected String peekObjName() throws VException{
        if(!isValid()){
            throw new VException(getMessage());
        }
        CurObj peek = peek();
        if(peek != null){
            return peek.getCurObjName();
        }else{
            return null;
        }
    }

    protected void setTopData(String k, Object v) throws VException{
        if(!isValid()){
            throw new VException(getMessage());
        }
        if(stack.isEmpty()){
            return;
        }else{
            CurObj top = stack.peek();
            top.getFieldsData().put(k, v);
        }
    }
}
