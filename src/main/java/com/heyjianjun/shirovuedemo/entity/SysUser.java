package com.heyjianjun.shirovuedemo.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jobob
 * @since 2021-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编码
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 状态（0正常 1删除 2停用）
     */
    private String status;

}
