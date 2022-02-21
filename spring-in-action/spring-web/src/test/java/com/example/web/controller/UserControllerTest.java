package com.example.web.controller;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.web.controller
 * @Description: TODO
 * @date 2022/2/21 上午11:21
 */
public class UserControllerTest{

    @Test
    public void testUsers() throws Exception {

//        1. 创建集合
        List<User> users = createUsers(20);
//        2. 创建mockRepository
        final UserRepository userRepository = mock(UserRepository.class);
//        不通过数据库自己构建返回结果
        when(userRepository.findUsers(1, 20)).thenReturn(users);

//        2. mock springmvc  给controller塞数据
        final UserController userController = new UserController();
        userController.setUserRepository(userRepository);
        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setSingleView(new InternalResourceView("/WEB-INF/jsp/user.jsp"))
                .build();

//        3. 请求返回结果是否一致
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(view().name("users"))
                .andExpect(model().attributeExists("userList"))
                .andExpect(model().attribute("userList", hasItems(users.toArray())));

    }

    private List<User> createUsers(int count) {

        final List<User> collect = Stream.iterate(BigInteger.ZERO, bigInteger -> bigInteger.add(BigInteger.ONE))
                .limit(count)
                .map(bigInteger -> new User(Integer.parseInt(bigInteger.toString()), new Date()))
                .collect(Collectors.toList());
        return collect;
    }

}