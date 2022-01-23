package com.wtrue.rical.common.test.validate.jdk;

import com.wtrue.rical.common.test.validate.ValidateUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 12:57 PM
 */
@Data
public class JdkSup {

    List<ValidateUtil> list = new ArrayList<>();

    boolean valid = true;
}
