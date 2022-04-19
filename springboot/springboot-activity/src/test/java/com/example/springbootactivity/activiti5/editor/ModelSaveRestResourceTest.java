package com.example.springbootactivity.activiti5.editor;

import com.example.springbootactivity.SpringbootActivityApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import junit.framework.TestCase;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.springbootactivity.activiti.editor
 * @Description: 生成一个model
 * @date 2022/4/18 下午9:29
 */
@RunWith(SpringRunner.class)
@Import(SpringbootActivityApplication.class)
public class ModelSaveRestResourceTest extends TestCase {

    private static final Logger logger = LoggerFactory.getLogger(ModelSaveRestResourceTest.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSaveModel() throws UnsupportedEncodingException {
        Model model = repositoryService.newModel();
        //设置默认流程名称
        String name = "TEST";
        String description = "";
        int revision = 1;
        //设置key
        String key = "TEST-PROCESS";
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);
        String id = model.getId();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
        //得到modelid
        logger.info("model id: {}", id);
    }
}