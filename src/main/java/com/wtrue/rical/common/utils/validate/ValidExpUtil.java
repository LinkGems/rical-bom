package com.wtrue.rical.common.utils.validate;

import com.wtrue.rical.common.utils.StringUtil;

import java.util.function.Supplier;

/**
 * @description:表达式校验工具类
 * @author: meidanlong
 * @date: 2022/1/20 10:08 PM
 */
public class ValidExpUtil extends BaseValid{

    public ValidExpUtil expression(String expName, Supplier<Boolean> exp){
        try{
            if(!isValid()){
                throw new VException(getMessage());
            }
            if(StringUtil.isEmpty(expName)){
                throwError("name of expression is empty");
            }
            if(exp == null){
                throwError("expression is null");
            }
            Boolean success = exp.get();
            if(success == null || !success) {
                throwError("expression %s is not legal", expName);
            }
        }catch (VException e){
            // do nothing...
        }
        return this;
    }
}
