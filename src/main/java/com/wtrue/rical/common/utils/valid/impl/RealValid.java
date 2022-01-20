package com.wtrue.rical.common.utils.valid.impl;

import com.wtrue.rical.common.utils.valid.IValid2;
import lombok.Data;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/20 3:56 PM
 */
@Data
public class RealValid implements IValid2 {
    private Boolean valid = true;

    @Override
    public IValid2 notNull(String fieldName) {
        System.out.println(1);
        this.setValid(false);
        return this;
    }
}
