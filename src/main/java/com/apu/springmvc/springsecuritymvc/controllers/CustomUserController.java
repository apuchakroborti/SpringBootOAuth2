package com.apu.springmvc.springsecuritymvc.controllers;

import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.ServiceResponse;
import com.apu.springmvc.springsecuritymvc.models.CustomUserDto;
import com.apu.springmvc.springsecuritymvc.models.UserSearchCriteria;
import com.apu.springmvc.springsecuritymvc.models.request.PasswordChangeRequestDto;
import com.apu.springmvc.springsecuritymvc.models.request.PasswordResetRequestDto;
import com.apu.springmvc.springsecuritymvc.services.CustomUserService;
import com.apu.springmvc.springsecuritymvc.services.UserService;
import com.apu.springmvc.springsecuritymvc.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class CustomUserController {
    private final UserService userService;
    private final CustomUserService customUserService;

    @Autowired
    CustomUserController(UserService userService, CustomUserService customUserService){
        this.userService = userService;
        this.customUserService = customUserService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ServiceResponse addUser(@RequestBody CustomUserDto customUserDto) throws GenericException {
        return new ServiceResponse(null, customUserService.addUser(customUserDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping()
    public ServiceResponse getUserList(UserSearchCriteria criteria, @PageableDefault(value = 10) Pageable pageable) throws GenericException {
        return Utils.pageToServiceResponse(customUserService.getUserList(criteria, pageable), CustomUserDto.class);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(path = "/{id}")
    public ServiceResponse getUserById(@PathVariable(name = "id") Long id ) throws GenericException {
        return new ServiceResponse<>(null, customUserService.findUserById(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ServiceResponse updateUserById(@PathVariable(name = "id") Long id, @RequestBody CustomUserDto employeeBean) throws GenericException {
        return new ServiceResponse(null, customUserService.updateUserById(id, employeeBean));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ServiceResponse deleteUserById(@PathVariable(name = "id") Long id) throws GenericException {
        return new ServiceResponse(null, customUserService.deleteUserById(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/update-password")
    public ServiceResponse updatePassword(@RequestBody PasswordChangeRequestDto passwordChangeRequestDto) throws GenericException {
        return new ServiceResponse(null, userService.changeUserPassword(passwordChangeRequestDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/reset-password")
    public ServiceResponse resetPassword(@RequestBody PasswordResetRequestDto passwordResetRequestDto) throws GenericException {
        return new ServiceResponse(null, userService.resetPassword(passwordResetRequestDto));
    }
}
