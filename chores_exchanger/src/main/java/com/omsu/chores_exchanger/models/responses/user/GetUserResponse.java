package com.omsu.chores_exchanger.models.responses.user;

import com.omsu.chores_exchanger.models.entities.User;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class GetUserResponse {
    private final String login;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String apartment;

    @ConstructorProperties({"login", "password", "firstName", "lastName", "apartment"})
    public GetUserResponse(String login, String password, String firstName, String lastName, String apartment) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.apartment = apartment;
    }

    public GetUserResponse(User user) {
        this(user.getLogin(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getApartment());
    }
}
