package com.omsu.chores_exchanger.models.requests.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.beans.ConstructorProperties;

@Data
public class SignInRequest {
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private final String login;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private final String password;

    @ConstructorProperties({"login", "password"})
    public SignInRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
