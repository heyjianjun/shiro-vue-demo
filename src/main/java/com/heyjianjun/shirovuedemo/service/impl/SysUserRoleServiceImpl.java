package com.heyjianjun.shirovuedemo.service.impl;

import com.heyjianjun.shirovuedemo.dto.RoleDOT;
import com.heyjianjun.shirovuedemo.entity.SysRole;
import com.heyjianjun.shirovuedemo.entity.SysUserRole;
import com.heyjianjun.shirovuedemo.mapper.SysUserRoleMapper;
import com.heyjianjun.shirovuedemo.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-08-26
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {


    /**
     * 根据userId关联查询角色信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<RoleDOT> queryRoleListByUserId(Long userId) {
        List<SysRole> sysRoles = super.baseMapper.queryRoleListByUserId(userId);
        List<RoleDOT> roleList = new ArrayList<>();
        sysRoles.forEach(e->{
            RoleDOT roleDOT = new RoleDOT();
            BeanUtils.copyProperties(e, roleDOT);
            roleList.add(roleDOT);
        });
        return roleList;
    }

}
