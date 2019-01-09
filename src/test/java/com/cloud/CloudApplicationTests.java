package com.cloud;

import com.cloud.models.Position;
import com.cloud.models.User;
import com.cloud.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
public abstract class CloudApplicationTests{

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MockMvc mockMvc;

    static protected User user1 = new User("Alice","Stop", new Position(0,0), "06/20/1996"),
            user2 = new User("Bob","Crypto", new Position(1.0,2.1), "06/21/1996"),
            user3 = new User("Carl","Immediately", new Position(1.23,3.56), "06/22/1996"),
            user4 = new User("John","Doe", new Position(4.56,9.87), "12/12/1999");



    @Before
    public void setUp() {
        for(User u : Arrays.asList(user1, user2, user3, user4)) {
            userRepository.save(u);
        }
    }

    @After
    public void end() {
        userRepository.deleteAll();
    }

}
