package com.omsu.chores_exchanger.models.requests.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.beans.ConstructorProperties;

@Getter
public class AddUserRequest {
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private final String lastName;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private final String firstName;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private final String login;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private final String password;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private final String apartment;

    @ConstructorProperties({"lastName", "firstName", "login", "password", "apartment"})
    public AddUserRequest(String lastName, String firstName, String login, String password, String apartment) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        this.apartment = apartment;
    }
}
