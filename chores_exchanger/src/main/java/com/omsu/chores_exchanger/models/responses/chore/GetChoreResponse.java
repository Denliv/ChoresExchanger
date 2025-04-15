package com.omsu.chores_exchanger.models.responses.chore;

import com.omsu.chores_exchanger.models.entities.ChoreImage;
import com.omsu.chores_exchanger.models.entities.Chore;
import com.omsu.chores_exchanger.models.entities.ChoreStatus;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.Base64;
import java.util.List;

@Getter
public class GetChoreResponse {
    private final String name;
    private final String info;
    private final String statusName;
    private final String statusInfo;
    private final String userLogin;
    private final String userLastName;
    private final String userFirstName;
    private final String userApartment;
    private final String[] images;

    @ConstructorProperties({"name", "info", "statusName", "statusInfo", "userLogin", "userLastName", "userFirstName", "userApartment", "images"})
    public GetChoreResponse(String name, String info, String statusName, String statusInfo, String userLogin, String userLastName, String userFirstName, String userApartment, List<ChoreImage> images) {
        this.name = name;
        this.info = info;
        this.statusName = statusName;
        this.statusInfo = statusInfo;
        this.userLogin = userLogin;
        this.userLastName = userLastName;
        this.userFirstName = userFirstName;
        this.userApartment = userApartment;
        this.images = images != null ? images.stream().map(image -> Base64.getEncoder().encodeToString(image.getImageData())).toArray(String[]::new) : null;
    }

    public GetChoreResponse(Chore chore) {
        this(
                chore.getName(),
                chore.getInfo(),
                ChoreStatus.valueOf(chore.getStatus()).getName(),
                ChoreStatus.valueOf(chore.getStatus()).getInfo(),
                chore.getUserCreator().getLogin(),
                chore.getUserCreator().getLastName(),
                chore.getUserCreator().getFirstName(),
                chore.getUserCreator().getApartment(),
                chore.getImages()
        );
    }
}
