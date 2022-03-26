package com.example.ecommerce.Cucumber.glue;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductSteps {
    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;

    Response response;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = this.port;
    }

    @When("i send a request to URL {string} to fetch products")
    public void i_send_a_request_to_url_to_fetch_products(String endpoint) {
        this.response = given().get(endpoint);
    }

    @Then("i must get a list of products")
    public void i_must_get_a_list_of_products() {
        response.then().assertThat().statusCode(200);
        int length = JsonPath
                .parse(response.body().asString())
                .read("$.length()");

        assertThat(length).isGreaterThan(0);
    }

    @When("i send a request to URL {string} to see the details of product {string}")
    public void i_send_a_request_to_url_to_see_the_details_of_product(String endpoint, String id) {
        response = given().get(endpoint);
    }
    @Then("i get an object with id equals to {string}")
    public void i_get_an_object_with_id_equals_to(String id) {
        response.then().assertThat().statusCode(200)
                .extract().path("id").equals(id);
    }
}
