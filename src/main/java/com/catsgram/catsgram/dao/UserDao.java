package com.catsgram.catsgram.dao;

import com.catsgram.catsgram.model.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findUserById(String id);

}
