package com.wtrue.rical.common.test;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/24 10:53 AM
 */
@Data
class Person {

    String name;

    Integer sex;

    List<String> hobby;

    List<Work> works;
}
