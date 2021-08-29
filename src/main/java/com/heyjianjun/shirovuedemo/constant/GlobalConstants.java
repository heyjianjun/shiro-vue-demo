package com.heyjianjun.shirovuedemo.constant;

/**
 * @Author : heyjianjun
 * @create 2021/8/21 18:11
 */
public class GlobalConstants {

    /**
     * 系统异常
     */
    public static final String SYS_ERR_CODE = "9999";

    public static final String SYS_ERR_MSG = "系统异常";

    public static final String SYS_SUCCESS_CODE = "0000";

    public static final String SYS_SUCCESS_MSG = "成功";

    /**
     * 登录的用户的token
     */
    public static final String USER_LOGIN_STATUS = "userLogin:";

    /**
     * token失效时间 小时
     */
    public final static long LOGIN_EXPIRE = 24;

   public static class CommonStatusConstants {

        /**
         * 有效
         */
        public static final String VALID_STATUS = "0";

        /**
         * 删除
         */
        public static final String DELETE_STATUS = "1";

    }

    public static class LoginStatus{
        /**
         * 正常
         *
         */
        public static final String NORMAL = "0";

        /**
         * 已被挤下线
         */
        public static final String OFFLINE = "1";
    }
}
