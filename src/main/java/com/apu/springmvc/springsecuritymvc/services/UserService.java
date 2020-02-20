package com.apu.springmvc.springsecuritymvc.services;


import com.apu.springmvc.springsecuritymvc.dto.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}