package com.lx.demo;

import com.mongodb.*;
import org.junit.Test;

import java.util.Arrays;

/**
 * 纯mongo的javaAPI实现
 */
public class MongoTest {

    @Test
    public void testMongo(){
        Mongo mongo = new Mongo("127.0.0.1",27017);
        DB db = new DB(mongo,"user-demo");
        DBCollection collection =  db.getCollection("User");
        //类比法：JDBC,相对来说比较底层
        DBObject dbObject = new BasicDBObject();
        dbObject.put("name","zhangsan");
        dbObject.put("age",999);
        dbObject.put("addr","bj");
        collection.insert(dbObject);
        DBCursor cursor = collection.find();
        for (DBObject obj : cursor){
            System.out.println(obj);
        }
    }
}
