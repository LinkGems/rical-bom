package com.wtrue.rical.common.test.validate.validate.impl;

import com.wtrue.rical.common.test.validate.validate.IObjectValidate;
import com.wtrue.rical.common.test.validate.validate.exception.ValidateException;
import com.wtrue.rical.common.test.validate.validate.pojo.ValidateObject;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:33 PM
 */
public class ObjectValidateImpl extends ValidateObject implements IObjectValidate {

    public ObjectValidateImpl(String objName, Object object) {
        super(objName, object);
    }

    public IObjectValidate notNull(String fieldName) throws ValidateException {
        return null;
    }

    public Object reflectGetValue(String fieldName) throws ValidateException {
        return null;
    }
}
