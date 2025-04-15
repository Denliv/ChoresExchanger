package com.omsu.chores_exchanger.models.requests.chore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class ChangeChoreStatusRequest {
    @NotNull
    @NotBlank
    private final String userLogin;
    @NotNull
    @NotBlank
    private final String choreId;

    @ConstructorProperties({"userLogin", "choreId"})
    public ChangeChoreStatusRequest(String userLogin, String choreId) {
        this.userLogin = userLogin;
        this.choreId = choreId;
    }
}
