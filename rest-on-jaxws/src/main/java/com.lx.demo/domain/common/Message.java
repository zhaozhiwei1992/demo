package com.lx.demo.domain.common;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
public class Message {

    public Message() {
        super();
    }

    public Message(String content) {
        super();
        this.content = content;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

