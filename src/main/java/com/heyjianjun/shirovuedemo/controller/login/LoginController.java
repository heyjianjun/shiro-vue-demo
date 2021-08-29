package com.heyjianjun.shirovuedemo.controller.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heyjianjun.shirovuedemo.constant.ErrorConstants;
import com.heyjianjun.shirovuedemo.constant.GlobalConstants;
import com.heyjianjun.shirovuedemo.controller.BaseController;
import com.heyjianjun.shirovuedemo.dto.PermissionDTO;
import com.heyjianjun.shirovuedemo.dto.RoleDOT;
import com.heyjianjun.shirovuedemo.dto.UserDTO;
import com.heyjianjun.shirovuedemo.entity.SysUser;
import com.heyjianjun.shirovuedemo.exception.BusinessException;
import com.heyjianjun.shirovuedemo.service.ILoginService;
import com.heyjianjun.shirovuedemo.service.ISysRolePermissionService;
import com.heyjianjun.shirovuedemo.service.ISysUserRoleService;
import com.heyjianjun.shirovuedemo.service.ISysUserService;
import com.heyjianjun.shirovuedemo.shiro.auth.AuthToken;
import com.heyjianjun.shirovuedemo.utils.generate.SaltUtils;
import com.heyjianjun.shirovuedemo.utils.result.ResultUtils;
import com.heyjianjun.shirovuedemo.utils.result.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : heyjianjun
 * @create 2021/8/24 16:47
 */
@RestController
@RequestMapping("/sys")
public class LoginController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ISysRolePermissionService rolePermissionService;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/login")
    public ResultVO login(@RequestBody SysUser sysUser) {
        String userName = sysUser.getUserName();
        String password = sysUser.getPassword();
        // 根据用户名查找
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        queryWrapper.eq("status", GlobalConstants.CommonStatusConstants.VALID_STATUS);
        SysUser one = userService.getOne(queryWrapper);
        if (one == null) {
            throw new BusinessException(ErrorConstants.ACCOUNT_NOT_EXIST);
        }
        // 验证密码
        String salt = one.getSalt();
        //明文密码进行md5+salt+hash散列
        Md5Hash md5Hash = new Md5Hash(password, salt, 1024);
        if (!StringUtils.equals(md5Hash.toHex(), one.getPassword())) {
            throw new BusinessException(ErrorConstants.PASSWORD_ERROR);
        }
        // 查询角色信息
        UserDTO user = new UserDTO();
        BeanUtils.copyProperties(one, user);
        List<RoleDOT> roleList = userRoleService.queryRoleListByUserId(one.getUserId());
        //根据角色查询权限
        roleList.forEach(e -> {
            List<PermissionDTO> permissionList = rolePermissionService.queryPermissionListByRoleId(e.getRoleId());
            e.setPermissions(permissionList);
        });
        user.setRoles(roleList);
        //创建token
        AuthToken authToken = loginService.createToken(user);
        // 登录
        Subject subject = SecurityUtils.getSubject();
        subject.login(authToken);
        Map<String, Object> result = new HashMap<>();
        result.put("token", authToken.getPrincipal());
        return ResultUtils.success(result);
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @RequestMapping("register")
    @ResponseBody
    public ResultVO register(@Valid @RequestBody SysUser user) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());
        SysUser sysUser = userService.getOne(queryWrapper);
        if (sysUser != null) {
            throw new BusinessException(ErrorConstants.ACCOUNT_EXIST);
        }
        String salt = SaltUtils.generateSale(6);
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        user.setStatus(GlobalConstants.CommonStatusConstants.VALID_STATUS);
        user.setSalt(salt);
        userService.save(user);
        return ResultUtils.success();
    }

    @RequestMapping("logout")
    @ResponseBody
    public ResultVO logout() {
        String token = request.getHeader("token");
        UserDTO user = (UserDTO) redisTemplate.opsForValue().get(token);
        redisTemplate.delete(token);
        redisTemplate.delete(GlobalConstants.USER_LOGIN_STATUS + user.getUserId());
        Subject subject = SecurityUtils.getSubject();
        subject.logout();//退出用户
        return ResultUtils.success();
    }

    @RequestMapping("test")
    @RequiresPermissions("user:*")
    public ResultVO test(@RequestBody Map<String, Object> params) {
        return ResultUtils.success();
    }

    @RequestMapping("test1")
    public ResultVO test1(@RequestBody Map<String, Object> params) {
        return ResultUtils.success();
    }
}
