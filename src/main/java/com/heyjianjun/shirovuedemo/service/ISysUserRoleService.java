package com.heyjianjun.shirovuedemo.service;

import com.heyjianjun.shirovuedemo.dto.RoleDOT;
import com.heyjianjun.shirovuedemo.entity.SysRole;
import com.heyjianjun.shirovuedemo.entity.SysUserRole;
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
public interface ISysUserRoleService extends IService<SysUserRole> {
    /**
     * 根据userId关联查询角色信息
     * @param UserId
     * @return
     */
    List<RoleDOT> queryRoleListByUserId(Long UserId);
}
