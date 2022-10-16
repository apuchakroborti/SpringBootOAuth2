package com.apu.springmvc.springsecuritymvc.models;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserBean implements Serializable {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;
    private String passwordConfirm;

    private Long userRoleId;

    private String phone;

    private LocalDateTime dateOfBirth;

    private Integer addressId;

    private Boolean status;
}
