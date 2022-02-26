package com.example.web.controller;

import com.example.domain.User;
import com.example.exception.AppException;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.web.controller
 * @Description: TODO
 * @date 2022/2/22 上午11:26
 */
@Controller
public class LoginController {

    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute(new User(1, "ttang"));
        return "registerForm";
    }

    /**
     * @data: 2022/2/25-下午5:08
     * @User: zhaozhiwei
     * @method: processRegistration
     * @param profilePicture 数组中包含了请求中对应part的数据（通过@RequestPart指定）。
     *                       如果用户提交表单的时候没有选择文件，那么这个数组会是空（而不是null）
      * @param user 如果是form表单提交，这里一定不能加RequestBody
     * @param errors 如果valid验证异常，spring会回写到errors中
     * @return: java.lang.String
     * @Description: 描述
     * 修改processRegistration()方法，使其能够接
     * 受上传的图片。其中一种方式是添加byte数组参数，并为其添
     * 加@RequestPart注解
     */
    @PostMapping("/register")
    public String processRegistration(
//            @RequestPart("profilePicture") byte[] profilePicture,
            @RequestPart("profilePicture") MultipartFile profilePicture,
//            @RequestPart("profilePicture") Part profilePicture,
            @Valid User user,
            RedirectAttributes model,
            Errors errors) throws IOException, AppException {
        if(errors.hasErrors()){
//            如果有异常会直接返回到当前页面, 并且填充错误信息
            System.out.println("报错了");
//            return "registerForm";
            throw new AppException("500001", "请求参数异常");
        }
        profilePicture.transferTo(new File("/tmp/transferto/" + profilePicture.getOriginalFilename()));
//        debug后可以观察上传过程中, 会在/tmp写入临时文件
//        这里配置 com.example.config.SpringWebAppInitializer.customizeRegistration
//        profilePicture.write("/tmp/transferto/" + profilePicture.getName());
//        userRepository.save(user);

//        保存成功后重定向
//        return "redirect:/user/"+user.getId();
//        该方式传参数更优雅， 并且不安全字符都会进行转义
        model.addAttribute("userid", user.getId());
//        可以通过flash的方式将整个对象传到下个重定向, 也可以采取将对象保存到session的方式
        model.addFlashAttribute(user);
        return "redirect:/user/{userid}";
    }

//    @Autowired
    private MultipartResolver multipartResolver;
}
