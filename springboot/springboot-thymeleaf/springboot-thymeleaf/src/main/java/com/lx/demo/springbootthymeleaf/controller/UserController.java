package com.lx.demo.springbootthymeleaf.controller;

import com.lx.demo.springbootthymeleaf.domain.User;
import com.lx.demo.springbootthymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 要使用thymeleaf必须引入 thymeleaf的包
 * application.properties中使用user前缀要小心，spring本身也有个user
 */
@Controller
@EnableConfigurationProperties(User.class)
@RequestMapping("/users")
public class UserController {

    private User user;
    public UserController(User user) {
        this.user = user;
    }

    /**
     * http://localhost:8080/users/
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(ModelMap model) {
        model.addAttribute("name", user.getName());
        model.addAttribute("msg", user.toString());
        model.addAttribute("msg2", "首页");
        return "index";
    }

    @Autowired
    private UserService userService;

    @GetMapping("/toUpdate")
    public String update(ModelMap modelMap, Long id){
        User user = userService.findById(id);
        modelMap.addAttribute("user", user);
        return "user/update";
    }

    @GetMapping("/list")
    public String findAll(ModelMap modelMap){
        modelMap.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @PostMapping("/update")
    public String update(User user){
        User user1 = userService.update(user);
        return "redirect:/users/list";
    }

    /**
     * 跳转到新增页面
     * @return
     */
    @GetMapping("/toAdd")
    public String toAdd(){
        return "user/add";
    }

    /**
     * return "user/userEdit"; 代表会直接去 resources 目录下找相关的文件。
     * return "redirect:/list"; 代表转发到对应的 Controller，这个示例就相当于删除内容之后自动调整到 list 请求，然后再输出到页面。
     *
     * 开始 controller 方法写的是   
     *
     *     @RequestMapping( value = "/add", method = RequestMethod.POST )
     *
     *     public String add( @RequestBody Map<String, Object> params ) {  
     *
     * 报错 Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported
     *
     * 改成
     *
     *     @RequestMapping( value = "/add", method = RequestMethod.POST )
     *
     *     public String add( @RequestParam Map<String, Object> params ) {
     *
     * 就不报错了。
     * @param user
     * @return
     */
    @PostMapping("/add")
    public String save(User user){
        userService.save(user);
        //转到/users controller请求
        return "redirect:/users/list";
    }

    @GetMapping("/delete")
    public String delete(Long id){
        userService.delete(id);
        return "redirect:/users/list";
    }
}
