package com.wtrue.rical.common.utils.validate;

import com.alibaba.fastjson.JSON;
import com.wtrue.rical.common.utils.StringUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;

/**
 * @description:校验类
 * @author: meidanlong
 * @date: 2022/1/20 7:10 PM
 */
public class ValidUtil extends InitValid {

    /**
     * 有参构造函数，传入对象用于初始化
     * @param objName
     * @param getObj 传入对象或者获取对象过程
     */
    public ValidUtil(String objName, Supplier getObj){
        try {
            if(StringUtil.isEmpty(objName)){
                throwError("baseObjName is empty");
            }
            if(getObj == null){
                throwError("function getObj is null");
            }
            Object curObj = getObj.get();
            if(curObj == null){
                throwError("%s is null", objName);
            }
            init(objName, curObj);
        }catch (NullPointerException npe){
            setError("NPE in the process of getting '%s'", objName);
        }catch (VException e){
            // do nothing...
        }
    }

    /**
     * 获取子对象
     * @param fieldName
     * @return
     */
    public ValidUtil sub(String fieldName){
        try{
            CurObj subObj = new CurObj(fieldName, getTopData(fieldName));
            push(subObj);
        } catch (VException e){
            // do nothing...
        }
        return this;
    }

    /**
     * 获取父对象
     * @return
     */
    public ValidUtil sup(){
        try{
            pop();
        } catch (VException e){
            // do nothing...
        }
        return this;
    }

    /**
     * 获取同级对象
     * @return
     */
    public ValidUtil supSub(String fieldName){
        try{
            sup();
            sub(fieldName);
        } catch (VException e){
            // do nothing...
        }
        return this;
    }

    /**
     * 判断对象属性非空
     * @param fieldName
     * @return
     */
    public ValidUtil notNull(String fieldName){
        try{
            Object fieldValue = getTopData(fieldName);
            if(fieldValue != null){
                return this;
            }
            fieldValue = reflectValue(fieldName);
            if(fieldValue == null){
                throwError("'%s#%s' should not be null", peekObjName(), fieldName);
            }else if(fieldValue instanceof String && StringUtil.isEmpty((String) fieldValue)){
                throwError("'%s#%s' should not be empty", peekObjName(), fieldName);
            }else if(fieldValue instanceof List && ((List<?>) fieldValue).size() <= 0){
                throwError("'%s#%s' should not be empty", peekObjName(), fieldName);
            }else{
                setTopData(fieldName, fieldValue);
            }
        } catch (NoSuchFieldException e) {
            setError("'%s' does not have field named '%s'", peekObjName(), fieldName);
        } catch (IllegalAccessException e) {
            setError("can not access field '%s#%s'", peekObjName(), fieldName);
        } catch (VException e){
            // do nothing...
        }
        return this;
    }

    /**
     * 批量判断对象属性非空
     * @param fieldNames
     * @return
     */
    public ValidUtil notNull(String... fieldNames){
        for(String fieldName : fieldNames){
            notNull(fieldName);
        }
        return this;
    }

    /**
     * 反射获取属性值
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws VException
     */
    private Object reflectValue(String fieldName) throws NoSuchFieldException, IllegalAccessException, VException {
        if(!isValid()){
            throw new VException(getMessage());
        }
        Object curObj = peekObj();
        Field field = curObj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(curObj);
    }

    /**
     * 获取栈顶数据，reflectValue兜底
     * @param fieldName
     * @return
     * @throws VException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Object getTopData(String fieldName){
        if(!isValid()){
            throw new VException(getMessage());
        }
        if(stack.isEmpty()){
            return null;
        }else{
            CurObj top = stack.peek();
            Object result = top.getFieldsData().get(fieldName);
            if(result == null){
                try {
                    result = reflectValue(fieldName);
                } catch (NoSuchFieldException e) {
                    setError("'%s' does not have field named '%s'", peekObjName(), fieldName);
                } catch (IllegalAccessException e) {
                    setError("can not access field '%s#%s'", peekObjName(), fieldName);
                }
            }
            return result;
        }
    }

    /**
     * 如果A的值为B，那么C的值必须为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public ValidUtil ifAIsBThenCMustD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        try{
            if(JSON.toJSONString(getTopData(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                    && !JSON.toJSONString(getTopData(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
                throwError("%s is %s, but %s is not %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
            }
        } catch (VException e){
            // do nothing...
        }
        return this;
    }

    /**
     * 如果A的值不为B，那么C的值必须为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public ValidUtil ifAIsNotBThenCMustD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        try{
            if(!JSON.toJSONString(getTopData(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                    && !JSON.toJSONString(getTopData(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
                throwError("%s is not %s, but %s is not %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
            }
        } catch (VException e){
            // do nothing...
        }
        return this;
    }

    /**
     * 如果A的值为B，那么C的值必须不能为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public ValidUtil ifAIsBThenCMustNotD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        try{
            if(JSON.toJSONString(getTopData(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                    && JSON.toJSONString(getTopData(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
                throwError("%s is %s, but %s is %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
            }
        } catch (VException e){
            // do nothing...
        }
        return this;
    }

    /**
     * 如果A的值为B，那么C的值必须不能为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public ValidUtil ifAIsNotBThenCMustNotD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        try{
            if(!JSON.toJSONString(getTopData(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                    && JSON.toJSONString(getTopData(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
                throwError("%s is not %s, but %s is %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
            }
        } catch (VException e){
            // do nothing...
        }
        return this;
    }

    /**
     * 对象属性最大值校验
     * @param fieldName
     * @param max
     * @return
     */
    public ValidUtil max(String fieldName, long max){
        try {
            Long toValidate = Long.valueOf(getTopData(fieldName).toString());
            if(toValidate > max){
                throwError("value of '%s#%s' should less then '%s'", peekObjName(), fieldName, String.valueOf(max));
            }
        } catch (NumberFormatException e){
            setError("'%s#%s' can not cast to Long", peekObjName(), fieldName);
        } catch (VException e){
            // do nothing...
        }
        return this;
    }

    /**
     * 对象属性最小值校验
     * @param fieldName
     * @param min
     * @return
     */
    public ValidUtil min(String fieldName, long min){
        try {
            Long toValidate = Long.valueOf(getTopData(fieldName).toString());
            if(toValidate < min){
                throwError("value of '%s#%s' should more then '%s'", peekObjName(), fieldName, String.valueOf(min));
            }
        } catch (NumberFormatException e){
            setError("'%s#%s' can not cast to Long", peekObjName(), fieldName);
        } catch (VException e){
            // do nothing...
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
    public ValidUtil betweenLong(String fieldName, long min, long max){
        min(fieldName, min);
        max(fieldName, max);
        return this;
    }

    /**
     * 集合最大数量
     * @param fieldName
     * @param max
     * @return
     */
    public ValidUtil listMaxSize(String fieldName, long max){
        // assert
        try{
            List list = (List) getTopData(fieldName);
            if(list.size()>max){
                throwError("size of '%s#%s' should less then '%s'", peekObjName(), fieldName, String.valueOf(max));
            }
        } catch (ClassCastException e){
            setError("'%s#%s' can not cast to List", peekObjName(), fieldName);
        } catch (VException e){
            // do nothing...
        }
        return this;
    }
}
