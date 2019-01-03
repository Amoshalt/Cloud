package com.cloud.controller;   //Test

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HelloController {
    @RequestMapping("/hello")
    public String home() {
        return "Hello World!";
    }
}
