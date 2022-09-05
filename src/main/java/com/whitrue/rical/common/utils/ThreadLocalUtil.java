package com.whitrue.rical.common.utils;

import com.whitrue.rical.common.domain.BaseThreadLocal;

/**
 * @description:threadLocal工具类（待补充）
 * @author: meidanlong
 * @date: 2022/1/21 2:38 PM
 */
public class ThreadLocalUtil {

    private ThreadLocalUtil() {
    }

    private static final ThreadLocal<BaseThreadLocal> tl = ThreadLocal.withInitial(BaseThreadLocal::new);

    public static void set(BaseThreadLocal data){
        tl.set(data);
    }

    public static String getAppKey(){
        return tl.get().getAppKey();
    }

    public static void remove(){
        tl.remove();
    }

}
