package com.heyjianjun.shirovuedemo.controller.role;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.heyjianjun.shirovuedemo.constant.GlobalConstants;
import com.heyjianjun.shirovuedemo.controller.BaseController;
import com.heyjianjun.shirovuedemo.dto.RoleDOT;
import com.heyjianjun.shirovuedemo.entity.SysRole;
import com.heyjianjun.shirovuedemo.service.ISysRoleService;
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
 * @create 2021/8/29 13:49
 */
@RestController
@RequestMapping("role")
public class RoleController extends BaseController {

    @Autowired
    private ISysRoleService roleService;

    @RequestMapping("/list")
    public ResultVO list(@RequestBody RoleDOT roleDOT) {
        Page<SysRole> page = new PageDTO<>();
        page.setCurrent(roleDOT.getCurrent());
        page.setSize(roleDOT.getSize());
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(roleDOT.getRoleName())) {
            queryWrapper.eq("role_name", roleDOT.getRoleName());
        }
        IPage<SysRole> rolePage = roleService.page(page, queryWrapper);
        return ResultUtils.success(rolePage);
    }

    @RequestMapping("/save")
    public ResultVO saveRole(@Valid @RequestBody RoleDOT roleDOT) {
        UpdateWrapper<SysRole> updateWrapper = new UpdateWrapper<>();
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDOT, sysRole);
        if (sysRole.getRoleId() != null) {
            updateWrapper.eq("role_id", sysRole.getRoleId());
        }
        if (StringUtils.isBlank(sysRole.getStatus())) {
            sysRole.setStatus(GlobalConstants.CommonStatusConstants.VALID_STATUS);
        }
        roleService.saveOrUpdate(sysRole, updateWrapper);
        return ResultUtils.success();
    }

    @RequestMapping("/delete/{roleId}")
    public ResultVO deleteRole(@PathVariable Long roleId) {
        roleService.removeById(roleId);
        return ResultUtils.success();
    }

}
