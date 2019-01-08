package com.cloud.controller;

import com.cloud.models.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Step2Test extends UserControllerTest {

    /**
     * Step 2 : pagination
     * Test GET /USER and GET /user?page=xxx
     * @throws Exception if error happen
     */
    @Test
    public void pagination() throws Exception {
        mockMvc.perform(delete("/user/"))
                .andDo(print())
                .andExpect(status().isOk());
        putUsersFromJSON("json/2000users.json");

        //Default page
        List<User> users = getPage(-1);
        Assert.assertEquals(100, users.size());

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
        Assert.assertEquals(0, users3.size());
    }
}
