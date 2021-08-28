package com.heyjianjun.shirovuedemo.exception;

import com.heyjianjun.shirovuedemo.constant.GlobalConstants;
import com.heyjianjun.shirovuedemo.utils.result.ResultUtils;
import com.heyjianjun.shirovuedemo.utils.result.ResultVO;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author : heyjianjun
 * @create 2021/8/25 16:06
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResultVO errorHandler(HttpServletRequest req, HttpServletResponse res, BusinessException e) {
        return ResultUtils.fail(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * 账户异常拦截
     * @param req
     * @param res
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(UnknownAccountException.class)
    public ResultVO loginErrorHandler(HttpServletRequest req, HttpServletResponse res, UnknownAccountException e) {
        return ResultUtils.fail(GlobalConstants.SYS_ERR_CODE, "账户不存在");
    }

    @ResponseBody
    @ExceptionHandler({UnauthorizedException.class})
    public ResultVO authorizedErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        return ResultUtils.fail(GlobalConstants.SYS_ERR_CODE, "权限不足");
    }

    @ResponseBody
    @ExceptionHandler({UnauthenticatedException.class})
    public ResultVO authenticatedErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        return ResultUtils.fail(GlobalConstants.SYS_ERR_CODE, "认证失败");
    }

    /**
     * 参数异常拦截
     * @param req
     * @param res
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO paramsErrorHandler(HttpServletRequest req, HttpServletResponse res, MethodArgumentNotValidException e) {
        return ResultUtils.fail("params_error", e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultVO commonErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        return ResultUtils.fail();
    }

}
