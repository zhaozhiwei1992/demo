package com.lx.demo.springbootmongodb.repository;

import com.lx.demo.springbootmongodb.domain.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    int deleteByPrimaryKey(Integer id) {
        final Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, User.class);
        return id;
    }

    User insert(User record) {
        return mongoTemplate.save(record);
    }

    /**
     * 根据名字查找用户
     * @param name
     * @return
     */
    List<User> selectByName(String name) {
        final Criteria criteria = Criteria.where("name").is(name);
        final Query query = new Query(criteria);
        return mongoTemplate.find(query, User.class);
    }

    User selectByPrimaryKey(Integer id) {
        return mongoTemplate.findById(id, User.class);
    }

    long updateByPrimaryKey(User record) {
        final Query id = new Query(Criteria.where("id").is(record.getId()));
        final Update update = new Update().set("name", record.getName()).set("age", record.getAge());
        final UpdateResult updateResult = mongoTemplate.updateFirst(id, update, User.class);
        return updateResult.getMatchedCount();
    }

}
