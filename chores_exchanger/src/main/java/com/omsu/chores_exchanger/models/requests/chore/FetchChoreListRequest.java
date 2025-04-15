package com.omsu.chores_exchanger.models.requests.chore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class FetchChoreListRequest {
    @NotNull
    @NotBlank
    private final String login;
    @NotNull
    @NotBlank
    private final String password;

    @ConstructorProperties({"login", "password"})
    public FetchChoreListRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
