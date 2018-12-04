package com.lx.activity.services.processor.exception;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class RewardException extends RuntimeException {

    public RewardException(String msg) {
        super(msg);
    }

    public RewardException(String msg, Throwable e) {
        super(msg, e);
    }
}
