package com.heyjianjun.shirovuedemo.service.impl;

import com.heyjianjun.shirovuedemo.dto.PermissionDTO;
import com.heyjianjun.shirovuedemo.entity.SysPermission;
import com.heyjianjun.shirovuedemo.entity.SysRolePermission;
import com.heyjianjun.shirovuedemo.mapper.SysRolePermissionMapper;
import com.heyjianjun.shirovuedemo.service.ISysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Override
    public List<PermissionDTO> queryPermissionListByRoleId(Long roleId) {
        List<SysPermission> sysPermissions = super.baseMapper.queryPermissionListByRoleId(roleId);
        List<PermissionDTO> permissionList = new ArrayList<>();
        sysPermissions.forEach(e->{
            PermissionDTO permissionDTO = new PermissionDTO();
            BeanUtils.copyProperties(e, permissionDTO);
            permissionList.add(permissionDTO);
        });
        return permissionList;
    }

}
