package com.heyjianjun.shirovuedemo.controller.role;

import com.heyjianjun.shirovuedemo.controller.BaseController;
import com.heyjianjun.shirovuedemo.dto.UserDTO;
import com.heyjianjun.shirovuedemo.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Object list(@RequestBody UserDTO userDTO) {

        return null;
    }
}
