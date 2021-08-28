package com.heyjianjun.shirovuedemo.service.impl;

import com.heyjianjun.shirovuedemo.dto.UserDTO;
import com.heyjianjun.shirovuedemo.service.ILoginService;
import com.heyjianjun.shirovuedemo.utils.generate.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author : heyjianjun
 * @create 2021/8/25 16:45
 */
@Service
public class LoginServiceImpl implements ILoginService {

    /**
     * token失效时间 小时
     */
    private final static long EXPIRE = 24;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 创建token
     *
     * @param user
     * @return
     */
    @Override
    public String createToken(UserDTO user) {
        String token = TokenUtils.generateToken();
        // 脱敏
        user.setPassword("");
        user.setSalt("");
        redisTemplate.opsForValue().set(token, user, EXPIRE, TimeUnit.HOURS);
        return token;
    }
}
