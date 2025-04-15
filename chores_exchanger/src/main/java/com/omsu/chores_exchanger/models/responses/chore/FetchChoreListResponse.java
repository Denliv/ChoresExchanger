package com.omsu.chores_exchanger.models.responses.chore;

import com.omsu.chores_exchanger.models.entities.Chore;
import com.omsu.chores_exchanger.models.entities.ChoreInfo;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

@Getter
public class FetchChoreListResponse {
    private final List<ChoreInfo> list;

    @ConstructorProperties({"list"})
    public FetchChoreListResponse(List<Chore> list) {
        this.list = new ArrayList<>(list.stream().map(o -> new ChoreInfo(o.getId(), o.getName(), o.getStatus())).toList());
    }
}

