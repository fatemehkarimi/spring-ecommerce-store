package com.example.ecommerce.Cucumber.glue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

public class UserLoginSteps {
    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;

    private ValidatableResponse validatableResponse;
    private String email;
    private String password;

    private void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    protected RequestSpecification requestSpecification() {
        configureRestAssured();
        return given();
    }

    @Given("i send a request to URL {string} with email = {string} and password = {string}")
    public void i_send_a_request_to_url_with_email_and_password(String url, String email, String password) {
    }

    @Then("the result is {string}")
    public void the_result_is(String string) {
    }
}
