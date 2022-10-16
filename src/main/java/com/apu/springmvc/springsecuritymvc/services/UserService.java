package com.apu.springmvc.springsecuritymvc.services;


import com.apu.springmvc.springsecuritymvc.models.UserBean;

public interface UserService {
    void save(UserBean user);

    UserBean findByUsername(String username);
}