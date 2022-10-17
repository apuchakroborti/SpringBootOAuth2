package com.apu.springmvc.springsecuritymvc.controllers;

import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.ServiceResponse;
import com.apu.springmvc.springsecuritymvc.models.UserBean;
import com.apu.springmvc.springsecuritymvc.models.UserSearchCriteria;
import com.apu.springmvc.springsecuritymvc.services.UserService;
import com.apu.springmvc.springsecuritymvc.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserServiceController {
    private final UserService userService;

    @Autowired
    UserServiceController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ServiceResponse saveUser(UserBean userBean) throws GenericException {
        return new ServiceResponse(null, userService.save(userBean));
    }

    @GetMapping()
    public ServiceResponse getUserList(UserSearchCriteria criteria, @PageableDefault(value = 10) Pageable pageable) throws GenericException {
        return Utils.pageToServiceResponse(userService.getUserList(criteria, pageable), UserBean.class);
    }

    @GetMapping(path = "/{id}")
    public ServiceResponse getUserById(@PathVariable(name = "id") Long id ) throws GenericException {
        return new ServiceResponse<>(null, userService.findUserById(id));
    }

    @PutMapping("/{id}")
    public ServiceResponse updateUserById(@PathVariable(name = "id") Long id, UserBean userBean) throws GenericException {
        return new ServiceResponse(null, userService.updateUserById(id, userBean));
    }

    @DeleteMapping("/{id}")
    public ServiceResponse deleteUserById(@PathVariable(name = "id") Long id) throws GenericException {
        return new ServiceResponse(null, userService.deleteUserById(id));
    }
}
