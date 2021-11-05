package com.wtrue.rical.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/3/21 8:13 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseResponse<T> extends BaseObject{

    private boolean success = true;

    private T data;

    private BaseError error;

}
