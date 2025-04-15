package com.omsu.chores_exchanger.services.realizations;

import com.omsu.chores_exchanger.models.entities.Chore;
import com.omsu.chores_exchanger.models.entities.ChoreStatus;
import com.omsu.chores_exchanger.models.entities.User;
import com.omsu.chores_exchanger.models.exceptions.ChoreNotFoundException;
import com.omsu.chores_exchanger.models.exceptions.SignInException;
import com.omsu.chores_exchanger.models.exceptions.UserAuthorityException;
import com.omsu.chores_exchanger.models.exceptions.UserNotFoundException;
import com.omsu.chores_exchanger.models.requests.chore.AddChoreRequest;
import com.omsu.chores_exchanger.models.requests.chore.ChangeChoreStatusRequest;
import com.omsu.chores_exchanger.models.responses.chore.AddChoreResponse;
import com.omsu.chores_exchanger.models.responses.chore.FetchChoreListResponse;
import com.omsu.chores_exchanger.models.responses.chore.GetChoreResponse;
import com.omsu.chores_exchanger.repositories.interfaces.IChoreRepository;
import com.omsu.chores_exchanger.repositories.interfaces.IUserRepository;
import com.omsu.chores_exchanger.services.interfaces.IChoreService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChoreService implements IChoreService {
    @Autowired
    private IChoreRepository choreRepository;
    @Autowired
    private IUserRepository userRepository;

    @Transactional
    @Override
    public AddChoreResponse add(AddChoreRequest request) throws UserNotFoundException, SignInException, IOException {
        User user = userRepository.findById(request.getUserLogin()).orElseThrow(() -> new UserNotFoundException("Can not find user with this login"));
        if (!user.getLogin().equals(request.getUserLogin())) {
            throw new SignInException("Incorrect login. Try to refresh the page and sign in again.");
        }
        if (!user.getPassword().equals(request.getUserPassword())) {
            throw new SignInException("Incorrect password. Try to refresh the page and sign in again.");
        }
        Chore chore = new Chore(
                null,
                user,
                request.getName(),
                request.getInfo(),
                ChoreStatus.CREATED.getName(),
                request.getImages());
        var save = choreRepository.save(chore);
        return new AddChoreResponse(save);
    }

    @Transactional
    @Override
    public GetChoreResponse getChoreById(String id) throws ChoreNotFoundException {
        var chore = choreRepository.findById(id).orElseThrow(() -> new ChoreNotFoundException("Can not find chore with this id"));
        return new GetChoreResponse(chore);
    }

    @Override
    public FetchChoreListResponse getAllCreated(String login, String password) {
        var list = choreRepository.findAllByStatus(ChoreStatus.CREATED.getName());
        return new FetchChoreListResponse(list);
    }

    @Override
    public FetchChoreListResponse getCreatedByCreatorLogin(String login, String password) {
        return new FetchChoreListResponse(choreRepository.findByCreatorLoginAndStatus(login, ChoreStatus.CREATED.getName()));
    }

    @Override
    public FetchChoreListResponse getTakenByCreatorLogin(String login, String password) {
        return new FetchChoreListResponse(choreRepository.findByCreatorLoginAndStatus(login, ChoreStatus.TAKEN.getName()));
    }

    @Override
    public FetchChoreListResponse getTakenByExecutorLogin(String login, String password) {
        return new FetchChoreListResponse(choreRepository.findByExecutorLoginAndStatus(login, ChoreStatus.TAKEN.getName()));
    }

    @Override
    public FetchChoreListResponse getCompletedByExecutorLogin(String login, String password) {
        return new FetchChoreListResponse(choreRepository.findByExecutorLoginAndStatus(login, ChoreStatus.COMPLETED.getName()));
    }

    @Override
    public void takeChoreByExecutorLogin(ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException {
        var chore = choreRepository.findById(request.getChoreId()).orElseThrow(() -> new ChoreNotFoundException("Can not find chore with this id"));
        User user = userRepository.findById(request.getUserLogin()).orElseThrow(() -> new UserNotFoundException("Can not find user with this login"));
        if (chore.getUserCreator().getLogin().equals(user.getLogin())) {
            throw new UserAuthorityException("Chore can not be taken for execution by its creator");
        }
        if (!chore.getStatus().equals(ChoreStatus.CREATED.getName())) {
            throw new UserAuthorityException("Only created chores can be taken for execution");
        }
        if (chore.getUserExecutor() != null) {
            throw new UserAuthorityException("Only chores without executor can be taken");
        }
        chore.setUserExecutor(user);
        chore.setStatus(ChoreStatus.TAKEN.getName());
        choreRepository.save(chore);
    }

    @Override
    public void deleteChoreByCreatorLogin(ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException {
        var chore = choreRepository.findById(request.getChoreId()).orElseThrow(() -> new ChoreNotFoundException("Can not find chore with this id"));
        User user = userRepository.findById(request.getUserLogin()).orElseThrow(() -> new UserNotFoundException("Can not find user with this login"));
        if (!chore.getUserCreator().getLogin().equals(user.getLogin())) {
            throw new UserAuthorityException("Chore can be deleted only by its creator");
        }
        if (!chore.getStatus().equals(ChoreStatus.CREATED.getName())) {
            throw new UserAuthorityException("Only created chores can be deleted");
        }
        if (chore.getUserExecutor() != null) {
            throw new UserAuthorityException("Only chores without executor can be deleted");
        }
        choreRepository.deleteById(chore.getId());
    }

    @Override
    public void refuseChoreByExecutorLogin(ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException {
        var chore = choreRepository.findById(request.getChoreId()).orElseThrow(() -> new ChoreNotFoundException("Can not find chore with this id"));
        User user = userRepository.findById(request.getUserLogin()).orElseThrow(() -> new UserNotFoundException("Can not find user with this login"));
        if (chore.getUserExecutor() == null) {
            throw new UserAuthorityException("Only chores with executor can be refused");
        }
        if (!chore.getUserExecutor().getLogin().equals(user.getLogin())) {
            throw new UserAuthorityException("Chore can be refused only by its executor");
        }
        if (!chore.getStatus().equals(ChoreStatus.TAKEN.getName())) {
            throw new UserAuthorityException("Only taken chores can be refused for execution");
        }
        chore.setUserExecutor(null);
        chore.setStatus(ChoreStatus.CREATED.getName());
        choreRepository.save(chore);
    }

    @Override
    public void completeChoreByCreatorLogin(ChangeChoreStatusRequest request) throws ChoreNotFoundException, UserNotFoundException, UserAuthorityException {
        var chore = choreRepository.findById(request.getChoreId()).orElseThrow(() -> new ChoreNotFoundException("Can not find chore with this id"));
        User user = userRepository.findById(request.getUserLogin()).orElseThrow(() -> new UserNotFoundException("Can not find user with this login"));
        if (chore.getUserExecutor() == null) {
            throw new UserAuthorityException("Only chores with executor can be completed");
        }
        if (!chore.getUserCreator().getLogin().equals(user.getLogin())) {
            throw new UserAuthorityException("Chore can be completed only by its creator");
        }
        if (!chore.getStatus().equals(ChoreStatus.TAKEN.getName())) {
            throw new UserAuthorityException("Only taken chores can be completed");
        }
        chore.setStatus(ChoreStatus.COMPLETED.getName());
        choreRepository.save(chore);
    }
}
