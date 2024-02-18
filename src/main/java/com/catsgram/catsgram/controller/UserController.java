package com.catsgram.catsgram.controller;

import com.catsgram.catsgram.exception.InvalidEmailException;
import com.catsgram.catsgram.exception.UserAlreadyExistException;
import com.catsgram.catsgram.exception.UserNotFoundException;
import com.catsgram.catsgram.model.UserAnother;
import com.catsgram.catsgram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public Set<UserAnother> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public UserAnother createUser(@RequestBody UserAnother userAnother) throws InvalidEmailException, UserAlreadyExistException {
        return userService.createUser(userAnother);
    }

    @PutMapping
    public UserAnother updateUser(@RequestBody UserAnother userAnother) throws InvalidEmailException {
        return userService.updateUser(userAnother);
    }

    @GetMapping(value = "/{email}")
    public Optional<UserAnother> findByEmail(@PathVariable String email){
        Optional<UserAnother> result = userService.getUsers().stream()
                .filter(x -> x.getEmail().equals(email))
                .findFirst();
        if(result.isEmpty()){
            throw new UserNotFoundException("Пользователя с такой почтой не существует");
        }
        return result;
    }


}
