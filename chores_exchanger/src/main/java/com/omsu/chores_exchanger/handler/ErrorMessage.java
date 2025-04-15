package com.omsu.chores_exchanger.handler;

import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class ErrorMessage {
    private final String message;

    @ConstructorProperties({"message"})
    public ErrorMessage(String message) {
        this.message = message;
    }
}
