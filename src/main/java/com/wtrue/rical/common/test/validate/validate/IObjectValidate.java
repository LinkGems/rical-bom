package com.wtrue.rical.common.test.validate.validate;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:33 PM
 */
public interface IObjectValidate {

    // "sub", "sup", "supSub", "valid"

    IObjectValidate sub(String fieldName);

    IObjectValidate sup();

    IObjectValidate supSub(String fieldName);

    ValidateUtil valid();

    IObjectValidate notNull(String fieldName);
}
