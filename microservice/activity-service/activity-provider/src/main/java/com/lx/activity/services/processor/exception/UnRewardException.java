package com.lx.activity.services.processor.exception;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class UnRewardException extends RuntimeException {

    private static final long serialVersionUID = -4928964211797413219L;

    public UnRewardException(String msg) {
        super(msg);
    }

    public UnRewardException(String msg, Throwable e) {
        super(msg, e);
    }

}
