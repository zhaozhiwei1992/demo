package com.example.springbootjavafx;

import com.example.springbootjavafx.view.HomeView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Title: Main
 * @Package com/example/springbootjavafx/Main.java
 * @Description: 服务启动入口, 默认进入首页
 * @author zhaozhiwei
 * @date 2022/9/15 下午4:30
 * @version V1.0
 */
@SpringBootApplication
public class Main extends AbstractJavaFxApplicationSupport{
    public static void main(String[] args) {
        launch(Main.class, HomeView.class, args);
    }
}
