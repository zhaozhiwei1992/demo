
package com.example.springbootjcrontab.jcrontab.data;

import org.jcrontab.data.CrontabEntryBean;
import org.jcrontab.data.CrontabEntryException;
import org.jcrontab.data.DataNotFoundException;
import org.jcrontab.data.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JcrontabDataSource
 * @Package com/example/springbootjcrontab/jcrontab/data/JcrontabDataSource.java
 * @Description: 数据库中获取定时任务配置
 * @date 2021/6/22 下午8:30
 */
public class JcrontabDataSource implements DataSource {

    private static final Logger logger = LoggerFactory.getLogger(JcrontabDataSource.class);

    private static class JcrontabDataSourceHolder {
        public static JcrontabDataSource jcrontabDataSource = new JcrontabDataSource();
    }

    public DataSource getInstance() {
        return JcrontabDataSourceHolder.jcrontabDataSource;
    }

    public JcrontabDataSource() {
    }

    private final CrontabParser cp = new CrontabParser();

    @SuppressWarnings("unchecked")
    public CrontabEntryBean[] findAll() throws CrontabEntryException, ClassNotFoundException, SQLException,
            DataNotFoundException {
        final int seconds = 60;
        final int years = 10;

//        String sql = TaskSetDAO.queryAll + " and enable=1 ";
//
//        List<CrontabEntryBean> cebs = daoSupport.query(sql, new RowMapper<CrontabEntryBean>() {
//
//            @Override
//            public CrontabEntryBean mapRow(ResultSet rs, int arg1) throws SQLException {
//                boolean[] bSeconds = new boolean[seconds];
//                boolean[] bYears = new boolean[years];
//
//                String id = rs.getString("jobid");
//                String cronExperssion = rs.getString("cronexpression");
//                cronExperssion = cronExperssion.replace('?', '*');
//                String taskClass = rs.getString("jobclass");
//                // 配置出现空格会导致表达式验证失败
//                String extrainfo = rs.getString("jobname").replace(" ","");
//                String appId = rs.getString("appid");
//
//                String line = cronExperssion + " " + "gov.mof.fasp2.task.consumer.TaskClient#executeTask"  + " " +
//                extrainfo + " " + appId + " " + id+ " " + taskClass;
//
//                String sBusinessDays = rs.getString("enable");
//                boolean businessDays = false;
//                if (sBusinessDays != null && sBusinessDays.equalsIgnoreCase("1")) {
//                    businessDays = true;
//                }
//                CrontabEntryBean ceb = null;
//                try {
//                    ceb = cp.marshall(line);
//
//                    cp.parseToken("*", bYears, false);
//                    // ceb.setId(id);
//                    ceb.setBYears(bYears);
//                    ceb.setYears("*");
//
//                    cp.parseToken("0", bSeconds, false);
//                    ceb.setBSeconds(bSeconds);
//                    ceb.setSeconds("0");
//                    ceb.setBusinessDays(businessDays);
//
//                } catch (CrontabEntryException e) {
//                    logger.error("", e);
//                }
//
//                return ceb;
//            }
//        });

        List<CrontabEntryBean> cebs = new ArrayList<>();
        CrontabEntryBean ceb = null;

        boolean[] bSeconds = new boolean[seconds];
        boolean[] bYears = new boolean[years];
        try {

//            crontab表达式指定时间下，执行TaskClient.executeTask方法
//            从而触发BdgCommonTask.execute方法，里面的参数就是bdg, 或者bdg.timertask.SyncIndexDataTask

//            相当与实现了一个分布式定时任务
            String line = "0 51 10 * * * *" + " " + "com.example.springbootjcrontab.consumer.TaskClient#executeTask" + " " +
                    "定时任务测试" + " " + "bdg" + " " + "bdg.timertask.SyncIndexDataTask" + " " + "com.example.springbootjcrontab.business.BdgCommonTask#execute";

            ceb = cp.marshall(line);

            cp.parseToken("*", bYears, false);
            ceb.setBYears(bYears);
            ceb.setYears("*");

            cp.parseToken("0", bSeconds, false);
            ceb.setBSeconds(bSeconds);
            ceb.setSeconds("0");
            ceb.setBusinessDays(true);

            cebs.add(ceb);
        } catch (CrontabEntryException e) {
            logger.error("", e);
        }


        if (cebs.isEmpty()) {
            return new CrontabEntryBean[]{};
        }

        return cebs.toArray(new CrontabEntryBean[cebs.size()]);
    }

    public CrontabEntryBean find(CrontabEntryBean ceb) throws CrontabEntryException, ClassNotFoundException,
            SQLException, DataNotFoundException {
        logger.info(" find ");
        CrontabEntryBean[] cebra = findAll();
        for (int i = 0; i < cebra.length; i++) {
            if (cebra[i].equals(ceb)) {
                return cebra[i];
            }
        }
        throw new DataNotFoundException("Unable to find :" + ceb);
    }

    public void remove(CrontabEntryBean[] arg0) throws Exception {
        logger.info("remove CrontabEntryBean : " + arg0);

    }

    public void store(CrontabEntryBean arg0) throws Exception {
        logger.info("store1 CrontabEntryBean : " + arg0);

    }

    public void store(CrontabEntryBean[] arg0) throws Exception {
        logger.info("store2 CrontabEntryBean : " + arg0);
    }

}