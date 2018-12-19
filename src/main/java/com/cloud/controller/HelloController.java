package com.cloud.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.lang.String.format;

@RestController
@RequestMapping("/")
public class HelloController {
    @RequestMapping("/hello")
    public String home() {
        return "Hello World!";
    }
    @RequestMapping("/env")
    public String env() {
        Map<String, String> env = System.getenv();
        StringBuilder builder = new StringBuilder();
        for (String envName : env.keySet()) {
            builder.append( format("%s=%s%n",
                    envName,
                    env.get(envName)));
        }
        return builder.toString();
    }
}
