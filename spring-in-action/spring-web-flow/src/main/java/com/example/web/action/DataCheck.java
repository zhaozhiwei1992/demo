package com.example.web.action;

import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

@Component
public class DataCheck extends AbstractAction {
    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        String userName = (String) context.getFlowScope().get("userName");
        System.out.println(userName);
        return success();
    }
}
