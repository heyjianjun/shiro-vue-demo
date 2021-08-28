package com.heyjianjun.shirovuedemo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author : heyjianjun
 * @create 2021/8/28 16:46
 */
@Setter
@Getter
public class PermissionDTO implements Serializable {

    private Long permissionId;

    private String permissionName;

    private String permissionCode;

    /**
     * 状态(0 有效 1 失效)
     */
    private String status;
}
