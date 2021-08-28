package com.heyjianjun.shirovuedemo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : heyjianjun
 * @create 2021/8/28 16:47
 */
@Setter
@Getter
public class RoleDOT implements Serializable {

    private Long roleId;

    private String roleName;

    private String roleCode;

    /**
     * 状态(0 有效 1 失效)
     */
    private String status;

    private List<PermissionDTO> permissions;
}
