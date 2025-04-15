package com.omsu.chores_exchanger.controllers;

import com.omsu.chores_exchanger.models.exceptions.DeleteAccountException;
import com.omsu.chores_exchanger.models.exceptions.SignInException;
import com.omsu.chores_exchanger.models.exceptions.UserAlreadyExistException;
import com.omsu.chores_exchanger.models.exceptions.UserNotFoundException;
import com.omsu.chores_exchanger.models.requests.user.AddUserRequest;
import com.omsu.chores_exchanger.models.requests.user.DeleteUserRequest;
import com.omsu.chores_exchanger.models.requests.user.SignInRequest;
import com.omsu.chores_exchanger.services.interfaces.IUserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
    @Autowired
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/addUser")
    public ResponseEntity<?> addUser(@Validated @RequestBody AddUserRequest request) throws ServiceException, UserAlreadyExistException {
        return new ResponseEntity<>(userService.add(request), HttpStatus.OK);
    }

    @PostMapping(value = "/signIn")
    public ResponseEntity<?> signIn(@Validated @RequestBody SignInRequest request) throws ServiceException, SignInException, UserNotFoundException {
        return new ResponseEntity<>(userService.signIn(request), HttpStatus.OK);
    }

    @GetMapping(value = "/getAccountDetails/{login}/{password}")
    public ResponseEntity<?> getUser(
            @NotNull @NotBlank @Validated @PathVariable("login") String login,
            @NotNull @NotBlank @Validated @PathVariable("password") String password) throws UserNotFoundException, SignInException {
        return new ResponseEntity<>(userService.get(login, password), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteAccount")
    public ResponseEntity<?> deleteUser(@Validated @RequestBody DeleteUserRequest request) throws UserNotFoundException, SignInException, DeleteAccountException {
        userService.delete(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
