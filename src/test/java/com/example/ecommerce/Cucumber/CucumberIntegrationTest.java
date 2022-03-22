package com.example.ecommerce.Cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(
        plugin={"pretty", "json:target/cucumber-report.json", "html:target/cucumber-report"},
        glue={"src/test/java/com/example/ecommerce/Cucumber/glue"},
        features="src/test/resources")
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberIntegrationTest {
    
}
