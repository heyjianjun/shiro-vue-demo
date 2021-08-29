package com.heyjianjun.shirovuedemo.service;

import com.heyjianjun.shirovuedemo.dto.UserDTO;
import com.heyjianjun.shirovuedemo.shiro.auth.AuthToken;

/**
 * @Author : heyjianjun
 * @create 2021/8/25 16:44
 */
public interface ILoginService {

    /**
     * 创建token
     *
     * @return
     */
    AuthToken createToken(UserDTO user);
}
