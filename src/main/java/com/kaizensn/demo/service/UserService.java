package com.kaizensn.demo.service;

import com.kaizensn.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user);

    List<User> findAll();
}
