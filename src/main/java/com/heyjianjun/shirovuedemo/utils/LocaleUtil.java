package com.heyjianjun.shirovuedemo.utils;

import java.util.Locale;

/**
 * @Author : heyjianjun
 * @create 2021/8/21 18:14
 */
public class LocaleUtil {

    private static ThreadLocal<Locale> threadLocal = new ThreadLocal();

    public LocaleUtil() {
    }

    public static Locale getLocale() {
        Locale locale = (Locale)threadLocal.get();
        return locale == null?Locale.SIMPLIFIED_CHINESE:locale;
    }

    public static void setLocale(Locale locale) {
        threadLocal.set(locale);
    }

    public static void setLocale(String country) {
        threadLocal.set(new Locale(country));
    }

    public static String getLocalString() {
        Locale locale = getLocale();
        return locale == null?"":locale.getLanguage();
    }
}
