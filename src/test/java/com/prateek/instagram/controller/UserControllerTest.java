package com.prateek.instagram.controller;

import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.sampleData.TestUtils;
import com.prateek.instagram.sampleData.UserSampleData;
import com.prateek.instagram.service.UserService;
import com.prateek.instagram.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    // MOckMvc to test RestController. Provided by Spring. Test Servlet related calls.
    @Autowired
    MockMvc mockMvc;

    // @InjectMocks is used to mock the bean for which test is being created
    @InjectMocks
    UserController userController;

    //@Mock is used to declare/mock the references of the dependent beans
    @Mock
    UserService userService;

    //create an instance of mockmvc.
    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void createUserAndReturnSuccessResponse() throws Exception {

        UserDto userDto = UserSampleData.getNewUserDto();



        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                                            .content(TestUtils.convertToJsonString(userDto))
                                            .contentType(APPLICATION_JSON)
                                            .accept(APPLICATION_JSON))
                                            .andExpect(status().isOk())
                                            .andReturn();

    }

    @Test
    public void updateUserAndReturnSuccessResponse() throws Exception {
        UserDto userDto = UserSampleData.getNewUpdateUserDto();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user")
                                              .content(TestUtils.convertToJsonString(userDto))
                                              .contentType(APPLICATION_JSON)
                                              .accept(APPLICATION_JSON))
                                              .andExpect(status().isOk())
                                              .andReturn();
    }

    @Test
    public void getUserAndReturnSuccessResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/2")
                                            .accept(APPLICATION_JSON))
                                            .andExpect(status().isOk());
    }

    @Test
    public void getUserAndReturnFailResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/prateek")
                .accept(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }





}