package com.example.ecommerce.web;

import java.util.List;

import com.example.ecommerce.dao.ProductDao;
import com.example.ecommerce.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductDao productDao;


    @GetMapping
    public ResponseEntity <List <Product> > getProductList() {
        List <Product> result = productDao.findAll();
        return new ResponseEntity <List <Product> > (result, HttpStatus.OK);
    }
    
}
