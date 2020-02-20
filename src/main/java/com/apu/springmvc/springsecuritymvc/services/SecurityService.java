package com.apu.springmvc.springsecuritymvc.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
