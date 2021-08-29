package com.heyjianjun.shirovuedemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heyjianjun.shirovuedemo.entity.SysUser;
import com.heyjianjun.shirovuedemo.mapper.SysUserMapper;
import com.heyjianjun.shirovuedemo.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-08-25
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
}
