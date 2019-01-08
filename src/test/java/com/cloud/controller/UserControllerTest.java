package com.cloud.controller;

import com.cloud.CloudApplicationTests;
import com.cloud.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Comparator;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class UserControllerTest extends CloudApplicationTests {

    /**
     * Get the regex to identify a user. Works better than a classical string if the id is null
     * @param u is the user to identify
     * @return regex matching the user JSON representation
     */
    String getUserRegex(User u) {
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

    /**
     * Get a JSON representation of an user. Works well if id is set
     * @param u is the user to convert
     * @return a JSON representation of the user (without the id if null)
     */
    String getUserJSON(User u) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        if(u.getId() != null && !u.getId().equals("")) {
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

    /**
     * Put users in database from json file
     * @throws Exception if error happen
     */
    void putUsersFromJSON(String path) throws Exception {
        File file = new ClassPathResource(path).getFile();
        BufferedReader br = new BufferedReader(new FileReader(file));
        mockMvc.perform(put("/user/").contentType("application/json").content(br.readLine()))
                .andExpect(status().isCreated());
    }

    /**
     * Performs a get request at the given URL and return the list of user obtained
     * @param url to target
     * @return content as User list
     * @throws Exception if error happen
     */
    List<User> getUserList(String url) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
        String content = mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return mapper.readValue(content, typeReference);
    }

    /**
     * GET a page of user and convert it into a sorted array
     * @param num index of the wanted page (negative index will return default page)
     * @return Array of users sorted by index
     * @throws Exception if error happen
     */
    List<User> getPage(int num) throws Exception {
        String url = "/users";
        if(num >= 0) {
            url += "?page=" + num;
        }
        System.out.println(url);
        List<User> users = getUserList(url);
        users.sort(Comparator.comparing(User::getId));
        return users;
    }
}