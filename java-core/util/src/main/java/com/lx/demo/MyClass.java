package com.lx.demo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "myList")
@XmlAccessorType(XmlAccessType.FIELD)
public class MyClass {
    @XmlTransient
    private List<LinkedHashMap<String, String>> myList;
    @XmlElement(name = "map")
    public List<LinkedHashMap<String, String>> getMyList() {
        return myList;
    }
    public void setMyList(List<LinkedHashMap<String, String>> myList) {
        this.myList = myList;
    }
}