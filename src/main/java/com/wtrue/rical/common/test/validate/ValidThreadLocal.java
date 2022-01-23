package com.wtrue.rical.common.test.validate;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/22 12:18 PM
 */
public class ValidThreadLocal {

    ThreadLocal<List<ValidCgLibUtil>> valids = ThreadLocal.withInitial(ArrayList::new);
}
