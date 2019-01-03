package com.cloud.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class UserControllerTest {
    
    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

//    @Test
//    public void getUsers() {
//    }
//
//    @Test
//    public void putUsers() {
//
//    }
//
//    @Test
//    public void deleteUsers() {
//    }
//
//    @Test
//    public void getUser() {
//    }
//
//    @Test
//    public void postUser() {
//    }
//
//    @Test
//    public void putUser() {
//    }

    @Test
    public void deleteUser() throws Exception {
        UUID id = UUID.fromString("");
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Configuration
    public static class TestConfiguration {

        @Bean
        public UserController userController() {
            return new UserController();
        }

    }
}