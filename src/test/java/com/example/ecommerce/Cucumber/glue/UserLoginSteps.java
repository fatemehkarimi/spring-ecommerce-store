package com.example.ecommerce.Cucumber.glue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class UserLoginSteps {
    private String email;
    private String password;

    @Given("a user with email = {string} and password = {string}")
    public void a_user_with_email_and_password(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @When("i try to login to my account")
    public void i_try_to_login_to_my_account() {
    }
}
