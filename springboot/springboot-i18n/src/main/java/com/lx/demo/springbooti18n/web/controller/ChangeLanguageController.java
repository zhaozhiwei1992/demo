package com.lx.demo.springbooti18n.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.stream.Stream;

@Controller
public class ChangeLanguageController {
    private static final Logger logger = LoggerFactory.getLogger(ChangeLanguageController.class);

    @GetMapping("/changeSessionLanauage")
    public String changeLanguage(@RequestParam String lang){
        logger.info("切换为: {}", lang);
        if("zh".equals(lang)){
            return "redirect:/?lang=zh_CN";
        }else {
            return "redirect:/?lang=en_US";
        }
    }

    @PostMapping("/changeSessionLanauage")
    public String changeLanguagePost(String lang, HttpServletRequest request){
        logger.info("切换为: {}", lang);
        if("zh".equals(lang)){
            //代码中即可通过以下方法进行语言设置
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("zh", "CN"));
        }else if("en".equals(lang)){
            //代码中即可通过以下方法进行语言设置
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en", "US"));
        }
        return "redirect:/";
    }
}
