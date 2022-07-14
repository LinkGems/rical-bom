package com.whitrue.rical.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/21 2:36 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ThreadLocalData extends BaseObject{

    private String appKey;
}
