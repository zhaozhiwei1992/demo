package com.lx.demo.controller;

import com.lx.activity.commons.ResultResp;
import com.lx.demo.controller.support.ResponseData;
import com.lx.activity.draw.ActivityTurntableDrawService;
import com.lx.activity.draw.bean.ActivityTurntableDrawReq;
import com.lx.activity.draw.bean.AwardDrawRecordBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */


@RestController
public class DrawController extends BaseController{


    @Autowired
    ActivityTurntableDrawService activityTurntableDrawService;


    @PostMapping("/doDraw")
    public ResponseData doDraw(){
        ActivityTurntableDrawReq drawReq=new ActivityTurntableDrawReq();
        drawReq.setUid(Integer.parseInt(getUid()));
        ResultResp<AwardDrawRecordBean> resp= activityTurntableDrawService.doDraw(drawReq);
        ResponseData data=new ResponseData();
        data.setCode(resp.getReturnCodeEnum().getCode());
        data.setMessage(resp.getReturnCodeEnum().getMsg());
        data.setData(resp.getResult());
        return data;
    }
}
