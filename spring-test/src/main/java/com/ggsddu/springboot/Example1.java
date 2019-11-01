package com.ggsddu.springboot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Example1 {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
