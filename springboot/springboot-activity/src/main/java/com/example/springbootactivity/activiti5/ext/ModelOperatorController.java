package com.example.springbootactivity.activiti5.ext;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootactivity.activiti5.service.ModelOperatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootactivity.activiti5.operate
 * @Description: 一些操作性的东西放这里，比如流程创建, 部署
 * 这些都是需要工作流组建原生提供的
 */
@Controller
public class ModelOperatorController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelOperatorService modelOperatorService;

    /**
     * 创建模型: 该方法可以独立提供，给业务扩展, 比如公司项目自己设计界面根据单据调用接口生成工作流配置
     * |
     * |
     * \/
     * 在模型基础上才可以画工作流
     */
    @RequestMapping("/create")
    @ResponseBody
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = "请假流程";
        String description = "这是一个请假流程";
        final Model modelData = modelOperatorService.create(name, description);
        if(!Objects.isNull(modelData)){
            response.sendRedirect("/editor?modelId=" + modelData.getId());
        }
    }

    /**
     * @Description: 工作流部署
     * curl http://127.0.0.1:8080/deployment?id=10001
     */
    @RequestMapping("deployment")
    @ResponseBody
    public JSONObject deployment(String id) throws Exception {
        return modelOperatorService.deployment(id);
    }

    /**
     * @data: 2022/4/19-下午3:54
     * @User: zhaozhiwei
     * @method: editor

     * @return: java.lang.String
     * @Description:
     * 查看流程图: http://127.0.0.1:8080/editor?modelId=10001
     */
    @GetMapping("editor")
    public String editor() {
        return "/modeler";
    }

    /**
     * @Description:
     * 导入工作流xml配置文件
     * 如果文件放到编译目录如one-task-process.bpmn20.xml会被自动引入
     */
    public String importBpmnXml(){
        return "";
    }

    /**
     * @Description: 导出工作流配置
     * ACT_RE_PROCDEF.id
     */
    public String exportBpmnXml(String processDefinitionId){
       return "";
    }
}

