package com.apu.springmvc.springsecuritymvc.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchCriteria {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String phone;
    private String email;
}
