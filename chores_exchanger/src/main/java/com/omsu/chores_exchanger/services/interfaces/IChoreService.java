package com.omsu.chores_exchanger.services.interfaces;

import com.omsu.chores_exchanger.models.exceptions.ChoreNotFoundException;
import com.omsu.chores_exchanger.models.exceptions.SignInException;
import com.omsu.chores_exchanger.models.exceptions.UserAuthorityException;
import com.omsu.chores_exchanger.models.exceptions.UserNotFoundException;
import com.omsu.chores_exchanger.models.requests.chore.AddChoreRequest;
import com.omsu.chores_exchanger.models.requests.chore.ChangeChoreStatusRequest;
import com.omsu.chores_exchanger.models.responses.chore.AddChoreResponse;
import com.omsu.chores_exchanger.models.responses.chore.FetchChoreListResponse;
import com.omsu.chores_exchanger.models.responses.chore.GetChoreResponse;

import java.io.IOException;

public interface IChoreService {
    AddChoreResponse add(AddChoreRequest request) throws UserNotFoundException, SignInException, IOException;
    GetChoreResponse getChoreById(String id) throws ChoreNotFoundException;
    FetchChoreListResponse getAllCreated(String login, String password);
    FetchChoreListResponse getCreatedByCreatorLogin(String login, String password);
    FetchChoreListResponse getTakenByCreatorLogin(String login, String password);
    FetchChoreListResponse getTakenByExecutorLogin(String login, String password);
    FetchChoreListResponse getCompletedByExecutorLogin(String login, String password);
    void takeChoreByExecutorLogin(ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException;
    void deleteChoreByCreatorLogin(ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException;
    void refuseChoreByExecutorLogin(ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException;
    void completeChoreByCreatorLogin(ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException;
}
