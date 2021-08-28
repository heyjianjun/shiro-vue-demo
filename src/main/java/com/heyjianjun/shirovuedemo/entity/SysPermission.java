package com.heyjianjun.shirovuedemo.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long permissionId;

    private String permissionName;

    private String permissionCode;

    /**
     * 状态(0 有效 1 失效)
     */
    private String status;


}
