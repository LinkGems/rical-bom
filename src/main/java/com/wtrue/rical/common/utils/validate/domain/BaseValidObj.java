package com.wtrue.rical.common.utils.validate.domain;

import com.wtrue.rical.common.domain.BaseObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 辅助校验对象
 * @author: meidanlong
 * @date: 2022/1/19 9:07 PM
 */
public class BaseValidObj extends BaseObject {

    // 当前对象名称
    private String curObjName;
    // 当前对象
    private Object curObj;
    // 当前对象属性值
    private Map<String, Object> fieldsData = new ConcurrentHashMap<>();

    public BaseValidObj(String curObjName, Object curObj) {
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
