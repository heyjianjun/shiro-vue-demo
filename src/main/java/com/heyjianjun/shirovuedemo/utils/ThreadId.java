package com.heyjianjun.shirovuedemo.utils;

import java.util.UUID;

/**
 * @Author : heyjianjun
 * @create 2021/8/21 18:13
 */
public class ThreadId {

    private static ThreadLocal<String> threadLocal = new ThreadLocal();

    public ThreadId() {
    }

    public static String getThreadId() {
        if(threadLocal.get() == null) {
            threadLocal.set(String.valueOf(UUID.randomUUID().getLeastSignificantBits()));
        }

        return (String)threadLocal.get();
    }

    public static void setThreadId(String threadId) {
        threadLocal.set(threadId);
    }

    public static void setThreadIdWithIP(String ip) {
        threadLocal.set(ip + ":" + UUID.randomUUID().getLeastSignificantBits());
    }
}
