package com.heyjianjun.shirovuedemo.shiro.realm;

import com.heyjianjun.shirovuedemo.constant.GlobalConstants;
import com.heyjianjun.shirovuedemo.dto.RoleDOT;
import com.heyjianjun.shirovuedemo.dto.UserDTO;
import com.heyjianjun.shirovuedemo.service.ISysUserService;
import com.heyjianjun.shirovuedemo.utils.MyApplicationBeanUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        if (StringUtils.equals(user.getStatus(), GlobalConstants.LoginStatus.OFFLINE)) {
            getRedisTemplate().delete(token);
            throw new IncorrectCredentialsException("您的账号已在别处登录，请重新登录");
        }
        // 只允许一个客户端登录
        String loginToken = (String) getRedisTemplate().opsForValue().get(GlobalConstants.USER_LOGIN_STATUS + user.getUserId());
        if (StringUtils.isNotBlank(loginToken)) {
            // 将已登录账户置为下线
            UserDTO loginUser = new UserDTO();
            BeanUtils.copyProperties(user, loginUser);
            loginUser.setStatus(GlobalConstants.LoginStatus.OFFLINE);
            getRedisTemplate().opsForValue().set(loginToken, loginUser, GlobalConstants.LOGIN_EXPIRE, TimeUnit.HOURS);
        }
        user.setPassword(token);
        getRedisTemplate().opsForValue().set(GlobalConstants.USER_LOGIN_STATUS + user.getUserId(), token);
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
