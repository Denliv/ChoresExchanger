package com.omsu.chores_exchanger.controllers;

import com.omsu.chores_exchanger.models.exceptions.ChoreNotFoundException;
import com.omsu.chores_exchanger.models.exceptions.SignInException;
import com.omsu.chores_exchanger.models.exceptions.UserAuthorityException;
import com.omsu.chores_exchanger.models.exceptions.UserNotFoundException;
import com.omsu.chores_exchanger.models.requests.chore.AddChoreRequest;
import com.omsu.chores_exchanger.models.requests.chore.ChangeChoreStatusRequest;
import com.omsu.chores_exchanger.services.interfaces.IChoreService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.service.spi.ServiceException;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
public class ChoreController {
    @Autowired
    private final IChoreService choreService;

    public ChoreController(IChoreService choreService) {
        this.choreService = choreService;
    }

    @PostMapping(value = "/addChore", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addChore(
            @RequestPart("userLogin") String userLogin,
            @RequestPart("userPassword") String userPassword,
            @RequestPart("name") String name,
            @RequestPart("info") String info,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws ServiceException, UserNotFoundException, SignInException, IOException {
        AddChoreRequest request = new AddChoreRequest(userLogin, userPassword, name, info, images);
        return new ResponseEntity<>(choreService.add(request), HttpStatus.OK);
    }

    @GetMapping(value = "/chore/{id}")
    public ResponseEntity<?> getChoreInfo(@Validated @UUID @PathVariable("id") String id) throws ServiceException, ChoreNotFoundException {
        return new ResponseEntity<>(choreService.getChoreById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/getMainPageChores/{login}/{password}")
    public ResponseEntity<?> getMainPageChores(@NotNull @NotBlank @Validated @PathVariable("login") String login,
                                               @NotNull @NotBlank @Validated @PathVariable("password") String password) {
        return new ResponseEntity<>(choreService.getAllCreated(login, password), HttpStatus.OK);
    }

    @GetMapping(value = "/getGivenPageChoresTaken/{login}/{password}")
    public ResponseEntity<?> getGivenPageChoresTaken(@NotNull @NotBlank @Validated @PathVariable("login") String login,
                                                @NotNull @NotBlank @Validated @PathVariable("password") String password) {
        return new ResponseEntity<>(choreService.getTakenByCreatorLogin(login, password), HttpStatus.OK);
    }

    @GetMapping(value = "/getGivenPageChoresNotTaken/{login}/{password}")
    public ResponseEntity<?> getGivenPageChoresNotTaken(@NotNull @NotBlank @Validated @PathVariable("login") String login,
                                                @NotNull @NotBlank @Validated @PathVariable("password") String password) {
        return new ResponseEntity<>(choreService.getCreatedByCreatorLogin(login, password), HttpStatus.OK);
    }

    @GetMapping(value = "/getTakenPageChores/{login}/{password}")
    public ResponseEntity<?> getTakenPageChores(@NotNull @NotBlank @Validated @PathVariable("login") String login,
                                                @NotNull @NotBlank @Validated @PathVariable("password") String password) {
        var a = choreService.getTakenByExecutorLogin(login, password);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @GetMapping(value = "/getCompletedPageChores/{login}/{password}")
    public ResponseEntity<?> getCompletedPageChores(@NotNull @NotBlank @Validated @PathVariable("login") String login,
                                                    @NotNull @NotBlank @Validated @PathVariable("password") String password) {
        return new ResponseEntity<>(choreService.getCompletedByExecutorLogin(login, password), HttpStatus.OK);
    }

    @PutMapping(value = "/takeChore")
    public ResponseEntity<?> takeChore(@Validated @RequestBody ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException {
        choreService.takeChoreByExecutorLogin(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteChore")
    public ResponseEntity<?> deleteChore(@Validated @RequestBody ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException {
        choreService.deleteChoreByCreatorLogin(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/refuseChore")
    public ResponseEntity<?> refuseChore(@Validated @RequestBody ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException {
        choreService.refuseChoreByExecutorLogin(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/completeChore")
    public ResponseEntity<?> completeChore(@Validated @RequestBody ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException {
        choreService.completeChoreByCreatorLogin(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
