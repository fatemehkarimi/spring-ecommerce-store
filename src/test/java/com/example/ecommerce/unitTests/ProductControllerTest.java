package com.example.ecommerce.unitTests;

import com.example.ecommerce.dao.ProductDao;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.web.ProductController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({ProductController.class})
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductDao productDao;

    @MockBean
    UserService userService;

    List <Product> products = Arrays.asList(
        new Product(1, "samsung phone", "Cell phone", 322.99),
        new Product(2, "apple iphone", "Cell phone", 1099),
        new Product(3, "LG Tv", "Tv", 542)
    );

    @Test
    public void getProductsList() throws Exception{
        Mockito.when(productDao.findAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @Test
    public void getProduct() throws Exception {
        Mockito.when(productDao.findById(1L)).thenReturn(Optional.ofNullable(products.get(0)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("samsung phone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Cell phone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("322.99"));
    }
}
