package com.wtrue.rical.common.test.validate.validate;

import com.wtrue.rical.common.domain.BaseObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:37 PM
 */
class ValidateObject extends BaseObject {

    private String objName;

    private Object curObj;

    private Map<String, Object> fieldMap = new ConcurrentHashMap<>();

    public ValidateObject(String objName, Object curObj) {
        this.objName = objName;
        this.curObj = curObj;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public Object getCurObj() {
        return curObj;
    }

    public void setCurObj(Object curObj) {
        this.curObj = curObj;
    }

    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public void setFieldMapData(String fieldName, Object obj){
        fieldMap.put(fieldName, obj);
    }

    public Object getFieldMapData(String fieldName){
        return fieldMap.get(fieldName);
    }
}
