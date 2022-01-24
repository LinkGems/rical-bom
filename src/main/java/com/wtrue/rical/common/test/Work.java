package com.wtrue.rical.common.test;

import lombok.Data;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/24 12:26 PM
 */
@Data
class Work {
    String name;

    /**
     * 1 创业，2 打工，3 宅家
     */
    Integer workType;
    /**
     * workType 为 1、2时，salary不为空
     */
    Integer salary;
}
