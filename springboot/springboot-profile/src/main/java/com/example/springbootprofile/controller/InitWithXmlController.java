package com.example.springbootprofile.controller;

import com.example.springbootprofile.domain.InitWithXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ImportResource(
       locations = {
               "/META-INF/spring/context.xml",
               "/META-INF/spring/context-prod.xml"
       }
)
public class InitWithXmlController {

    /**
     * @see Qualifier 精确指定某一个bean
     */
    @Autowired
    @Qualifier(value = "initWithXML-prod")
    private InitWithXML initWithXML;

    @GetMapping("/initxml")
    public InitWithXML initWithXML(){
        return initWithXML;
    }

}
