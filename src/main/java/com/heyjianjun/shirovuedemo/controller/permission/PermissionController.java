package com.heyjianjun.shirovuedemo.controller.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.heyjianjun.shirovuedemo.constant.GlobalConstants;
import com.heyjianjun.shirovuedemo.controller.BaseController;
import com.heyjianjun.shirovuedemo.dto.PermissionDTO;
import com.heyjianjun.shirovuedemo.entity.SysPermission;
import com.heyjianjun.shirovuedemo.service.ISysPermissionService;
import com.heyjianjun.shirovuedemo.utils.result.ResultUtils;
import com.heyjianjun.shirovuedemo.utils.result.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author : heyjianjun
 * @create 2021/8/29 18:26
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {



    @Autowired
    private ISysPermissionService permissionService;

    @RequestMapping("/list")
    public ResultVO list(@RequestBody PermissionDTO permissionDTO) {
        Page<SysPermission> page = new PageDTO<>();
        page.setCurrent(permissionDTO.getCurrent());
        page.setSize(permissionDTO.getSize());
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(permissionDTO.getPermissionName())) {
            queryWrapper.eq("permission_name", permissionDTO.getPermissionName());
        }
        IPage<SysPermission> rolePage = permissionService.page(page, queryWrapper);
        return ResultUtils.success(rolePage);
    }


    @RequestMapping("/save")
    public ResultVO saveUser(@Valid @RequestBody PermissionDTO permissionDTO) {
        UpdateWrapper<SysPermission> updateWrapper = new UpdateWrapper<>();
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(permissionDTO, sysPermission);
        if (sysPermission.getPermissionId() != null) {
            updateWrapper.eq("permission_id", sysPermission.getPermissionId());
        }
        if (StringUtils.isBlank(sysPermission.getStatus())) {
            sysPermission.setStatus(GlobalConstants.CommonStatusConstants.VALID_STATUS);
        }
        permissionService.saveOrUpdate(sysPermission, updateWrapper);
        return ResultUtils.success();
    }

    @RequestMapping("/delete/{permissionId}")
    public ResultVO deleteUser(@PathVariable Long permissionId) {
        permissionService.removeById(permissionId);
        return ResultUtils.success();
    }


}
