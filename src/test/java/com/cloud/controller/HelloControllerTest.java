package com.cloud.controller;

import com.cloud.CloudApplicationTests;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



public class HelloControllerTest extends CloudApplicationTests {

    @Test
    public void HelloWorldControllerTest () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home/hello").accept(MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World!"));
    }

    @Configuration
    public static class TestConfiguration {

        @Bean
        public HelloController helloController() {
            return new HelloController();
        }

    }

}