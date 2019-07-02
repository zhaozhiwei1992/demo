package com.lx.demo.domain;

import java.io.Serializable;


/**
 * 序列化对象
 *
 * 如果serialVersionUID不同时
 * java.io.InvalidClassException: com.lx.demo.domain.User; local class incompatible: stream classdesc serialVersionUID = -5388016759828919799, local class serialVersionUID = 2083262352962630020
 * 	at java.io.ObjectStreamClass.initNonProxy(ObjectStreamClass.java:687)
 * 	at java.io.ObjectInputStream.readNonProxyDesc(ObjectInputStream.java:1883)
 * 	at java.io.ObjectInputStream.readClassDesc(ObjectInputStream.java:1749)
 * 	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2040)
 * 	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1571)
 * 	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:431)
 * 	at com.lx.demo.javacore.SerializeDemo.deSerialize(SerializeDemo.java:57)
 * 	at com.lx.demo.javacore.SerializeDemo.main(SerializeDemo.java:22)
 */
public class User implements Serializable {

    private static final long serialVersionUID = 2083262352962630020L;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
