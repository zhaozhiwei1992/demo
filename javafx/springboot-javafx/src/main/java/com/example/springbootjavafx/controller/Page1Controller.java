package com.example.springbootjavafx.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.Event;
import javafx.fxml.FXML;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootjavafx.controller
 * @Description: TODO
 * @date 2022/9/15 下午5:05
 */
@FXMLController
public class Page1Controller {

    @FXML
    public void reactOnClick(final Event e) {
        System.out.println("Clicked a button");
        System.out.println(e);
    }
}
