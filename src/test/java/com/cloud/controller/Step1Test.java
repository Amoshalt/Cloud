package com.cloud.controller;

import com.cloud.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Step1Test extends UserControllerTest {

    @Before
    public void populate() throws Exception {
        mockMvc.perform(delete("/user"))
                .andDo(print())
                .andExpect(status().isOk());

        StringBuilder users = new StringBuilder("[");
        for(User u : Arrays.asList(user1, user2, user3, user4)) {
            users.append(this.getUserJSON(u)).append(",");
        }
        users.replace(users.length()-1, users.length(), "]");
        System.out.println(users.toString());
        mockMvc.perform(put("/user")
                .contentType("application/json")
                .content(users.toString()))
                .andExpect(status().isCreated());
    }

    @After
    public void depopulate() throws Exception {
        mockMvc.perform(delete("/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test GET /user
     * @throws Exception if error happen
     */
    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf("[" + user1 + "," + user2 + "," + user3 + "," + user4 + "]")));
    }

    /**
     * Test PUT /user
     * @throws Exception if error happen
     */
    @Test
    public void putUsers() throws Exception {
        Pattern pattern = Pattern.compile("^\\["+
                getUserRegex(user3)+
                ","+
                getUserRegex(user4)+
                "]$");
        Matcher matcher = pattern.matcher(mockMvc.perform(put("/user/").contentType("application/json").content("["
                + getUserJSON(user3)
                + ","
                + getUserJSON(user4)
                + "]"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString());
        Assert.assertTrue(matcher.matches());
    }

    /**
     * Test DELETE /user
     * @throws Exception if error happen
     */
    @Test
    public void deleteUsers() throws Exception {
        mockMvc.perform(delete("/user"))
                .andDo(print())
                .andExpect(status().isOk());

        Assert.assertEquals(String.valueOf(""),
                mockMvc.perform(get("/user" + user1.getId()))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());
        Assert.assertEquals(String.valueOf(""),
                mockMvc.perform(get("/user" + user1.getId()))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());
        Assert.assertEquals("[]",
                mockMvc.perform(get("/users"))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());
    }

    /**
     * Test GET /USER/{id}
     * @throws Exception if error happen
     */
    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get("/user/" + user1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(user1)));
    }

    /**
     * Test POST /user
     * @throws Exception if error happen
     */
    @Test
    public void postUser() throws Exception {
        mockMvc.perform(delete("/user"))
                .andExpect(status().isOk());
        Assert.assertEquals(String.valueOf(user1),
                mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(getUserJSON(user1)))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());
        //Check data change
        mockMvc.perform(get("/user/" + user1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(user1)));
    }

    /**
     * Test PUT /user/{id}
     * @throws Exception if error happen
     */
    @Test
    public void putUser() throws Exception {
        //Assert there is some data for user 2 id
        mockMvc.perform(put("/user/" + user2.getId())
                .contentType("application/json")
                .content(getUserJSON(user2)))
                .andDo(print())
                .andExpect(status().isOk());
        //Change data for user2 id
        user2.setFirstName("Jack");
        user2.setLastName("Jefferson");
        user2.setBirthDay("07/10/1924");
        mockMvc.perform(put("/user/" + user2.getId())
                .contentType("application/json")
                .content(getUserJSON(user2)))
                .andDo(print())
                .andExpect(status().isOk());
        //Check data change
        mockMvc.perform(get("/user/" + user2.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(user2)));
    }

    /**
     * Test DELETE /user/{id}
     * @throws Exception if error happen
     */
    @Test
    public void deleteUser() throws Exception {
        //Delete user1
        mockMvc.perform(delete("/user/" + user1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        //Check that user1 isn't in DB anymore
        mockMvc.perform(get("/user/" + user2.getId())).andDo(print()).andExpect(MockMvcResultMatchers.content().string(user2.toString()));
        Assert.assertEquals(String.valueOf(""),
                mockMvc.perform(get("/user/" + user1.getId()))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());

        //Check if user does not exist
        mockMvc.perform(delete("/user/fakeID"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}
