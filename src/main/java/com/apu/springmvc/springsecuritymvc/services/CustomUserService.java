package com.apu.springmvc.springsecuritymvc.services;

import com.apu.springmvc.springsecuritymvc.entity.CustomUser;
import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.CustomUserDto;
import com.apu.springmvc.springsecuritymvc.models.UserSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomUserService {
    CustomUserDto addUser(CustomUserDto user) throws GenericException;

    CustomUserDto findByUsername(String username) throws GenericException;
    CustomUserDto findUserById(Long id) throws GenericException;
    CustomUserDto updateUserById(Long id, CustomUserDto employeeBean) throws GenericException;
    Page<CustomUser> getUserList(UserSearchCriteria criteria, Pageable pageable) throws GenericException;
    Boolean deleteUserById(Long id) throws GenericException;
}
