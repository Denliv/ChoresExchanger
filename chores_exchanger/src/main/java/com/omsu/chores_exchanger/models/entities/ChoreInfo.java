package com.omsu.chores_exchanger.models.entities;

import lombok.Data;

import java.beans.ConstructorProperties;

@Data
public class ChoreInfo {
    private final String id;
    private final String name;
    private final String status;

    @ConstructorProperties({"id", "name", "status"})
    public ChoreInfo(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}
