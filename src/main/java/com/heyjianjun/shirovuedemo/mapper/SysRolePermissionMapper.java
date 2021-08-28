package com.heyjianjun.shirovuedemo.mapper;

import com.heyjianjun.shirovuedemo.entity.SysPermission;
import com.heyjianjun.shirovuedemo.entity.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-08-26
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    List<SysPermission> queryPermissionListByRoleId(Long roleId);
}
