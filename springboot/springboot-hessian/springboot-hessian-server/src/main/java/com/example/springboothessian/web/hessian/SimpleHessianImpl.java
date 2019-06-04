package com.example.springboothessian.web.hessian;

import org.springframework.stereotype.Service;

@Service
public class SimpleHessianImpl implements SimpleHessian{
    public String sayHello(String name){
        return "hello: " + name;
    }
}
