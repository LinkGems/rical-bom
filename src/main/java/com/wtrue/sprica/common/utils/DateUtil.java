package com.wtrue.sprica.common.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * @description:日期工具
 * @author: meidanlong
 * @date: 2021/7/22 11:18 AM
 */
public class DateUtil {

    private static String BAIDU_URL = "http://www.baidu.com";

    /**
     * 获取当前时间，精确到秒
     * 以百度时间为准
     * @return
     */
    public static Date curTime() throws IOException {
        URL url = new URL(BAIDU_URL);
        URLConnection uc = url.openConnection();
        uc.connect();// 发出连接
        long ld = uc.getDate();
        return new Date(ld);
    }
}
