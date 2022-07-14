package com.whitrue.rical.common.test;

import com.whitrue.rical.common.domain.BaseObject;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/24 10:53 AM
 */
@Data
class Person extends BaseObject {

    String name;

    Integer sex;

    List<String> hobby;

    List<Work> works;
}
