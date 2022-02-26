package com.example.web.action;

import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.core.collection.ParameterMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginAction extends AbstractAction {

    protected Event doExecute(RequestContext context) throws Exception {
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getNativeRequest();
        String userName = request.getParameter("userName");
        //设置值到当前流程（只在当前流程可见）
        context.getFlowScope().put("userName", userName);
        //设置值到顶层流程，所有子流程共享数据
        context.getConversationScope().put("userName", userName);
//        //设置到当前request中
//        context.getRequestScope().put("userName",userName);
//        //设置到当前流程（视图解析后，清除）
//        context.getFlashScope().put("userName",userName);
//        //设置到当前视图中，只在当前视图状态可见
//        context.getViewScope().put("userName",userName);
        String password = request.getParameter("password");
        String eventId = request.getParameter("_eventId");
        String test = request.getParameter("test");
        if (null != userName && userName.equals("shuyuq")) {
            return this.success();
        }
        return this.error();
    }
}
