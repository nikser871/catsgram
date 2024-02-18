package com.catsgram.catsgram.service;

import com.catsgram.catsgram.exception.InvalidEmailException;
import com.catsgram.catsgram.exception.UserAlreadyExistException;
import com.catsgram.catsgram.model.UserAnother;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    private final Set<UserAnother> userAnothers = new HashSet<>();
    private final Map<String, String> nickEmail = new HashMap<>();


    @GetMapping
    public Set<UserAnother> findAll() {
        return userAnothers;
    }

    @PostMapping
    public UserAnother createUser(@RequestBody UserAnother userAnother) throws InvalidEmailException, UserAlreadyExistException {
        checkExistenceOFThisUser(userAnother);
        checkEmail(userAnother);
        userAnothers.add(userAnother);
        nickEmail.put(userAnother.getNickname(), userAnother.getEmail());
        return userAnother;
    }

    @PutMapping
    public UserAnother updateUser(@RequestBody UserAnother userAnother) throws InvalidEmailException {
        checkEmail(userAnother);
        return userAnother;
    }

    public void checkExistenceOFThisUser(UserAnother userAnother) throws UserAlreadyExistException {
        if (userAnothers.contains(userAnother)) {
            throw new UserAlreadyExistException("Пользователь уже существует");
        } else {
            userAnothers.add(userAnother);
        }
    }

    public void checkEmail(UserAnother userAnother) throws InvalidEmailException {
        if (userAnother.getEmail() == null) {
            throw new InvalidEmailException("Поле \"email\" не заполнено");
        }
    }

    public Set<UserAnother> getUsers() {
        return userAnothers;
    }

    public Map<String, String> getNickEmail() {
        return nickEmail;
    }
}
