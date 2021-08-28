package com.heyjianjun.shirovuedemo.mapper;

import com.heyjianjun.shirovuedemo.entity.SysRole;
import com.heyjianjun.shirovuedemo.entity.SysUserRole;
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
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    List<SysRole> queryRoleListByUserId(Long userId);
}
