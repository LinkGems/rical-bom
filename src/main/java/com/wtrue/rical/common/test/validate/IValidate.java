package com.wtrue.rical.common.test.validate;

import com.wtrue.rical.common.test.validate.jdk.Jdk;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/22 12:10 PM
 */
public interface IValidate {

    IValidate notNull(String fieldName);

    Jdk valid();
}
