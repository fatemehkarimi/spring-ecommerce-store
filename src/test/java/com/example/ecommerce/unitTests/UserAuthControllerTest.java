package com.example.ecommerce.unitTests;

import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.web.UserAuthController;
import com.example.ecommerce.web.dto.UserSignupDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({UserAuthController.class})
public class UserAuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();


    @MockBean
    UserService userService;

    User user1 = new User(
            UUID.fromString("aae82711-1546-4118-aaa0-de58f2d3c18b"),
            "fatemeh",
            "karimi",
            "09123456789",
            "fatemehkarimi@gmail.com",
            "$2a$10$EdJnVTWFWG4j9VorOuO7W.7fkn2AB/P.Od4TUbFO4x.vqraR80Tq2");

    UserSignupDto user1Dto = new UserSignupDto(
            "fatemeh",
            "karimi",
            "fatemehkarimi@gmail.com",
            "admin",
            "09123456789"
    );

    @Test
    public void successUserLogin() throws Exception {
        Mockito.when(userService.loadUserByUsername("fatemehkarimi@gmail.com")).thenReturn(
                new org.springframework.security.core.userdetails.User(
                        user1.getEmail(), user1.getPassword(), new HashSet<GrantedAuthority>() )
        );
        Object loginData = new Object() {
            public final String email = "fatemehkarimi@gmail.com";
            public final String password = "admin";
        };

        String json = objectMapper.writeValueAsString(loginData);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/login")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("user is now logged in"));
    }

    @Test
    public void failedUserLoginWithWrongPassword() throws Exception {
        Mockito.when(userService.loadUserByUsername("fatemehkarimi@gmail.com")).thenReturn(
                new org.springframework.security.core.userdetails.User(
                        user1.getEmail(), user1.getPassword(), new HashSet<GrantedAuthority>() )
        );
        Object loginData = new Object() {
            public final String email = "fatemehkarimi@gmail.com";
            public final String password = "test123";
        };

        String json = objectMapper.writeValueAsString(loginData);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Bad credentials"));
    }

    @Test
    public void failedUserLoginWithNonSignedUpEmail() throws Exception {
        Mockito.when(userService.loadUserByUsername(
                Mockito.anyString())).thenThrow(UsernameNotFoundException.class);

        Object loginData = new Object() {
            public final String email = "anything@yahoo.com";
            public final String password = "test123";
        };

        String json = objectMapper.writeValueAsString(loginData);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Bad credentials"));
    }

    @Test
    public void successSignup() throws Exception {
        Mockito.when(userService.save(user1Dto)).thenReturn(user1);

        Object signupData = new Object() {
            public String firstName = user1Dto.getFirstName();
            public String lastName = user1Dto.getLastName();
            public String email = user1Dto.getEmail();
            public String password = user1Dto.getPassword();
            public String phoneNumber = user1Dto.getPhoneNumber();
        };

        String json = objectMapper.writeValueAsString(signupData);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/signup")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void failSignupAlreadyExistUsername() throws Exception {
        Mockito.when(userService.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);
        Object signupData = new Object() {
            public String firstName = user1Dto.getFirstName();
            public String lastName = user1Dto.getLastName();
            public String email = user1Dto.getEmail();
            public String password = user1Dto.getPassword();
            public String phoneNumber = user1Dto.getPhoneNumber();
        };

        String json = objectMapper.writeValueAsString(signupData);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/signup")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string("email already exists"));
    }
}
