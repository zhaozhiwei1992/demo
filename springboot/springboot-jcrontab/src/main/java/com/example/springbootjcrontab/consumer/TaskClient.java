package com.example.springbootjcrontab.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class TaskClient {

    private static final Logger logger = LoggerFactory.getLogger(TaskClient.class);

    private static final List<String> runTasks = new Vector<String>();
    private static RestTemplate restTemplate = null;

    public static RestTemplate getRestTemplate() {
        if (restTemplate == null || true) {
            restTemplate = new RestTemplate();
            List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
            if (interceptors == null) {
                interceptors = new ArrayList<>();
            }
            restTemplate.setInterceptors(interceptors);
        }
        return restTemplate;
    }

    private static String getServerAddress(String appid) {
        String serverAddress = "";
        return serverAddress;
    }

    /**
     * 执行定时任务
     *
     * @param args       参数
     * @return
     */
    public static void executeTask(String[] args) throws Exception {
        String taskclass = args[3];
        if(runTasks.contains(taskclass)){
            return;
        }else{
            runTasks.add(taskclass);
        }
        String appid = args[1];
        String serverAddress = getServerAddress(appid);
        if(serverAddress == null){
            logger.warn("微服务{}不存在！", appid);
            //没有找到微服务就不再调用了，下次再尝试
            runTasks.remove(taskclass);
            throw new Exception("没找到定时任务的运行服务："+appid);
//            return;
        }
        String url = String.format("http://%s/buscommon/task/executeTask", serverAddress);
        Map result = null;
        try {
            //设置访问参数
            MultiValueMap params = new LinkedMultiValueMap();
            params.put("args", Arrays.asList(args));
            ResponseEntity<Map> responseEntity = getRestTemplate().postForEntity(
                    url,
                    params, Map.class);
            result = responseEntity.getBody();
            if(!"success".equals(result.get("status"))){
                System.out.println(result);
            }
        } catch (Throwable e1) {
            e1.printStackTrace();
            logger.error(String.format("调用业务微服务%s,请求参数%s", serverAddress, args) + e1.getMessage());
            result = new HashMap<>();
            result.put("status","fail");
            result.put("message",e1.getMessage());
            throw e1;
        }finally {
            runTasks.remove(taskclass);
        }

    }
}
