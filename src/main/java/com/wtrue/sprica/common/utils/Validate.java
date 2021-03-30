package com.wtrue.sprica.common.utils;

import lombok.Data;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/3/21 8:24 PM
 */
@Data
public class Validate {

    private boolean valid = true;
    private String msg;

    private String notNullMsg = " should not be null.";

    public Validate notNull(Object obj, String... params){
        if(!valid){
            return this;
        }
        if(obj == null){
            valid = false;
            String objName = obj.getClass().getName();
            msg = objName + notNullMsg;
            return this;
        }
        if(params != null && params.length > 0){
            Class<?> objClass = obj.getClass();
            for(String param : params){

            }
        }
        return this;
    }


}
