package com.example.ecommerce.Cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
//@CucumberContextConfiguration
//@SpringBootTest(classes={EcommerceApplication.class,
//                         CucumberIntegrationTest.class},
//                         webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberOptions(plugin={"pretty"}, tags="", features="src/test/resources")
public class CucumberIntegrationTest {
    
}
