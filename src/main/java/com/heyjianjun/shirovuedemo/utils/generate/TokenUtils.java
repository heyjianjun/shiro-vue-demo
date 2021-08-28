package com.heyjianjun.shirovuedemo.utils.generate;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.UUID;

/**
 * @Author : heyjianjun
 * @create 2021/8/25 16:49
 */
public class TokenUtils {

    public static String generateToken() {
        // 这个token生成随意,唯一就行
        Md5Hash md5Hash = new Md5Hash(UUID.randomUUID().toString(), "token", 1024);
        return md5Hash.toHex();
    }
}
