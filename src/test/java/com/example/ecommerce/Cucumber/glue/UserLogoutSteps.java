package com.example.ecommerce.Cucumber.glue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserLogoutSteps {
    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;

    ObjectMapper objectMapper = new ObjectMapper();

    private void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    protected RequestSpecification requestSpecification() {
        configureRestAssured();
        return given();
    }

    @Given("i am already logged in with email = {string} and password = {string}")
    public void i_am_already_logged_in_with_email_and_password(
            String user_email, String user_password) throws Exception
    {
        Object loginData = new Object() {
            public String email = user_email;
            public String password = user_password;
        };

        String json = objectMapper.writeValueAsString(loginData);
        requestSpecification().contentType(ContentType.JSON)
                .body(json).post("/api/auth/login")
                .then().assertThat().statusCode(200);
    }

    @When("i send a request to URL {string}")
    public void i_send_a_request_to_url(String endpoint) {
        requestSpecification().post(endpoint)
                .then().assertThat().statusCode(200);
    }

    @Then("i must not be able to see my profile information at URL {string}")
    public void i_must_not_be_able_to_see_my_profile_information(String endpoint) {
        Response response = requestSpecification().get(endpoint);
        response.then().assertThat().statusCode(401);

        String message = response.jsonPath().get("message");
        assertThat(message).isEqualTo("Full authentication is required to access this resource");
    }
}
