package com.example.ecommerce.unitTests;

import com.example.ecommerce.dao.AddressDao;
import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.AddressService;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.web.UserAddressController;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest({UserAddressController.class})
public class UserAddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    AddressService addressService;

    @MockBean
    UserDao userDao;

    @MockBean
    UserService userService;

    @MockBean
    AddressDao addressDao;

    User user = new User(
            UUID.fromString("aae82711-1546-4118-aaa0-de58f2d3c18b"),
            "fatemeh",
            "karimi",
            "09123456789",
            "fatemehkarimi@gmail.com",
            "$2a$10$EdJnVTWFWG4j9VorOuO7W.7fkn2AB/P.Od4TUbFO4x.vqraR80Tq2");

    Address address1 = new Address(1L, "Isfahan", "Khajoo bridge", "91152908763", user);
    Address address2 = new Address(2L, "Isfahan", "Chahar bagh Abbasi", "1144009988", user);
    Address address3 = new Address(3L, "Tehran", "Resalat square", "01274463849", user);

    @Test
    @WithMockUser(username="fatemehkarimi@gmail.com", password="admin")
    public void addUserAddress() throws Exception {
        Mockito.when(userDao.findByEmail(user.getEmail())).thenReturn(user);
        Mockito.when(addressDao.save(address1)).thenReturn(address1);

        Object addressData = new Object() {
            public String city = address1.getCity();
            public String streetAddress = address1.getCity();
            public String postalCode = address1.getPostalCod();
        };

        String json = objectMapper.writeValueAsString(addressData);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user/address/add")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void forbidUnauthorizedUser() throws Exception {
        Object addressData = new Object() {
            public String city = address1.getCity();
            public String streetAddress = address1.getCity();
            public String postalCode = address1.getPostalCod();
        };

        String json = objectMapper.writeValueAsString(addressData);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user/address/add")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
