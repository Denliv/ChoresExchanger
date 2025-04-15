package com.omsu.chores_exchanger.models.responses.user;

import com.omsu.chores_exchanger.models.entities.User;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class AddUserResponse {
    private final String login;

    @ConstructorProperties({"login"})
    public AddUserResponse(String login) {
        this.login = login;
    }

    public AddUserResponse(User user) {
        this(user.getLogin());
    }
}
