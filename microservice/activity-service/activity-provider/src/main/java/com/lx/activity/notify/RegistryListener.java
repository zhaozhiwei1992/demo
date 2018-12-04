package com.lx.activity.notify;

import com.lx.activity.dal.entitys.ActDrawNum;
import com.lx.activity.dal.persistence.ActDrawNumMapper;
import com.lx.common.utils.JsonUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service
public class RegistryListener implements MessageListener<Integer,String> {

    @Autowired
    ActDrawNumMapper actDrawNumMapper;

    @Override
    public void onMessage(ConsumerRecord<Integer, String> integerStringConsumerRecord) {
        UserBean userBean=null;
        try {
            //幂等校验

            String jsonStr=integerStringConsumerRecord.value();
            userBean=(UserBean) JsonUtils.json2Object(integerStringConsumerRecord.value(),UserBean.class);
            ActDrawNum actDrawNum=new ActDrawNum();
            actDrawNum.setName(userBean.getName());
            actDrawNum.setNowNumber(0);
            actDrawNum.setUid(userBean.getUid());
            actDrawNum.setMaxNumber(1);
            actDrawNumMapper.inputDrawNumber(actDrawNum);
        }catch (DuplicateKeyException e){
            //已经存在用户的抽奖资格记录，则累加抽奖机会
            actDrawNumMapper.updateMaxNumber(userBean.getUid());
        }catch(Exception e){
            throw new RuntimeException("消息处理异常:"+e);
        }
    }
}
