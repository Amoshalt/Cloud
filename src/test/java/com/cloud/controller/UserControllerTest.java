package com.cloud.controller;

import com.cloud.CloudApplicationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends CloudApplicationTests {

    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get("/user/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf("[" + user1 + "," + user2 + "]")));
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
    public void deleteUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + user1.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/" + user2.getId())).andDo(print()).andExpect(MockMvcResultMatchers.content().string(user2.toString()));
        Assert.assertEquals(String.valueOf("null"),
                mockMvc.perform(get("/user/" + user1.getId()))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());
    }
}