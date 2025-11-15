package com.example.userdemo.service;

import com.example.userdemo.entity.User;
import java.util.List;

public interface UserService {

    User addUser(User user);

    List<User> listUsers();

    void deleteUser(Long id);
}
