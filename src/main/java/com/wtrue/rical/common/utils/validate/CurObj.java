package com.wtrue.rical.common.utils.validate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:校验当前对象的基本信息
 * @author: meidanlong
 * @date: 2022/1/20 8:19 PM
 */
public class CurObj {
    // 当前对象名称
    private String curObjName;
    // 当前对象
    private Object curObj;
    // 当前对象属性值
    private Map<String, Object> fieldsData = new ConcurrentHashMap<>();

    public CurObj(String curObjName, Object curObj) {
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