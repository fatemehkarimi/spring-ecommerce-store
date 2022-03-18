package com.example.ecommerce.Cucumber.glue;

import java.util.ArrayList;
import java.util.List;

import com.example.ecommerce.model.User;

import com.example.ecommerce.web.dto.UserLoginDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class UserLoginSteps {
    private String email;
    private String password;
    private List <User> signedUpUsers = new ArrayList<User>();
    private UserLoginDto loginDto;

    @Given("a user with email = {string} and password = {string}")
    public void a_user_with_email_and_password(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Given("the following users are already available")
    public void the_following_users_are_already_available(io.cucumber.datatable.DataTable dataTable) {
        List <List <String>> rows = dataTable.asLists(String.class);

        for(List <String> columns : rows) {
            String email = columns.get(0);
            String password = columns.get(1);
            User user = new User(null, null, null, null, email, password);
            signedUpUsers.add(user);
        }
    }

    @When("i try to login to my account")
    public void i_try_to_login_to_my_account() {
        this.loginDto = new UserLoginDto(email, password);
        
    }
}
