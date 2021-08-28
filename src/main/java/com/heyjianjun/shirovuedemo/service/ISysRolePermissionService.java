package com.heyjianjun.shirovuedemo.service;

import com.heyjianjun.shirovuedemo.dto.PermissionDTO;
import com.heyjianjun.shirovuedemo.entity.SysPermission;
import com.heyjianjun.shirovuedemo.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-08-26
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {

    List<PermissionDTO> queryPermissionListByRoleId(Long roleId);

}
