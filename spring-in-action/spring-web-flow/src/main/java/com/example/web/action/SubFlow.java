package com.example.web.action;

import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

@Component
public class SubFlow extends AbstractAction {
    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        if(null != context.getConversationScope().get("userName")){
            System.out.println("111111111111111111");
        }
        return success();
    }
}
