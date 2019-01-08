package com.cloud.controller;

import com.cloud.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Step3Test extends UserControllerTest {
    @Before
    public void fillWith2000() throws Exception {
        mockMvc.perform(delete("/user/"))
                .andDo(print())
                .andExpect(status().isOk());

        putUsersFromJSON("json/2000users.json");
    }

    @After
    public void Empty() throws Exception {
        mockMvc.perform(delete("/user/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void searchByAge() throws Exception {
        //Get default page for age >= 12
        String url = "/user/age?gt=12";
        List<User> users = getUserList(url);
        Assert.assertEquals(100, users.size());

        //Get page 0 for age >= 12, should be the same as default
        url = "/user/age?gt=12&page=0";
        List<User> users0 = getUserList(url);
        Assert.assertEquals(100, users0.size());
        Assert.assertArrayEquals(users.toArray(), users0.toArray());

        //Get page 1 for age >= 12, should be different than default
        url = "/user/age?gt=12&page=1";
        List<User> users1 = getUserList(url);
        Assert.assertEquals(100, users1.size());
        Assert.assertNotEquals(users.toArray(), users1.toArray());

        //Get default page for age >= 73, should have 53 users
        url = "/user/age?gt=73";
        users = getUserList(url);
        Assert.assertEquals(53, users.size());

        //Get default page for age >= -2, should have BadRequest error
        url = "/user/age?gt=-2";
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isBadRequest());

        //Get default page for age = 73, should have 53 users
        url = "/user/age?eq=73";
        users = getUserList(url);
        Assert.assertEquals(41, users.size());

        //Get default page for age = 19, should have BadRequestError
        url = "/user/age?eq=-1";
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchByText() throws Exception {
        //Get default page for name = john, should find 2 users
        String url = "/user/search?name=john";
        List<User> users = getUserList(url);
        Assert.assertEquals(2, users.size());

        //Get default page for name = john, page 2, should find 0 users
        url = "/user/search?name=john&page=2";
        users = getUserList(url);
        Assert.assertEquals(0, users.size());

        //Get default page for name = , should be a bad request
        url = "/user/search?name=";
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchByPosition() throws Exception {
        //Get default page for pos = {0,0}, should find 10 users
        String url = "/user/nearest?lat=0&lon=0";
        List<User> users_0_0 = getUserList(url);
        Assert.assertEquals(10, users_0_0.size());

        //Get default page for pos = {90,90}, should find 2 users
        url = "/user/nearest?lat=90&lon=90";
        List<User> users_90_90 = getUserList(url);
        Assert.assertEquals(10, users_90_90.size());

        //Nearest users to {0,0} and {90,90} shouldn't be the same
        Assert.assertNotEquals(users_0_0.toArray(), users_90_90.toArray());
    }
}
