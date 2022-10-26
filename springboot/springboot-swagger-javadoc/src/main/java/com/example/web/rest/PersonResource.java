package com.example.web.rest;

import com.example.domain.Person;
import com.example.repository.PersonRepository;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: PersonController
 * @Package: com/example/springbootcache/controller/PersonController.java
 * @Description: 用户信息接口
 * @author: zhaozhiwei
 * @date: 2022/10/25 下午8:23
 * @version: V1.0
 */
@RestController
public class PersonResource {

    private static final Logger log = LoggerFactory.getLogger(PersonResource.class);

    @Autowired
    private PersonRepository personRepository;

    /**
     * @date: 2022/10/25-上午10:19
     * @author: zhaozhiwei
     * @method: findByID
      * @param id : 唯一id
     * @return: com.lx.demo.springbootcache.domain.Person
     * @Description: 根据id获取用户信息
     * 获取十次， 只有第一次是读库，后续都是取缓存
     * 直接删掉redis缓存里的内容，仍然可以获取数据，并且走缓存，此时获取的是服务缓存ehcache中的信息
     * seq 10 |xargs -i curl -XGET 'http://localhost:8080/persons/2'
     */
    @ApiOperation(value = "根据id获取用户信息")
    @GetMapping("/persons/{id}")
    public Person findByID(@PathVariable Long id){
        return personRepository.findByID(id);
    }

    /**
     * @date: 2022/10/25-上午10:25
     * @author: zhaozhiwei
     * @method: deleteByID
      * @param id :
     * @return: com.lx.demo.springbootcache.domain.Person
     * @Description: 根据id删除person信息
     * curl -XDELETE 'http://localhost:8080/persons/1'
     */
    @DeleteMapping("/persons/{id}")
    public Person deleteByID(@PathVariable Long id){
        return personRepository.deleteByID(id);
    }

    /**
     * @date: 2022/10/25-上午10:14
     * @author: zhaozhiwei
     * @method: save
      * @param person :
     * @return: com.lx.demo.springbootcache.domain.Person
     * @Description: 保存方法
     *
      curl -XPOST 'http://localhost:8080/persons' -H 'Content-Type: application/json' -d '
{
"name":"zhangsan"
}'

     */
    @PostMapping("/persons")
    public Person save(@RequestBody Person person){
        return personRepository.save(person);
    }

    /**
     * @date: 2022/10/25-上午10:21
     * @author: zhaozhiwei
     * @method: update
      * @param person :
     * @return: com.lx.demo.springbootcache.domain.Person
     * @Description: 修改内容看是否会调整缓存

    curl -XPUT 'http://localhost:8080/persons' -H 'Content-Type: application/json' -d '
    {
    "id":1,
    "name":"zhangsan2"
    }'
     */
    @PutMapping("/persons")
    public Person update(@RequestBody Person person){
        return personRepository.update(person, person.getId());
    }
}
