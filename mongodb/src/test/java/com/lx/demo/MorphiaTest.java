package com.lx.demo;

import com.lx.demo.domain.User;
import com.mongodb.MongoClient;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;

import java.util.Arrays;

/**
 * Morphia 相当于mongo中的一个orm框架
 */
public class MorphiaTest {

    @Test
    public void testMongodb(){
        Morphia morphia = new Morphia();
        Datastore ds = morphia.createDatastore(new MongoClient("localhost",27017),"user-demo");

        User user = new User();
        user.setAge("18");
        user.setAddress("bj");
        user.setName("zhangsan");
        user.setPassword("12346");

        //db.User.find() 可以在mongodb中查看数据
        Key<User> key = ds.save(user);
        System.out.println(key.getId());
    }

}
