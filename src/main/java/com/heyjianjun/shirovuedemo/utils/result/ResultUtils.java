package com.heyjianjun.shirovuedemo.utils.result;

import com.heyjianjun.shirovuedemo.constant.GlobalConstants;
import org.apache.commons.lang.StringUtils;

/**
 * @Author : heyjianjun
 * @create 2021/8/21 18:06
 */
public class ResultUtils {

    public static ResultVO success() {
        return new ResultVO(GlobalConstants.SYS_SUCCESS_CODE, GlobalConstants.SYS_SUCCESS_MSG);
    }

    public static <T> ResultVO<T> success(String code, String msg) {
        return success(code, msg, null);
    }

    public static <T> ResultVO<T> success(String code, String msg, T data) {
        code = StringUtils.isBlank(code) ? GlobalConstants.SYS_SUCCESS_CODE : code;
        msg = StringUtils.isBlank(msg) ? GlobalConstants.SYS_SUCCESS_MSG : msg;
        return new ResultVO<>(code, msg, data);
    }

    public static <T> ResultVO<T> success(T data) {
        return new ResultVO(GlobalConstants.SYS_SUCCESS_CODE, GlobalConstants.SYS_SUCCESS_MSG, data);
    }

    public static ResultVO fail() {
        return new ResultVO(GlobalConstants.SYS_ERR_CODE, GlobalConstants.SYS_ERR_MSG);
    }

    public static ResultVO fail(String code, String message) {
        return new ResultVO(code, message);
    }
}
