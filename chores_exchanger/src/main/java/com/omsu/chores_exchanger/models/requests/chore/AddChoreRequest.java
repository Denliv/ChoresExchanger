package com.omsu.chores_exchanger.models.requests.chore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;

@Getter
public class AddChoreRequest {
    @NotNull
    @NotBlank
    private final String userLogin;
    @NotNull
    @NotBlank
    private final String userPassword;
    @NotNull
    @NotBlank
    private final String name;
    @NotNull
    @NotBlank
    private final String info;

    private MultipartFile[] images;

    @ConstructorProperties({"userLogin", "userPassword", "name", "info", "images"})
    public AddChoreRequest(String userLogin, String userPassword, String name, String info, MultipartFile[] images) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.name = name;
        this.info = info;
        this.images = images;
    }
}
