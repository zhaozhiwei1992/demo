package com.lx.demo.springbootcache.controller;

import com.lx.demo.springbootcache.domain.Person;
import com.lx.demo.springbootcache.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonRepository personRepository;

    /**
     * @data: 2022/10/25-上午10:19
     * @User: zhaozhiwei
     * @method: findByID
      * @param id :
     * @return: com.lx.demo.springbootcache.domain.Person
     * @Description: 获取十次， 只有第一次是读库，后续都是取缓存
     * seq 10 |xargs -i curl -XGET 'http://localhost:8080/persons/2'
     */
    @GetMapping("/persons/{id}")
    public Person findByID(@PathVariable Long id){
        return personRepository.findByID(id);
    }

    /**
     * @data: 2022/10/25-上午10:25
     * @User: zhaozhiwei
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
     * @data: 2022/10/25-上午10:14
     * @User: zhaozhiwei
     * @method: save
      * @param person :
     * @return: com.lx.demo.springbootcache.domain.Person
     * @Description: 描述
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
     * @data: 2022/10/25-上午10:21
     * @User: zhaozhiwei
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
