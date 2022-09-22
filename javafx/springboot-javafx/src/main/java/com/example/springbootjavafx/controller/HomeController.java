package com.example.springbootjavafx.controller;

import com.example.springbootjavafx.view.Page1View;
import com.example.springbootjavafx.view.Page2View;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootjavafx.controller
 * @Description: 首页控制器, view中的一些操作都从这里作为入口
 * @date 2022/9/15 下午4:57
 */
@FXMLController
public class HomeController {

    @FXML
    Pane myDynamicPane;

    @Autowired
    Page1View page1View;

    @Autowired
    Page2View page2View;

    public void showPage1View(final Event e) {
        myDynamicPane.getChildren().clear();
        myDynamicPane.getChildren().add(page1View.getView());
    }

    public void showPage2View(final Event e) {
        myDynamicPane.getChildren().clear();
        myDynamicPane.getChildren().add(page2View.getView());
    }

}
