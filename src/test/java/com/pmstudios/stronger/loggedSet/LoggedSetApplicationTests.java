package com.pmstudios.stronger.loggedSet;

// this is the new, jUnit 5
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
// jUnit 4 i believe?
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class LoggedSetApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
       assertNotNull(mockMvc);
    }

    @Test
    public void addLoggedSetTest__withoutPreviousSets() {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/logged-exericse/1");
    }

}
