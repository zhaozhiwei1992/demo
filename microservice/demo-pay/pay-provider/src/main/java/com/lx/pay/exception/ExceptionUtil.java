
package com.lx.pay.exception;


import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.lx.pay.commons.PayReturnCodeEnum;

public class ExceptionUtil{

    private static Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

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
        }else if(e instanceof BizException){
            ex = new ServiceException(((BizException) e).getErrorCode(), ((BizException) e).getMessage());
        }
        else if (e instanceof Exception) {
            ex = new ServiceException(PayReturnCodeEnum.SYSTEM_ERROR.getCode(),
                    PayReturnCodeEnum.SYSTEM_ERROR.getMsg());
        }
        logger.error("ExceptionUtil.handlerException4biz,Exception=" + e.getMessage(), e);
        return ex;
    }
}
