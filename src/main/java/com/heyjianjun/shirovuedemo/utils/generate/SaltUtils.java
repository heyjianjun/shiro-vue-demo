package com.heyjianjun.shirovuedemo.utils.generate;

import java.util.Random;

/**
 * @Author : heyjianjun
 * @create 2021/8/26 13:59
 */
public class SaltUtils {

    public static final String words = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm!@#$%^&*1234567890";

    public static String generateSale(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(words.charAt(new Random().nextInt(words.length() - 1)));
        }
        return stringBuilder.toString();
    }
}
