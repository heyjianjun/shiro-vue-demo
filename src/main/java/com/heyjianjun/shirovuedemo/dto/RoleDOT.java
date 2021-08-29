package com.heyjianjun.shirovuedemo.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @Author : heyjianjun
 * @create 2021/8/28 16:47
 */
@Setter
@Getter
public class RoleDOT extends BaseDTO {

    private Long roleId;

    @NotEmpty(message = "角色名不能为空")
    private String roleName;

    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 状态(0 有效 1 失效)
     */
    private String status;

    private List<PermissionDTO> permissions;
}
