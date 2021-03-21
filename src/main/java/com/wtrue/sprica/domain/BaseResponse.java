package com.wtrue.sprica.domain;

import lombok.Data;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/3/21 8:13 PM
 */
@Data
public class BaseResponse extends BaseObject{

    private boolean success = true;

    private BaseError error;

}
