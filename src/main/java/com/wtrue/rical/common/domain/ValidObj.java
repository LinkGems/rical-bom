package com.wtrue.rical.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/18 10:56 AM
 */
@Getter
@Setter
public class ValidObj extends BaseObject{

    private String curObjName;

    private Object curObj;

    private Map<String, Object> fieldsData = new ConcurrentHashMap<>();

    public ValidObj(String curObjName, Object curObj) {
        this.curObjName = curObjName;
        this.curObj = curObj;
    }
}
