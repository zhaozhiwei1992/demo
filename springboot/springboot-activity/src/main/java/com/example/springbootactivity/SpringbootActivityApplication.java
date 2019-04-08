package com.example.springbootactivity;

import com.example.springbootactivity.domain.Person;
import com.example.springbootactivity.repository.PersonRepository;
import com.example.springbootactivity.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 参考: http://www.cnblogs.com/momoweiduan/p/9454140.html
 * https://www.activiti.org/userguide/
 */
//@SpringBootApplication(exclude = {
//        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class
//        , org.activiti.spring.boot.SecurityAutoConfiguration.class})
@SpringBootApplication(exclude = {
        org.activiti.spring.boot.SecurityAutoConfiguration.class
})
public class SpringbootActivityApplication {

    @Autowired
    private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootActivityApplication.class, args);
    }

    /**
     * 初始化模拟数据
     *
     * @param myService
     * @return
     */
    @Bean
    public CommandLineRunner init(final ActivitiService myService) {
        return strings -> {
            if (personRepository.findAll().size() == 0) {
                personRepository.save(new Person("wtr"));
                personRepository.save(new Person("wyf"));
                personRepository.save(new Person("admin"));
            }
        };
    }
//    @Bean
//    public CommandLineRunner init(final RepositoryService repositoryService,
//                                  final RuntimeService runtimeService,
//                                  final TaskService taskService) {
//
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... strings) throws Exception {
//                System.out.println("Number of process definitions : "
//                        + repositoryService.createProcessDefinitionQuery().count());
//                System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
//                runtimeService.startProcessInstanceByKey("oneTaskProcess");
//                System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
//            }
//        };
//
//
//    }
}