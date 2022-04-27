package com.example.springbootjpa.repository;
import com.example.springbootjpa.domain.Book;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.springbootjpa.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void init(){
        User user = new User();
        user.setId(5L);
        user.setName("zhangsan");
        user.setPassword("110");
        user.setAge(10);
        final Book chinese = new Book(7L, "chinese", 100f);
        bookRepository.save(chinese);
        user.setBooks(Arrays.asList(chinese));
        userRepository.save(user);

        User user2 = new User();
        user2.setId(6L);
        user2.setName("lisi");
        user2.setPassword("110");
        user2.setAge(10);
        user2.setBooks(Arrays.asList(chinese));
        userRepository.save(user2);
        log.info("测试前执行初始化------");
    }

    @Test
    public void testFindUserBook(){
        final Book chinese = new Book(7L, "chinese", 100f);
        bookRepository.save(chinese);

        final Page<UserBookDTO> users = userRepository.findByUser(new PageRequest(0, 10, new Sort(Sort.Direction.DESC,"name")));
//        users.get().collect(Collectors.toList()).forEach(userBookDTO -> {
//            log.info("组合查询信息: {}", userBookDTO.getUserName());
//        });
        users.forEach(userBookDTO -> {
            log.info("组合查询信息: {}", userBookDTO.getUserName());
        });

        bookRepository.delete(chinese);
    }

    @Test
    public void testFindByNamePageable(){
        final Page<User> users = userRepository.findByName("zhangsan", new PageRequest(0, 1, new Sort(Sort.Direction.ASC, "id")));
        users.forEach(user1 -> {
            log.info("分页用户, {}", user1);
        });
    }

    @Test
    public void testFindByIdOrName(){
        final Optional<User> user2 = userRepository.findByIdOrName(1L, "zhangsan");
//        log.info("用户, {}", user2.get());
        Assert.assertEquals(user2.get().getName(), "zhangsan");
    }

    @Test
    public void testFindByName(){
        String name = "zhangsan";
        Assert.assertEquals(name, userRepository.findByName(name).get().getName());
    }

    @Test
    @Transactional
    public void testDefaultImpl(){
        log.info("所有用户信息, {}", userRepository.findAll());
        log.info("删除前用户总数: {}", userRepository.count());
        User user = new User();
        user.setId(5L);
        user.setName("zhangsan");
        user.setPassword("110");
        user.setAge(10);
        final Book chinese = new Book(7L, "chinese", 100f);
        user.setBooks(Arrays.asList(chinese));
        userRepository.delete(user);
        log.info("删除后用户总数: {}", userRepository.count());
    }

    /**
     * @Description: sql注入测试
     */
    @Test
    public void testSelectByName(){
        final List<User> zhangsan = userRepository.selectByName("zhangsan");
//        log.info("用户信息: {}", zhangsan);
        Assert.assertEquals(1, zhangsan.size());
//        这种形式直接查不到东西,jpa本生已经处理
        final List<User> users = userRepository.selectByName("zhangsan or name is not null");
//        log.info("用户信息: {}", users);
        Assert.assertEquals(0, users.size());
    }


    @Test
    public void testFindBySql(){
        final List<User> users = userRepository.findBySql();
//        log.info("用户信息: {}", users);
        Assert.assertEquals(2, users.size());
    }

    @After
    public void destroy(){
        userRepository.deleteAll();
        log.info("测试后销毁成功------");
    }
}
