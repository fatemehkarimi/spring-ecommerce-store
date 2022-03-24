package com.example.ecommerce.Cucumber.glue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.web.server.LocalServerPort;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserAccountSteps {
    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;

    String email;
    String password;
    String updated_phoneNumber;
    String cookie;
    ObjectMapper objectMapper = new ObjectMapper();

    private Response response;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Given("i am a logged in user with email = {string} and password = {string}")
    public void i_am_a_logged_in_user_with_email_and_password(
            String user_email, String user_password) throws Exception
    {
        this.email = user_email;
        this.password = user_password;

        Object loginData = new Object() {
            public String email = "fatemehkarimi.1998@yahoo.com";
            public String password = "admin";
        };

        String json = objectMapper.writeValueAsString(loginData);
        this.cookie = given().contentType(ContentType.JSON)
                        .body(json).post("/api/auth/login")
                        .headers().getValue("Set-Cookie");
        this.cookie = this.cookie.split(";")[0];
    }

    @When("i send a request to URL {string} to change my phone number to {string}")
    public void i_send_a_request_to_url_to_change_my_phone_number_to(String endpoint, String new_phoneNumber)
            throws Exception
    {
        this.updated_phoneNumber = new_phoneNumber;
        Object updateData = new Object() {
            public String phoneNumber = new_phoneNumber;
        };

        String json = objectMapper.writeValueAsString(updateData);

        response = given().header("Cookie", this.cookie)
                .contentType(ContentType.JSON)
                .body(json)
                .post(endpoint);
    }

    @Then("i receive a response that notifies me the account has been updated")
    public void i_receive_a_response_that_notifies_me_the_account_has_been_updated() {
        response.then().assertThat().statusCode(200);
    }

    @Then("i can see my updated profile at URL {string}")
    public void i_can_see_my_updated_profile_at_url(String endpoint) throws Exception{
        String result = given().header("Cookie", this.cookie)
                .get(endpoint)
                .jsonPath().get("phoneNumber");
        assertThat(result).isEqualTo(updated_phoneNumber);
    }
}
