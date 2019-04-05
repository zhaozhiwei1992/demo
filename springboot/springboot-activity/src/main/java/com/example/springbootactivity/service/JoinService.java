package com.example.springbootactivity.service;

import com.example.springbootactivity.domain.Comp;
import com.example.springbootactivity.domain.Person;
import com.example.springbootactivity.repository.CompRepository;
import com.example.springbootactivity.repository.PersonRepository;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class JoinService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    private CompRepository compRepository;

    /**
     *
     * 加入公司操作，可从DelegateExecution获取流程中的变量
     * @param execution
     */
    public void joinGroup(DelegateExecution execution) {
        Boolean bool = (Boolean) execution.getVariable("joinApproved");
        if (bool) {
            Long personId = (Long) execution.getVariable("personId");
            Long compId = (Long) execution.getVariable("compId");
            Comp comp = compRepository.findOne(compId);
            Person person = personRepository.findOne(personId);
            person.setComp(comp);
            personRepository.save(person);
            System.out.println("加入组织成功");
        } else {
            System.out.println("加入组织失败");
        }
    }

    /**
     *
     * 获取符合条件的审批人，演示这里写死，使用应用使用实际代码
     * @param execution
     * @return
     */
    public List<String> findUsers(DelegateExecution execution) {
        return Arrays.asList("admin", "wtr");
    }
}
