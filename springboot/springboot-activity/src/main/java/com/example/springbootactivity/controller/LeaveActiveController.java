package com.example.springbootactivity.controller;

import com.example.springbootactivity.activiti5.dto.Action;
import com.example.springbootactivity.activiti5.dto.ProcessResult;
import com.example.springbootactivity.activiti5.service.ActivityService;
import com.example.springbootactivity.domain.Person;
import com.example.springbootactivity.repository.PersonRepository;
import org.activiti.engine.task.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootactivity.controller
 * @Description: 请假销假流程测试
 * 开始流程
 * |
 * |
 * 审核
 * |
 * |
 * 审核2
 * |
 * |
 * 结束
 * @date 2022/4/19 上午9:33
 */
@RestController
@RequestMapping("/leave")
public class LeaveActiveController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private PersonRepository personRepository;

    /**
     * @param bpmnType : 工作流类型
     * @param userid   : 发起人
     * @data: 2022/4/19-上午9:41
     * @User: zhaozhiwei
     * @method: start
     * @return: java.lang.String
     * @Description: 流程开始
     * curl -X POST http://127.0.0.1:8080/leave/start\?bpmnType\=process\&userid\=zhangsan
     * -H "Content-Type:application/json;charset=UTF-8" -H "Accept:application/json;charset=UTF-8"
     */
    @PostMapping("/start")
    public String start(String bpmnType, String userid) {
        final Action action = new Action(Action.CREATE);
        final List<Person> personList = personRepository.findAll();
        final ProcessResult actionResult = activityService.doAction(bpmnType, action, personList, userid);
        return "";
    }

    /**
     * @data: 2022/4/19-下午5:34
     * @User: zhaozhiwei
     * @method: audit
      * @param id :
 * @param userid :
     * @return: java.lang.String
     * @Description: 描述
     *
     * curl -X POST http://127.0.0.1:8080/leave/start\?bpmnType\=process\&userid\=zhangsan
     * -H "Content-Type:application/json;charset=UTF-8" -H "Accept:application/json;charset=UTF-8"
     */
    @PostMapping("/audit")
    public String audit(String id, String userid) {
        final Action action = new Action(Action.AUDIT);
        final List<Person> personList = personRepository.findAll();
        final ProcessResult actionResult = activityService.doAction(id, action, personList, userid);
//       必要场景下, 根据返回结果更新业务数据中状态标识(description)和节点标识(taskid), 减少调用工作流查询
        return "";
    }

    @PostMapping("/delete")
    public String delete(String id, String userid) {
        return "";
    }

    @PostMapping("/obsolute")
    public String obsolute(String id, String userid) {
        final Action action = new Action(Action.OBSOLETE);
        final List<Person> personList = personRepository.findAll();
        final ProcessResult actionResult = activityService.doAction(id, action, personList, userid);
        return "";
    }

    @PostMapping("/auditInfo")
    public String auditInfo(String bpmnType, String dataId) {
        activityService.auditInfo(bpmnType, dataId);
        return "";
    }

    /**
     * 获取待办任务列表
     */
    @RequestMapping("findTaskListByUserName")
    public ResponseEntity findTaskListByUserName(@Param("userName") String userName) {
        List<Task> taskList = activityService.findTaskByUserId("10001", userName);
        //任务列表：[Task[id=5005, name=填写请假申请单]]
        System.out.println("任务列表：" + taskList);
        List resultList = new ArrayList();
        taskList.forEach(task -> {
            Map map = new HashMap();
            map.put("id", task.getId());
            map.put("name", task.getName());
            map.put("assignee", task.getAssignee());
            map.put("delegationState", task.getDelegationState());
            map.put("createTime", task.getCreateTime());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("processInstanceId", task.getProcessInstanceId());
            resultList.add(map);
        });
        return ResponseEntity.ok(resultList);
    }

    /**
     * @data: 2022/4/19-下午5:35
     * @User: zhaozhiwei
     * @method: queryActiveHighLighted
     * @param bpmnType :
     * @param businessKey : 查询指定业务数据的流程情况
     * @return: void
     * @Description: 描述
     *
     * http://127.0.0.1:8080/leave/queryActiveHighLighted?bpmnType=process&businessKey=1
     */
    @RequestMapping("queryActiveHighLighted")
    public void queryActiveHighLighted(String bpmnType, String businessKey) throws Exception {
        activityService.queryActiveHighLighted(bpmnType, businessKey);
    }
}
