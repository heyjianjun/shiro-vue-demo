package com.heyjianjun.shirovuedemo.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : heyjianjun
 * @create 2021/8/28 16:44
 */
@Getter
@Setter
public class UserDTO implements Serializable {

    /**
     * 用户编码
     */
    private Long userId;

    /**
     * 登录账号
     */
    @NotEmpty(message = "账户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 状态（0正常 1删除 2停用）
     */
    private String status;

    private List<RoleDOT> roles;
}
