package com.heyjianjun.shirovuedemo.exception;


import com.alibaba.fastjson.JSON;
import com.heyjianjun.shirovuedemo.constant.GlobalConstants;
import com.heyjianjun.shirovuedemo.utils.LocaleUtil;
import com.heyjianjun.shirovuedemo.utils.ResourceMsgUtil;
import com.heyjianjun.shirovuedemo.utils.ThreadId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author : heyjianjun
 * @create 2021/8/21 17:53
 */
public class BusinessException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(BusinessException.class);

    String errorCode;

    String errorMessage;

    public BusinessException() {
    }

    public BusinessException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = ResourceMsgUtil.getMessage(errorCode, (Object[])null, LocaleUtil.getLocale());
        this.writeBusinessException();
    }

    public BusinessException(String errorCode, String[] keyInfos) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = ResourceMsgUtil.getMessage(errorCode, keyInfos, LocaleUtil.getLocale());
        this.writeBusinessException();
    }

    public BusinessException(final String message, final String errorCode) {
        this.errorCode = String.valueOf(errorCode);
        this.errorMessage = message;
        this.writeBusinessException();
    }



    public BusinessException(final Throwable e) {
        this.errorCode = GlobalConstants.SYS_ERR_CODE;
        this.errorMessage = ResourceMsgUtil.getMessage(errorCode, null, LocaleUtil.getLocale());
        this.initCause(e);
        this.writeBusinessExceptionSys();
    }
    private void writeBusinessException() {
        logger.error("线程：" + ThreadId.getThreadId() + " ; 业务异常编码：" + this.getErrorCode() + " ；业务异常信息：" + this.getErrorMessage());
    }


    private void writeBusinessExceptionSys() {
        logger.error("线程：" + ThreadId.getThreadId() + " ; 业务异常编码：" + this.getErrorCode() + " ；业务异常信息：" + this.getErrorMessage() + " ；具体异常为：" +
                JSON.toJSONString(this.getCause()));
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
