package com.wtrue.rical.common.utils;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/3/21 8:24 PM
 */
@Data
public class ValidUtil {

    private boolean valid = true;
    private String msg;

    /**
     * 判空
     * @param name
     * @param obj
     * @return
     */
    public ValidUtil notNull(String name, Object obj){
        if(!valid){
            return this;
        }
        if(obj == null){
            valid = false;
            msg = name + " should not be null";
        }else{
            if(obj instanceof String && StringUtils.isBlank((CharSequence) obj)){
                valid = false;
                msg = name + " should not be blank";
            }
        }
        return this;
    }

    /**
     * 需大于
     * @param name
     * @param obj
     * @param min
     * @return
     */
    public ValidUtil moreThen(String name, Object obj, Integer min){
        notNull(name,obj);
        if(!valid){
            return this;
        }
        if(StringUtils.isNumeric(obj.toString())){
            Integer target = Integer.valueOf(obj.toString());
            if(target <= min){
                valid = false;
                msg = name + " should more then " + min;
            }
        }else{
            valid = false;
            msg = name + " is not a number";
        }
        return this;
    }

    /**
     * 需小于
     * @param name
     * @param obj
     * @param max
     * @return
     */
    public ValidUtil lessThen(String name, Object obj, Integer max){
        notNull(name,obj);
        if(!valid){
            return this;
        }
        if(StringUtils.isNumeric(obj.toString())){
            Integer target = Integer.valueOf(obj.toString());
            if(target > max){
                valid = false;
                msg = name + " should less then " + max;
            }
        }else{
            valid = false;
            msg = name + " is not a number";
        }
        return this;
    }

}
