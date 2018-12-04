package com.lx.activity.dal.persistence;


import com.lx.activity.dal.entitys.ActDrawRecord;

import java.util.List;

public interface ActDrawRecordMapper {
	/**
	 * 添加抽奖记录
	 * */
	int addActDrawRecord(ActDrawRecord actDrawRecord);


	/**
	 * 查询中奖记录信息
	 * @return 中奖记录列表（包含轮播的10条记录）
	 */
	List<ActDrawRecord> queryDrawRecordList();

}
