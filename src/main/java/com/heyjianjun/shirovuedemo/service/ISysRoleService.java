package com.heyjianjun.shirovuedemo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heyjianjun.shirovuedemo.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-08-26
 */
public interface ISysRoleService extends IService<SysRole> {
    IPage<SysRole> queryRolePage(Page<SysRole> page);
}
