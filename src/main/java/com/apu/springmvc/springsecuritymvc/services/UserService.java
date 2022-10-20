package com.apu.springmvc.springsecuritymvc.services;


import com.apu.springmvc.springsecuritymvc.entity.User;
import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.EmployeeBean;
import com.apu.springmvc.springsecuritymvc.models.UserSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    EmployeeBean save(EmployeeBean user) throws GenericException;

    EmployeeBean findByUsername(String username) throws GenericException;
    EmployeeBean findUserById(Long id) throws GenericException;
    EmployeeBean updateUserById(Long id, EmployeeBean employeeBean) throws GenericException;
    Page<User> getUserList(UserSearchCriteria criteria, Pageable pageable) throws GenericException;
    Boolean deleteUserById(Long id) throws GenericException;
}