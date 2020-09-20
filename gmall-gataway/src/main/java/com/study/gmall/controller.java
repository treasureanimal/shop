package com.study.gmall;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

    @Value("${parent}")
    private String name;

    @RequestMapping("/test")
    public String test(){
        System.out.println("name = " + name);
        return name;
    }
}
