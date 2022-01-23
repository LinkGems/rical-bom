package com.wtrue.rical.common.test.validate.validate.pojo;

import com.wtrue.rical.common.domain.BaseObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:37 PM
 */
public class ValidateObject extends BaseObject {

    private String objName;

    private Object object;

    private Map<String, Object> fieldMap = new ConcurrentHashMap<>();

    public ValidateObject(String objName, Object object) {
        this.objName = objName;
        this.object = object;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
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
