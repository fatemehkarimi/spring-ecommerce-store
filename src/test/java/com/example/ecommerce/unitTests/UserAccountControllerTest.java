package com.example.ecommerce.unitTests;


import com.example.ecommerce.dao.OrderDao;
import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.web.UserAccountController;
import com.example.ecommerce.web.dto.UserSignupDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest({UserAccountController.class})
public class UserAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    UserService userService;

    @MockBean
    UserDao userDao;

    @MockBean
    OrderDao orderDao;

    User user = new User(
            UUID.fromString("aae82711-1546-4118-aaa0-de58f2d3c18b"),
            "fatemeh",
            "karimi",
            "09123456789",
            "fatemehkarimi@gmail.com",
            "$2a$10$EdJnVTWFWG4j9VorOuO7W.7fkn2AB/P.Od4TUbFO4x.vqraR80Tq2");

    @Test
    @WithMockUser(username="fatemehkarimi@gmail.com", password="admin")
    public void getUserProfile() throws Exception {
        Mockito.when(userDao.findByEmail(user.getEmail())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/profile"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    @WithMockUser(username="fatemehkarimi@gmail.com", password="admin")
    public void updateUserProfile() throws Exception {
        Mockito.when(userDao.findByEmail(user.getEmail())).thenReturn(user);
        Mockito.when(userService.update(Mockito.any())).thenReturn(user);

        Object updateData = new Object() {
            public String phoneNumber = "091122334455";
        };

        String json = objectMapper.writeValueAsString(updateData);
        mockMvc.perform(MockMvcRequestBuilders
                       .post("/api/user/profile/edit")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
