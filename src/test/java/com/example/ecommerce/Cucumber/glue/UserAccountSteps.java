package com.example.ecommerce.Cucumber.glue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasKey;

public class UserAccountSteps {
    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;

    String email;
    String password;
    String updated_phoneNumber;
    String addressId;
    String updated_cityName;
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

    @When("i send a request to URL {string} to update the city of address with id = {string} to {string}")
    public void i_send_a_request_to_url_to_update_the_city_of_address_with_id_to(
            String endpoint, String addressId, String updated_cityName) throws Exception
    {

        this.updated_cityName = updated_cityName;
        this.addressId = addressId;

        Object object = new Object() {
            public String city = updated_cityName;
        };

        String json = objectMapper.writeValueAsString(object);
        given().header("Cookie", this.cookie)
                .contentType(ContentType.JSON)
                .body(json)
                .put(endpoint)
                .then().statusCode(200);

    }

    @Then("i can see that the city name of that address has been updated at URL {string}")
    public void i_can_see_that_the_city_name_of_that_address_has_been_updated_at_url(String endpoint) throws Exception {
        List <Map<String, Object> > result =
        given().header("Cookie", this.cookie)
                .get(endpoint).then()
                .extract().body().as(new TypeRef<List<Map<String, Object>>>() {});

        for(Map <String, Object> address : result) {
            if(address.get("id").equals(this.addressId)) {
                assertThat(address.get("city")).isEqualTo(this.updated_cityName);
                break;
            }
        }
    }

    @When("i send a request to URL {string} to see list of orders")
    public void i_send_a_request_to_url_to_see_list_of_orders(String endpoint) {
        response = given().header("Cookie", this.cookie)
                .get(endpoint);
    }

    @Then("i can see the list of my previous orders")
    public void i_can_see_the_list_of_my_previous_orders() {
        response.then().statusCode(200);
        int length = JsonPath
                .parse(response.body().asString())
                .read("$.length()");

        assertThat(length).isGreaterThan(0);
    }

    @When("i send a request to URL {string} to see details of an order")
    public void i_send_a_request_to_url_to_see_details_of_an_order(String endpoint) {
        response = given().header("Cookie", this.cookie)
                .get(endpoint);
    }
    @Then("i receive a list of products that were ordered in order {string}")
    public void i_receive_a_list_of_products_that_were_ordered_in_order(String order_id) {
        response.then().statusCode(200);
        String result_id = response.jsonPath().get("id").toString();
        assertThat(result_id).isEqualTo(order_id);

        response.then().body("$", hasKey("products"));
        int count_products = response.jsonPath().getList("products").size();
        assertThat(count_products).isGreaterThan(0);
    }
}
