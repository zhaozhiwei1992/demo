package com.example.controller;

import com.example.domain.User;
import com.example.service.AggregationService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.Observer;

import java.util.HashMap;

@Slf4j
@RestController
public class AggregationController {

    @Autowired
    private AggregationService aggregationService;

    /**
     * curl -X GET http://localhost:10086/aggregate/998
     * 结果:
     * {"user1":{"id":998,"name":"测试聚合","age":998},"user2":{"id":998,"name":"测试聚合","age":998}}%
     * @param id
     * @return
     */
    @GetMapping("/aggregate/{id}")
    public DeferredResult<HashMap<String, User>> aggregate(@PathVariable("id") Long id){
        Observable<HashMap<String, User>> result = this.aggregateObservable(id);
        return this.toDeferredResult(result);
    }

    private DeferredResult<HashMap<String, User>> toDeferredResult(Observable<HashMap<String, User>> details) {
        final DeferredResult<HashMap<String, User>> result = new DeferredResult<>();

        //订阅
        details.subscribe(new Observer<HashMap<String, User>>() {
            @Override
            public void onCompleted() {
                log.info("完成.....");
            }

            @Override
            public void onError(Throwable e) {
                log.error("发生错误, {}", e);
            }

            @Override
            public void onNext(HashMap<String, User> stringUserHashMap) {
                result.setResult(stringUserHashMap);
            }
        });
        return result;
    }

    private Observable<HashMap<String, User>> aggregateObservable(Long id) {
        //合并结果
        return Observable.zip(
                this.aggregationService.getUserByID(id),
                this.aggregationService.getUserByIDCopy(id),
                ((user1, user2) -> {
                    final HashMap<String, User> map = Maps.newHashMap();
                    map.put("user1", user1);
                    map.put("user2", user2);
                    return map;
                })
        );
    }
}
