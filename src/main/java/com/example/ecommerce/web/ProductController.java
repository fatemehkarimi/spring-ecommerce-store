package com.example.ecommerce.web;

import java.util.List;
import java.util.Optional;

import com.example.ecommerce.dao.ProductDao;
import com.example.ecommerce.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ProductController {
    @Autowired
    private ProductDao productDao;


    @GetMapping("/products")
    public ResponseEntity <List <Product> > getProductList() {
        List <Product> result = productDao.findAll();
        return new ResponseEntity <List <Product> > (result, HttpStatus.OK);
    }
    
    @GetMapping("/product/{id}")
    public ResponseEntity <Optional <Product> > getProduct(@PathVariable("id") long id) {
        Optional<Product> result = productDao.findById(id);
        return new ResponseEntity<Optional <Product> >(result, HttpStatus.OK);
    }
}
