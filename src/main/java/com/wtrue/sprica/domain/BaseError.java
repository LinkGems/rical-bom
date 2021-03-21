package com.wtrue.sprica.domain;

import lombok.Data;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/3/21 8:16 PM
 */
@Data
public class BaseError extends BaseObject {

    private Integer code;

    private String message;

}
