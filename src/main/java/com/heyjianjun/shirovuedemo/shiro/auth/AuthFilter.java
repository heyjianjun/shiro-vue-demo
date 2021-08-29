package com.heyjianjun.shirovuedemo.shiro.auth;

import com.alibaba.fastjson.JSONObject;
import com.heyjianjun.shirovuedemo.constant.GlobalConstants;
import com.heyjianjun.shirovuedemo.dto.UserDTO;
import com.heyjianjun.shirovuedemo.utils.MyApplicationBeanUtil;
import com.heyjianjun.shirovuedemo.utils.result.ResultUtils;
import com.heyjianjun.shirovuedemo.utils.result.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : heyjianjun
 * @create 2021/8/25 14:06
 */
public class AuthFilter extends AuthenticatingFilter {


    private RedisTemplate redisTemplate;

    /**
     * 创建token
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        AuthToken authToken = new AuthToken(token);
        return authToken;
    }

    /**
     * 登录成功后序操作
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        // 登录成功执行登录成功后序操作, 比如记录日志 登录时间 登录ip等
        return super.onLoginSuccess(token, subject, request, response);
    }

    /**
     * 登录失败 token失效
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setCharacterEncoding("UTF-8");
        ResultVO resultVO = ResultUtils.fail("401", e.getMessage());
        try {
            httpResponse.getWriter().print(JSONObject.toJSON(resultVO));
        } catch (IOException ioException) {
            return false;
        }
        return false;
    }

    /**
     * 拦截所有请求
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return false;
    }

    /**
     * 拒绝访问
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //获取请求token，如果token不存在，直接返回
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setCharacterEncoding("UTF-8");
            ResultVO resultVO = ResultUtils.fail("401", "请先登录");
            httpResponse.getWriter().print(JSONObject.toJSON(resultVO));
            return false;
        }
        // 检查token是否失效
        UserDTO user = (UserDTO) getRedisTemplate().opsForValue().get(token);
        if (user == null) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setCharacterEncoding("UTF-8");
            ResultVO resultVO = ResultUtils.fail("401", "登录已失效,请重新登录");
            httpResponse.getWriter().print(JSONObject.toJSON(resultVO));
            return false;
        }
        if (StringUtils.equals(user.getStatus(), GlobalConstants.LoginStatus.OFFLINE)) {
            getRedisTemplate().delete(token);
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setCharacterEncoding("UTF-8");
            ResultVO resultVO = ResultUtils.fail("401", "您的账号已在别处登录，请重新登录");
            httpResponse.getWriter().print(JSONObject.toJSON(resultVO));
            return false;
        }
        // 登录对象不存在或者认证已经失效,重新登录
        Subject subject = getSubject(servletRequest, servletResponse);
        if (subject == null || !subject.isAuthenticated()) {
            return executeLogin(servletRequest, servletResponse);
        }
        return true;
    }

    public RedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate) MyApplicationBeanUtil.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}
