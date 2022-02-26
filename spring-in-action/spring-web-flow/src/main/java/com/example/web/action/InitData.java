package com.example.web.action;

import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

@Component
public class InitData extends AbstractAction {
    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        System.out.println("--------------init data--------------------");
        return success();
    }
}
