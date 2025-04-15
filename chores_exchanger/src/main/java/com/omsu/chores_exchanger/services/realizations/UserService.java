package com.omsu.chores_exchanger.services.realizations;

import com.omsu.chores_exchanger.models.entities.ChoreStatus;
import com.omsu.chores_exchanger.models.entities.User;
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
import com.omsu.chores_exchanger.repositories.interfaces.IChoreRepository;
import com.omsu.chores_exchanger.repositories.interfaces.IUserRepository;
import com.omsu.chores_exchanger.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IChoreRepository choreRepository;

    @Override
    public AddUserResponse add(AddUserRequest request) throws UserAlreadyExistException {
        if (userRepository.existsById(request.getLogin())) {
            throw new UserAlreadyExistException("This login is already used. Try different login");
        }
        User user = new User(request.getLastName(), request.getFirstName(), request.getLogin(), request.getPassword(), request.getApartment());
        var save = userRepository.save(user);
        return new AddUserResponse(save);
    }

    @Override
    public SignInResponse signIn(SignInRequest request) throws SignInException, UserNotFoundException {
        var user = userRepository.findById(request.getLogin()).orElseThrow(() -> new UserNotFoundException("Can not find user with this login"));
        if (!user.getLogin().equals(request.getLogin())) {
            throw new SignInException("Incorrect login in sign in");
        }
        if (!user.getPassword().equals(request.getPassword())) {
            throw new SignInException("Incorrect password in sign in");
        }
        return new SignInResponse(user);
    }

    @Override
    public GetUserResponse get(String login, String password) throws UserNotFoundException, SignInException {
        var user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException("Can not find user with this login"));
        if (!user.getLogin().equals(login)) {
            throw new SignInException("Incorrect login. Try to refresh the page and sign in again.");
        }
        if (!user.getPassword().equals(password)) {
            throw new SignInException("Incorrect password. Try to refresh the page and sign in again.");
        }
        return new GetUserResponse(user);
    }

    @Override
    public void delete(DeleteUserRequest request) throws UserNotFoundException, SignInException, DeleteAccountException {
        var user = userRepository.findById(request.getLogin()).orElseThrow(() -> new UserNotFoundException("Can not find user with this login"));
        if (!user.getLogin().equals(request.getLogin())) {
            throw new SignInException("Incorrect login. Try to refresh the page and try to delete account again.");
        }
        if (!user.getPassword().equals(request.getPassword())) {
            throw new SignInException("Incorrect password. Try to refresh the page and try to delete account again.");
        }
        if (choreRepository.findByCreatorLoginAndStatus(user.getLogin(), ChoreStatus.CREATED.getName()).size() != 0 ||
            choreRepository.findByCreatorLoginAndStatus(user.getLogin(), ChoreStatus.TAKEN.getName()).size() != 0 ||
            choreRepository.findByExecutorLoginAndStatus(user.getLogin(), ChoreStatus.TAKEN.getName()).size() != 0) {
            throw new DeleteAccountException("Can not delete account with given or taken chores. Refuse, complete or delete them and try agaiin");
        }
        userRepository.deleteById(user.getLogin());
    }
}
