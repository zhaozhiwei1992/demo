package com.example.springcloudhystrixserviceprovider.aop;

import com.example.springcloudhystrixserviceprovider.domain.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 通过api方式使用hystrix
 */
public class UserHystrixCommand extends HystrixCommand<List<User>> {

    private static final Logger logger = LoggerFactory.getLogger(UserHystrixCommand.class);

    private String serviceid;

    public UserHystrixCommand(String serviceid) {
        super(HystrixCommandGroupKey.Factory.asKey(
                "User-Ribbon-Client"),
                100);
    }

    private static final Random random = new Random();
    /**
     * 主逻辑
     * @return
     * @throws Exception
     */
    @Override
    protected List<User> run() throws Exception {
        // 通过休眠来模拟执行时间
        long executeTime = random.nextInt(200);
        System.out.println("Execute Time : " + executeTime + " ms");
        Thread.sleep(executeTime);
        return Arrays.asList(new User(1L, "张三", 18), new User(2L, "李四", 19));
    }

    /**
     * api 方式实现， 方法名字固定
     * {@see com.netflix.hystrix.HystrixCommand#getFallbackMethodName()}
     *
     *  protected String getFallbackMethodName() {
     *         return "getFallback";
     *     }
     * @return
     */
    public List<User> getFallback(){
        logger.error("execute timeout 100ms");
        return Collections.emptyList();
    }

}
