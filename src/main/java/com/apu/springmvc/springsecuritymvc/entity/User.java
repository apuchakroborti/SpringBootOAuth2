package com.apu.springmvc.springsecuritymvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends EntityCommon{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name",nullable = false, length = 50)
    private String lastName;

    @Column(name = "username",nullable = false, length = 120, unique = true)
    private String username;

    @Column(name = "email",nullable = false, length = 120, unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_role_id", nullable = false)
    private Role role;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "address_id")
    private Integer addressId;

    private Boolean status;
}