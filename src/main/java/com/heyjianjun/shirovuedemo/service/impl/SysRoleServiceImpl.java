package com.heyjianjun.shirovuedemo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heyjianjun.shirovuedemo.entity.SysRole;
import com.heyjianjun.shirovuedemo.mapper.SysRoleMapper;
import com.heyjianjun.shirovuedemo.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-08-26
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public IPage<SysRole> queryRolePage(Page<SysRole> page) {
        return baseMapper.selectPage(page, null);
    }
}
