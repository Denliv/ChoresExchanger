package com.omsu.chores_exchanger.models.entities;

import lombok.Getter;

@Getter
public enum ChoreStatus {
    CREATED("CREATED", "Готова к исполнению"),
    TAKEN("TAKEN", "Принята к выполнению"),
    COMPLETED("COMPLETED", "Выполнена");
    private final String name;
    private final String info;

    ChoreStatus(String name, String info) {
        this.name = name;
        this.info = info;
    }
}