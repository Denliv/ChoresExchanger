package com.omsu.chores_exchanger.services.interfaces;

import com.omsu.chores_exchanger.models.exceptions.DeleteAccountException;
import com.omsu.chores_exchanger.models.exceptions.SignInException;
import com.omsu.chores_exchanger.models.exceptions.UserAlreadyExistException;
import com.omsu.chores_exchanger.models.exceptions.UserNotFoundException;
import com.omsu.chores_exchanger.models.requests.user.AddUserRequest;
import com.omsu.chores_exchanger.models.requests.user.DeleteUserRequest;
import com.omsu.chores_exchanger.models.requests.user.SignInRequest;
import com.omsu.chores_exchanger.models.responses.user.AddUserResponse;
import com.omsu.chores_exchanger.models.responses.user.GetUserResponse;
import com.omsu.chores_exchanger.models.responses.user.SignInResponse;

public interface IUserService {
    AddUserResponse add(AddUserRequest request) throws UserAlreadyExistException;

    SignInResponse signIn(SignInRequest request) throws SignInException, UserNotFoundException;

    GetUserResponse get(String login, String password) throws UserNotFoundException, SignInException;
    void delete(DeleteUserRequest request) throws UserNotFoundException, SignInException, DeleteAccountException;
}
