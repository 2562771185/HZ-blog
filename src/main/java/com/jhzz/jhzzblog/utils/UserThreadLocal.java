package com.jhzz.jhzzblog.utils;

import com.jhzz.jhzzblog.entity.SysUser;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/26
 * \* Time: 14:51
 * \* Description:
 * \
 */
public class UserThreadLocal {
    private UserThreadLocal() {
    }
//    线程变量隔离
    private static ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }
    public static void remove() {
        LOCAL.remove();
    }
}
