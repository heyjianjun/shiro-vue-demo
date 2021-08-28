package com.heyjianjun.shirovuedemo.shiro.realm;

import com.heyjianjun.shirovuedemo.dto.RoleDOT;
import com.heyjianjun.shirovuedemo.dto.UserDTO;
import com.heyjianjun.shirovuedemo.service.ISysUserService;
import com.heyjianjun.shirovuedemo.utils.MyApplicationBeanUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @Author : heyjianjun
 * @create 2021/8/24 15:19
 *
 * 自定义realm
 */
public class CustomerRealm extends AuthorizingRealm {


    private ISysUserService userService;

    private RedisTemplate redisTemplate;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserDTO user = (UserDTO) principalCollection.getPrimaryPrincipal();
        List<RoleDOT> roles = user.getRoles();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        roles.stream().forEach(role->{
            simpleAuthorizationInfo.addRole(role.getRoleCode());
            role.getPermissions().forEach(permission->{
                simpleAuthorizationInfo.addStringPermission(permission.getPermissionCode());
            });
        });
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();
        // 查询redis
        UserDTO user = (UserDTO) getRedisTemplate().opsForValue().get(token);
        // token不存在
        if (user == null) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        user.setPassword(token);
        return new SimpleAuthenticationInfo(user, token, this.getName());
    }

    /**
     * 退出登录操作
     * @param principals
     */
    @Override
    public void onLogout(PrincipalCollection principals) {
        super.onLogout(principals);
    }

    public ISysUserService getUserService() {
        if (userService == null) {
             userService = (ISysUserService) MyApplicationBeanUtil.getBean("SysUserServiceImpl");
        }
        return userService;
    }

    public RedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate) MyApplicationBeanUtil.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}
