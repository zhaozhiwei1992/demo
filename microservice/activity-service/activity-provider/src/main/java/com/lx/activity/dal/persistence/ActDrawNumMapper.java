package com.lx.activity.dal.persistence;

import com.lx.activity.dal.entitys.ActDrawNum;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface ActDrawNumMapper {

    /**
     * 插入抽奖次数记录
     * @param ActDrawNum
     * @return
     */
    int inputDrawNumber(ActDrawNum ActDrawNum);

    /**
     * 更新已抽次数
     * @param uid
     * @return
     */
    int updateNowNumber(int uid);

    /**
     * 更新总的抽次数
     * @param uid
     * @return
     */
    int updateMaxNumber(int uid);

    /**
     * 查询指定用户的抽奖次数
     * @param uid
     * @return
     */
    ActDrawNum queryDrawNumForUid(int uid);
}
