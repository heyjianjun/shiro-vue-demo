package com.heyjianjun.shirovuedemo.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.heyjianjun.shirovuedemo.constant.GlobalConstants;
import com.heyjianjun.shirovuedemo.controller.BaseController;
import com.heyjianjun.shirovuedemo.dto.UserDTO;
import com.heyjianjun.shirovuedemo.entity.SysUser;
import com.heyjianjun.shirovuedemo.service.ISysUserService;
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
 * @create 2021/8/29 18:17
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @RequestMapping("/list")
    public ResultVO list(@RequestBody UserDTO userDTO) {
        Page<SysUser> page = new PageDTO<>();
        page.setCurrent(userDTO.getCurrent());
        page.setSize(userDTO.getSize());
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userDTO.getUserName())) {
            queryWrapper.eq("user_name", userDTO.getUserName());
        }
        IPage<SysUser> userPage = userService.page(page, queryWrapper);
        userPage.getRecords().forEach(sysUser ->{
            sysUser.setPassword("");
            sysUser.setSalt("");
        });
        return ResultUtils.success(userPage);
    }


    @RequestMapping("/save")
    public ResultVO saveUser(@Valid @RequestBody UserDTO userDTO) {
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDTO, sysUser);
        if (sysUser.getUserId() != null) {
            updateWrapper.eq("user_id", sysUser.getUserId());
        }
        if (StringUtils.isBlank(sysUser.getStatus())) {
            sysUser.setStatus(GlobalConstants.CommonStatusConstants.VALID_STATUS);
        }
        userService.saveOrUpdate(sysUser, updateWrapper);
        return ResultUtils.success();
    }

    @RequestMapping("/delete/{userId}")
    public ResultVO deleteUser(@PathVariable Long userId) {
        userService.removeById(userId);
        return ResultUtils.success();
    }
}
