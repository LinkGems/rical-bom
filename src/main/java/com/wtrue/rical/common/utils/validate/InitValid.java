package com.wtrue.rical.common.utils.validate;

import com.wtrue.rical.common.domain.BaseObject;
import com.wtrue.rical.common.utils.StringUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/19 10:19 AM
 */
class InitValid extends BaseValid{

    protected ValidObj curValidObj;
    protected Stack<ValidObj> objStack = new Stack<>();

    // initialize
    /**
     * 批量判空
     * @param fieldNames
     * @return
     */
    public InitValid notNull(String... fieldNames){
        for (String fieldName : fieldNames) {
            notNull(fieldName);
        }
        return this;
    }

    /**
     * 对象属性非空校验
     *
     * 1、属性对象非空校验
     * 2、String 校验null和""
     * 3、List 校验null和size==0
     *
     * @param fieldName
     * @return
     */
    public InitValid notNull(String fieldName){

        // assert
        if(!super.valid){
            return this;
        }
        // main
        Object fieldValue = getFieldValue(fieldName);
        if(fieldValue == null){
            populateError("'%s#%s' should not be null", curValidObj.getCurObjName(), fieldName);
        }else if(fieldValue instanceof String && StringUtil.isEmpty((String) fieldValue)){
            populateError("'%s#%s' should not be empty", curValidObj.getCurObjName(), fieldName);
        }else if(fieldValue instanceof List && ((List<?>) fieldValue).size() <= 0){
            populateError("'%s#%s' should not be empty", curValidObj.getCurObjName(), fieldName);
        }else{
            setMapValue(fieldName, fieldValue);
        }
        return this;
    }


    // 工具集

    /**
     * 获取属性值
     * @param fieldName
     * @return
     */
    protected Object getFieldValue(String fieldName){
        try {
            Object curObj = curValidObj.getCurObj();
            Field field = curValidObj.getCurObj().getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(curObj);
        } catch (NoSuchFieldException e) {
            populateError("there is not a filed named '%s#%s'", curValidObj.getCurObjName(), fieldName);
        } catch (IllegalAccessException e) {
            populateError("'%s#%s' access illegal", curValidObj.getCurObjName(), fieldName);
        }
        return null;
    }

    /**
     * 获取对象数据集
     * @return
     */
    protected Map<String, Object> getMap(){
        ValidObj curValidObj = objStack.peek();
        return curValidObj.getFieldsData();
    }

    /**
     * 重置数据集
     * @param map
     */
    protected void setMap(Map<String, Object> map){
        ValidObj curValidObj = objStack.peek();
        curValidObj.setFieldsData(map);
    }

    /**
     * 更新数据集内数据
     * @param fieldName
     * @param obj
     */
    protected void setMapValue(String fieldName, Object obj){
        ValidObj curValidObj = objStack.peek();
        Map<String, Object> fieldsData = curValidObj.getFieldsData();
        if(fieldsData == null){
            fieldsData = new ConcurrentHashMap<>();
        }
        fieldsData.put(fieldName, obj);
        curValidObj.setFieldsData(fieldsData);
    }

    /**
     * 获取数据集内数据
     * @param fieldName
     * @return
     */
    protected Object getMapValue(String fieldName){
        ValidObj curValidObj = objStack.peek();
        Map<String, Object> fieldsData = curValidObj.getFieldsData();
        if(fieldsData == null){
            notNull(fieldName);
        }
        return fieldsData.get(fieldName);
    }
}

/**
 * 辅助校验对象
 */
class ValidObj extends BaseObject {

    // 当前对象名称
    private String curObjName;
    // 当前对象
    private Object curObj;
    // 当前对象属性值
    private Map<String, Object> fieldsData = new ConcurrentHashMap<>();

    public ValidObj(String curObjName, Object curObj) {
        this.curObjName = curObjName;
        this.curObj = curObj;
    }

    public String getCurObjName() {
        return curObjName;
    }

    public void setCurObjName(String curObjName) {
        this.curObjName = curObjName;
    }

    public Object getCurObj() {
        return curObj;
    }

    public void setCurObj(Object curObj) {
        this.curObj = curObj;
    }

    public Map<String, Object> getFieldsData() {
        return fieldsData;
    }

    public void setFieldsData(Map<String, Object> fieldsData) {
        this.fieldsData = fieldsData;
    }
}