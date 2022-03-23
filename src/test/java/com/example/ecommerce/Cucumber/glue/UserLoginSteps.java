package com.example.ecommerce.Cucumber.glue;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserLoginSteps {
    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;

    ObjectMapper objectMapper = new ObjectMapper();

    private Response response;
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
    public void i_send_a_request_to_url_with_email_and_password(String endpoint, String user_email, String user_password)
            throws Exception
    {
        this.email = user_email;
        this.password = user_password;
        Object loginData = new Object() {
            public String email = user_email;
            public String password = user_password;
        };

        String json = objectMapper.writeValueAsString(loginData);
        this.response = requestSpecification().contentType(ContentType.JSON)
                .body(json).post(endpoint);
    }

    @Then("the result is {string}")
    public void the_result_is(String result) {
        if(result.equals("authenticated")) {
            this.response.then().assertThat().statusCode(200);
            String message = this.response.then().extract().asString();
            assertThat(message).isEqualTo("user is now logged in");
        }
        else {
            this.response.then().assertThat().statusCode(401);
            String message = this.response.jsonPath().get("message");
            assertThat(message).isEqualTo("Bad credentials");
        }
    }
}
