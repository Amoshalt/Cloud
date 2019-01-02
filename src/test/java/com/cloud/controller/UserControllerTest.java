package com.cloud.controller;

import com.cloud.CloudApplicationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    public void putUsers() throws Exception {
//        mockMvc.perform(put("/user/"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string(String.valueOf("[" + user1 + "," + user2 + "]")));
    }

    @Test
    public void deleteUsers() throws Exception {
        mockMvc.perform(delete("/user/"))
                .andDo(print())
                .andExpect(status().isOk());

        Assert.assertEquals(String.valueOf(""),
                mockMvc.perform(get("/user/" + user1.getId()))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());
        Assert.assertEquals(String.valueOf(""),
                mockMvc.perform(get("/user/" + user1.getId()))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());
        Assert.assertEquals("[]",
                mockMvc.perform(get("/user/"))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());
    }

    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get("/user/" + user1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(user1)));
    }

    @Test
    public void postUser() {
    }

    @Test
    public void putUser() {
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/user/" + user1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/user/" + user2.getId())).andDo(print()).andExpect(MockMvcResultMatchers.content().string(user2.toString()));
        Assert.assertEquals(String.valueOf(""),
                mockMvc.perform(get("/user/" + user1.getId()))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());

        mockMvc.perform(delete("/user/fakeID"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}