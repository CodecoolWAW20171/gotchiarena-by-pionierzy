package com.codecool.pionierzy.gotchiarena.service;

import com.codecool.pionierzy.gotchiarena.model.User;

public interface UserService {

    User findByUsername(String username);

    void save(User user);
}
