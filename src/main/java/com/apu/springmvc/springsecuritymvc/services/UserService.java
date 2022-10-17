package com.apu.springmvc.springsecuritymvc.services;


import com.apu.springmvc.springsecuritymvc.entity.User;
import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.UserBean;
import com.apu.springmvc.springsecuritymvc.models.UserSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    UserBean save(UserBean user) throws GenericException;

    UserBean findByUsername(String username) throws GenericException;
    UserBean findUserById(Long id) throws GenericException;
    UserBean updateUserById(Long id, UserBean userBean) throws GenericException;
    Page<User> getUserList(UserSearchCriteria criteria, Pageable pageable) throws GenericException;
    Boolean deleteUserById(Long id) throws GenericException;
}