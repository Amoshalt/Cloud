package com.cloud.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

public class UserControllerTest {
    
    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void getUsers() {
    }

    @Test
    public void putUsers() {
    }

    @Test
    public void deleteUsers() {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void postUser() {
    }

    @Test
    public void putUser() {
    }

    @Test
    public void deleteUser() {
    }

    @Configuration
    public static class TestConfiguration {

        @Bean
        public UserController helloController() {
            return new UserController();
        }

    }
}