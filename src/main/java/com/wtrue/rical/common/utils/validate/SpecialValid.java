package com.wtrue.rical.common.utils.validate;

import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/19 10:28 AM
 */
class SpecialValid extends CommonValid{

    /**
     * 对象属性字符长度校验
     * @param fieldName
     * @param max
     * @return
     */
    public SpecialValid stringMaxLength(String fieldName, long max){
        // assert
        if(!this.valid || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        Object value = getMapValue(fieldName);
        if(value instanceof String){
            String toValidate = value.toString();
            if(toValidate.length() > max){
                populateError("length of '%s#%s' should less then '%s'", curValidObj.getCurObjName(), fieldName, String.valueOf(max));
            }
        }else{
            populateError("'%s#%s' is not a string", curValidObj.getCurObjName(), fieldName);
        }

        return this;
    }

    /**
     * 对象属性最大值校验
     * @param fieldName
     * @param max
     * @return
     */
    public SpecialValid maxLong(String fieldName, long max){
        // assert
        if(!this.valid || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        try {
            Long toValidate = Long.valueOf(getMapValue(fieldName).toString());
            if(toValidate > max){
                populateError("value of '%s#%s' should less then '%s'", curValidObj.getCurObjName(), fieldName, String.valueOf(max));
            }
        }catch (NumberFormatException e){
            populateError("'%s#%s' can not cast to Long", curValidObj.getCurObjName(), fieldName);
        }
        return this;
    }

    /**
     * 对象属性最小值校验
     * @param fieldName
     * @param min
     * @return
     */
    public SpecialValid minLong(String fieldName, long min){
        // assert
        if(!this.valid || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        try {
            Long toValidate = Long.valueOf(getMapValue(fieldName).toString());
            if(toValidate < min){
                populateError("value of '%s#%s' should more then '%s'", curValidObj.getCurObjName(), fieldName, String.valueOf(min));
            }
        }catch (NumberFormatException e){
            populateError("'%s#%s' can not cast to Long", curValidObj.getCurObjName(), fieldName);
        }
        return this;
    }

    /**
     * 对象属性在两值之间校验
     * @param fieldName
     * @param min
     * @param max
     * @return
     */
    public SpecialValid betweenLong(String fieldName, long min, long max){
        // assert
        if(!this.valid){
            return this;
        }
        // main
        minLong(fieldName, min);
        maxLong(fieldName, max);
        return this;
    }

    /**
     * 集合最大数量
     * @param fieldName
     * @param max
     * @return
     */
    public SpecialValid listMaxSize(String fieldName, long max){
        // assert
        if(!this.valid || !notNull(fieldName).isValid()){
            return this;
        }
        // main
        try{
            List list = (List) getMapValue(fieldName);
            if(list.size()>max){
                populateError("size of '%s#%s' should less then '%s'", curValidObj.getCurObjName(), fieldName, String.valueOf(max));
            }
        }catch (Exception e){
            populateError("'%s#%s' can not cast to List", curValidObj.getCurObjName(), curValidObj.getCurObjName());
        }
        return this;
    }
}
