package com.omsu.chores_exchanger.models.responses.user;

import com.omsu.chores_exchanger.models.entities.User;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class SignInResponse {
    private final String login;
    private final String password;
    private final String firstName;
    private final String lastName;


    @ConstructorProperties({"login", "password", "firstName", "lastName"})
    public SignInResponse(String login, String password, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public SignInResponse(User user) {
        this(user.getLogin(), user.getPassword(), user.getFirstName(), user.getLastName());
    }
}
