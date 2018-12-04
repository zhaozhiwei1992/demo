package com.lx.user.util;

import com.lx.user.constants.ResponseCodeEnum;
import com.lx.user.exception.ServiceException;
import com.lx.user.exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 赵志伟
 * @ClassName: ExceptionUtil
 * @description [描述该类的功能]
 * @create 2018-07-07 上午10:13
 **/
public class ExceptionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);
    /**
     * 将下层抛出的异常转换为resp返回码
     *
     * @param e Exception
     * @return
     */
    public static Exception handlerException4biz(Exception e) {
        Exception ex = null;
        if (!(e instanceof Exception)) {
            return null;
        }
        if (e instanceof ValidateException) {
            ex = new ServiceException(((ValidateException) e).getErrorCode(), ((ValidateException) e).getErrorMessage());
        }else if (e instanceof Exception) {
            ex = new ServiceException(ResponseCodeEnum.SYSTEM_BUSY.getCode(),
                    ResponseCodeEnum.SYSTEM_BUSY.getMsg());
        }
        logger.error("ExceptionUtil.handlerException4biz,Exception=" + e.getMessage(), e);
        return ex;
    }
}
