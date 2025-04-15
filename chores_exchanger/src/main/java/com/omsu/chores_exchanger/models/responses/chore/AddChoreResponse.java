package com.omsu.chores_exchanger.models.responses.chore;

import com.omsu.chores_exchanger.models.entities.Chore;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class AddChoreResponse {
    private final String id;

    @ConstructorProperties({"id"})
    public AddChoreResponse(String id) {
        this.id = id;
    }

    public AddChoreResponse(Chore chore) {
        this(chore.getId());
    }
}
