package com.example.springbootactivity.activiti5.service;

import com.example.springbootactivity.activiti5.dto.Action;
import com.example.springbootactivity.activiti5.dto.ProcessResult;
import com.example.springbootactivity.domain.Person;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootactivity.activiti5.service
 * @Description: 封装工作流一些基本操作
 */
@Service
public class ActivityService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public ProcessResult doAction(final String bpmnType
            , final Action action
            , final List<Person> datas
            , final String userId) {
        try {
            logger.info("开始执行动作doAction");
            long st = System.currentTimeMillis();

//            根据bpmnType获取当前最新部署的工作流信息
            final ProcessDefinition processDefinition =
                    repositoryService.createProcessDefinitionQuery().processDefinitionKey(bpmnType).latestVersion().singleResult();

            final HashMap<String, Object> param = new HashMap<>();
            param.put("processDefinitionId", processDefinition.getId());
            param.put("userid", userId);
            param.put("businessKey", datas.get(2).getId());

            ProcessResult processResult = new ProcessResult();

            if (action.isCancel()) {
//                doCancel();
            } else if (action.isBack()) {
                // 线上退回
//                doBack();
            } else if (Action.DELETE.equals(action.getActionType())) {
//               删除工作流
                doDelete(param);
            } else if (Action.CREATE.equals(action.getActionType())) {
                processResult = doCreate(param);
            } else {
                String variable = action.getVariable();
                processResult = doAudit(param);
            }
            logger.error("执行动作" + (action.isCancel() ? "Cancel" : "") + action.getActionType() + "用时："
                    + (System.currentTimeMillis() - st) + "ms");
            return processResult;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private void doDelete(HashMap<String, Object> param) {
        //流程没有结束：
        final String businessKey = String.valueOf(param.get("businessKey"));
//        获取当前用户的待办节点
        String processDefinitionId = (String) param.get("processDefinitionId");
//        根据业务数据businessKey获取 流程实例
        //根据当前数据主键, 获取流程实例信息
        HistoricProcessInstance processInstance =
                historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionId(processDefinitionId)
                        .processInstanceBusinessKey(businessKey)
                        .singleResult();
        String processInstanceId = processInstance.getId();
//        获取流程实例当前节点
        final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        String comment = "删除原因";
        taskService.addComment(task.getId(), processInstanceId, comment);
        runtimeService.deleteProcessInstance(processInstanceId, comment);
        historyService.deleteHistoricProcessInstance(processInstanceId);

        //流程已经结束：
        // historyService.deleteHistoricProcessInstance(procesInstanceId);
        //runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    /**
     * @Description: 流程往后走一岗
     * 实现:
     * 哪条数据, 走哪个图, 是否通过
     */
    private ProcessResult doAudit(HashMap<String, Object> param) {
        final String businessKey = String.valueOf(param.get("businessKey"));
//        获取当前用户的待办节点
        String processDefinitionId = (String) param.get("processDefinitionId");

        //根据当前数据主键, 获取流程实例信息
        HistoricProcessInstance processInstance =
                historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionId(processDefinitionId)
                        .processInstanceBusinessKey(businessKey)
                        .singleResult();

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        taskService.complete(task.getId());
        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        final ProcessResult processResult = new ProcessResult<List<Map>>("20001", "操作流程成功！");
        final List<Map> arrayList = new ArrayList<>();
        final Map<String, Object> map = new HashMap<>();
        map.put("businessKey", businessKey);
        if(!Objects.isNull(task)){
//            结束节点返回空, 可以约定结束节点描述为011
            logger.info("走流程后: \n工作流节点信息:{} \n工作流节点id:{}", task.getDescription(), task.getId());
            map.put("taskId", task.getId());
            map.put("description", task.getDescription());
        }else{
            map.put("taskId", "");
            map.put("description", "011");
        }
        arrayList.add(map);
        processResult.setResult(arrayList);
        return processResult;
    }

    /**
     * @param param :
     * @data: 2022/4/19-下午3:02
     * @User: zhaozhiwei
     * @method: doCreate
     * @return: com.example.springbootactivity.activiti5.dto.ProcessResult
     * @Description: 流程启动
     */
    private ProcessResult doCreate(Map<String, Object> param) {
        String processDefinitionId = (String) param.get("processDefinitionId");
        //流程发起人
        String businessKey = String.valueOf(param.get("businessKey"));

        if (StringUtils.isEmpty(processDefinitionId)) {
            return new ProcessResult<List<Map>>("40001", "参数错误");
        }

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey,
                param);
        logger.info("启动成功,\n 流程id: {}, \n流程processDefinitionId: {}, \n流程definitionName:{}, \n流程startUserId:{}, " +
                        "\n流程businessKey:{}"
                , processInstance.getId()
                , processInstance.getProcessDefinitionId()
                , processInstance.getProcessDefinitionName()
                , processInstance.getStartUserId()
                , processInstance.getBusinessKey()
        );
        final ProcessResult processResult = new ProcessResult<List<Map>>("20001", "启动流程成功！");
        final List<Map> arrayList = new ArrayList<>();
        final Map<String, Object> map = new HashMap<>();
        map.put("businessKey", businessKey);
        map.put("taskId", "");
        map.put("description", "000");
        arrayList.add(map);
        processResult.setResult(arrayList);
        return processResult;
    }

    /**
     * @Description: 获取待办信息, 只有待办才需要往后走
     */
    public List<Task> findTaskByUserId(String processDefinitionId, String userName) {
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionId(processDefinitionId)
//                .processDefinitionKey(processDefinitionKey)
                //只查询该任务负责人的任务
                .taskAssignee(userName)
                .list();
        return taskList;
    }

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngineConfigurationImpl processEngineConfiguration;

    /**
     * @Description: 获取历史审核信息
     */
    public void auditInfo(String bpmnType, String businessKey) {
    }

    /**
     * 流程图高亮显示
     * 首先启动流程，获取processInstanceId，替换即可生成
     *
     * @throws Exception
     */
    public void queryActiveHighLighted(String bpmnType, String businessKey) throws Exception {

//        根据流程类型和业务数据id获取 processInstanceId

        final ProcessDefinition processDefinition =
                repositoryService.createProcessDefinitionQuery()
                        .processDefinitionKey(bpmnType)
                        .latestVersion().singleResult();

        //获取历史流程实例
        HistoricProcessInstance processInstance =
                historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionId(processDefinition.getId())
                        .processInstanceBusinessKey(businessKey)
                        .singleResult();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity =
                (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

        List<HistoricActivityInstance> highLightedActivitList =
                historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<String>();

        //高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity, highLightedActivitList);

        for (HistoricActivityInstance tempActivity : highLightedActivitList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }
        //配置字体
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,
                highLightedFlows, "宋体", "微软雅黑", "黑体", null, 2.0);
        BufferedImage bi = ImageIO.read(imageStream);
        File file = new File("demo2.png");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        ImageIO.write(bi, "png", fos);
        fos.close();
        imageStream.close();
        System.out.println("图片生成成功");
    }

    /**
     * 获取需要高亮的线
     *
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {

        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        return highFlows;
    }

}
