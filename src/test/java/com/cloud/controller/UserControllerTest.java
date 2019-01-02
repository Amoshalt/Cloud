package com.cloud.controller;

import com.cloud.CloudApplicationTests;
import com.cloud.models.User;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends CloudApplicationTests {

    private String getUserRegex(User u) {
        StringBuilder builder = new StringBuilder();
        builder.append("\\{\"id\":\"");
        if(u.getId() != null && !u.getId().equals("")) {
            builder.append(u.getId());
        }
        else {
            builder.append("[a-z0-9]{24}");
        }
        builder.append("\",\"firstName\":\"")
                .append(u.getFirstName())
                .append("\",\"lastName\":\"")
                .append(u.getLastName())
                .append("\",\"position\":\\{\"lat\":")
                .append(u.getPosition().getLat())
                .append(",\"lon\":")
                .append(u.getPosition().getLon())
                .append("\\},\"birthDay\":\"")
                .append(u.getBirthDay())
                .append("\"}");
        return builder.toString();
    }

    private String getUserJSON(User u) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        if(u.getId() != null && u.getId() != "") {
            builder.append("\"id\":\"")
                    .append(u.getId())
                    .append("\",");
        }
        builder.append("\"firstName\":\"")
                .append(u.getFirstName())
                .append("\",\"lastName\":\"")
                .append(u.getLastName())
                .append("\",\"position\":")
                .append(u.getPosition())
                .append(",\"birthDay\":\"")
                .append(u.getBirthDay())
                .append("\"}");
        return builder.toString();
    }

    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get("/user/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf("[" + user1 + "," + user2 + "]")));
    }

    @Test
    public void putUsers() throws Exception {
        Pattern pattern = Pattern.compile("^\\["+
                getUserRegex(user3)+
                ","+
                getUserRegex(user4)+
                "\\]$");
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

    @Test
    public void deleteUser() throws Exception {
        //Delete user1
        mockMvc.perform(delete("/user/" + user1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        //Check that user1 is in DB anymore
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