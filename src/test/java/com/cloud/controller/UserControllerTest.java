package com.cloud.controller;

import com.cloud.CloudApplicationTests;
import com.cloud.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends CloudApplicationTests {

    /**
     * Get the regex to identify a user. Works better than a classical string if the id is null
     * @param u is the user to identify
     * @return regex matching the user JSON representation
     */
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

    /**
     * Get a JSON representation of an user. Works well if id is set
     * @param u is the user to convert
     * @return a JSON representation of the user (without the id if null)
     */
    private String getUserJSON(User u) {
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
     * Put 2000 users in database from /json/users.json
     * @throws Exception if error happen
     */
    private void putUsersFromJSON() throws Exception {
        File file = new ClassPathResource("json/users.json").getFile();
        BufferedReader br = new BufferedReader(new FileReader(file));
        mockMvc.perform(put("/user/").contentType("application/json").content(br.readLine()))
                .andExpect(status().isCreated());
    }

    /**
     * Test GET /user
     * @throws Exception if error happen
     */
    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get("/user/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf("[" + user1 + "," + user2 + "]")));
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

    /**
     * GET a page of user and convert it into a sorted array
     * @param num index of the wanted page (negative index will return default page)
     * @return Array of users sorted by index
     * @throws Exception if error happen
     */
    private List<User> getPage(int num) throws Exception {
        String url = "/user";
        if(num >= 0) {
            url += "?page=" + num;
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
        String content = mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<User> users = mapper.readValue(content, typeReference);
        users.sort(Comparator.comparing(User::getId));
        return users;
    }

    /**
     * Step 2 : pagination
     * Test GET /USER and GET /user?page=xxx
     * @throws Exception if error happen
     */
    @Test
    public void pagination() throws Exception {
        deleteUser();
        putUsersFromJSON();

        //Default page
        List<User> users = getPage(-1);
        Assert.assertEquals(users.size(), 100);

        //First page (index 0)
        //Should be the same as default
        List<User> users0 = getPage(0);
        Assert.assertEquals(users0.size(), 100);
        Assert.assertArrayEquals(users0.toArray(), users.toArray());

        //Page with index 2, should be different than others
        List<User> users2 = getPage(2);
        Assert.assertEquals(users2.size(), 100);
        //Arrays are sorted => check that users aren't just rearranged
        Assert.assertNotEquals(users.toArray(), users2.toArray());

        //Page with high index should be empty
        List<User> users3 = getPage(2000);
        Assert.assertEquals(users3.size(), 0);
    }
}