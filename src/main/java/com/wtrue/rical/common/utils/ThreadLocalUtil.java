package com.wtrue.rical.common.utils;

import com.wtrue.rical.common.domain.ThreadLocalData;

/**
 * @description:threadLocal工具类（待补充）
 * @author: meidanlong
 * @date: 2022/1/21 2:38 PM
 */
public class ThreadLocalUtil {

    private ThreadLocalUtil() {
    }

    private static final ThreadLocal<ThreadLocalData> tl = ThreadLocal.withInitial(ThreadLocalData::new);

    public static void setTl(ThreadLocalData data){
        tl.set(data);
    }

    public static String getAppKey(){
        return tl.get().getAppKey();
    }

}
