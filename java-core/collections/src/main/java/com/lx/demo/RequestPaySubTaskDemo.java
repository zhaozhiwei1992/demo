package com.lx.demo;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: 申请凭证数据, 单号相同
 * @date 2021/10/17 上午12:02
 */
public class RequestPaySubTaskDemo {
    private static final List<Map<String, Object>> requestDatas = new ArrayList<>();

    private static final List<Map<String, Object>> paysubDatas = new ArrayList<>();
    private static final Random random = new Random();

    {
        for (int i = 1; i < 100; i++) {
            final Map<String, Object> map = new HashMap<>();
//            guid实际可能不相同
            map.put("guid", i);
            map.put("billcode", i);
            if (random.nextInt(10) > 5) {
                map.put("amt", i * 10);
            } else {
//                随机给负金额, 模拟退款
                map.put("amt", -i * 10);
            }
            requestDatas.add(map);
            paysubDatas.add(map);
        }
    }

    public static void main(String[] args) {

        final RequestPaySubTaskDemo requestPaySubTaskDemo = new RequestPaySubTaskDemo();
        System.out.println("传入申请信息: " + requestDatas);
//        System.out.println(paysubDatas);

        final Map<String, List<String>> map = constractRequestListMap();
        System.out.println("构建结果: " + map);

        // 遍历map处理业务
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
            final String key = stringListEntry.getKey();
            final List<String> value = stringListEntry.getValue();
            if(key.contains("-b")){
//                退款处理, 处理value中的凭证退款->申请退款
            }else{
//                正常业务, 批量处理value中的申请-->凭证
            }
        }

    }

    /**
     * @data: 2021/10/17-上午12:57
     * @User: zhaozhiwei
     * @method: constractRequestListMap

     * @return: java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     * @Description: 描述
     */
    private static Map<String, List<String>> constractRequestListMap() {
        // 根据申请构建数据
//        下标值, 应该从当前接近最大整数开始, 防止排序错误, 直接使用1000开始
        int currentInt =  1001;
        final Map<String, List<String>> map = new TreeMap<>();
        for (int i = 0, requestDatasSize = requestDatas.size(); i < requestDatasSize; i++) {
            Map<String, Object> requestData = requestDatas.get(i);
            final Object amt = requestData.get("amt");
            final BigDecimal bigDecimal = new BigDecimal(String.valueOf(amt));

            if (i != 0) {
                Map<String, Object> preRequestData = requestDatas.get(i - 1);
                final Object preAmt = preRequestData.get("amt");
                final BigDecimal preBigDecimal = new BigDecimal(String.valueOf(preAmt));
                // 状态切换标志, 只要出现金额大于0小于0变化就切换, 下标+1
//                flag变化, key增加, 本次为正数，上次为负数,或者上次为负数,本次为正数(包含0)
                if (
                        (bigDecimal.compareTo(BigDecimal.ZERO) >= 0 && preBigDecimal.compareTo(BigDecimal.ZERO) < 0)
                                || (bigDecimal.compareTo(BigDecimal.ZERO) < 0 && preBigDecimal.compareTo(BigDecimal.ZERO) >= 0)
                ) {
                    currentInt++;
                }

            }

            String key = String.valueOf(currentInt);
            if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
                key = key + "-b";
            }

            final List<String> stringList = map.get(key);
            if (Objects.isNull(stringList)) {
                final ArrayList<String> strings = new ArrayList<>();
                strings.add(String.valueOf(requestData.get("billcode")));
                map.put(key, strings);
            } else {
                stringList.add(String.valueOf(requestData.get("billcode")));
            }
        }
        return map;
    }
}
