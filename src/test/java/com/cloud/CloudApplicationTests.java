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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class CloudApplicationTests{

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MockMvc mockMvc;

    protected User user1;
    protected User user2;
    protected User user3;
    protected User user4;



    @Before
    public void setUp() {

        user1= new User("Alice","Stop", new Position(0,0), "20/06/1996");
        user2= new User("Bob","Crypto", new Position(1.0,2.1), "21/06/1996");
        user3= new User("Carl","Immediately", new Position(1.23,3.56), "22/06/1996");
        user4= new User("John","Doe", new Position(4.56,9.87), "12/12/1999");

        this.userRepository.save(user1);
        this.userRepository.save(user2);
    }

    @After
    public void end() {
        userRepository.deleteAll();
    }

}
