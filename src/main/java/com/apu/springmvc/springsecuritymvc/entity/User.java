package com.apu.springmvc.springsecuritymvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends EntityCommon implements Serializable {

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

    @Column(name = "user_role_id", nullable = false)
    private Long userRoleId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "address_id")
    private Integer addressId;

    private Boolean status;
}

/*
INSERT INTO public.users_actr(
	id, first_name, last_name, username, email, encrypted_password, user_id, user_role_id, organization_id, domain_name, logged_out_at, status, created_by, create_time, edited_by, edit_time, internal_version)
	VALUES (1, 'Apu1', 'chak', 'user1@gmail.com', 'user1@gmail.com', '12345678', 'Apu1', 1, 1, 'tigerit.com', null, 'ACTIVE', 'user1@gmail.com', '2020-05-10 19:41:22.787147+06', null, null, 0);


	INSERT INTO public.users_actr(
	id, first_name, last_name, username, email, encrypted_password, user_id, user_role_id, organization_id, domain_name, logged_out_at, status, created_by, create_time, edited_by, edit_time, internal_version)
	VALUES (2, 'Apu2', 'chak', 'user2@gmail.com', 'user2@gmail.com', '12345678', 'Apu2', 2, 1, 'tigerit.com', null, 'ACTIVE', 'user2@gmail.com', '2020-05-10 19:41:22.787147+06', null, null, 0);


	INSERT INTO public.users_actr(
	id, first_name, last_name, username, email, encrypted_password, user_id, user_role_id, organization_id, domain_name, logged_out_at, status, created_by, create_time, edited_by, edit_time, internal_version)
	VALUES (3, 'Apu3', 'chak', 'user3@gmail.com', 'user3@gmail.com', '12345678', 'Apu3', 3, 1, 'tigerit.com', null, 'ACTIVE', 'user3@gmail.com', '2020-05-10 19:41:22.787147+06', null, null, 0);


	INSERT INTO public.users_actr(
	id, first_name, last_name, username, email, encrypted_password, user_id, user_role_id, organization_id, domain_name, logged_out_at, status, created_by, create_time, edited_by, edit_time, internal_version)
	VALUES (4, 'Apu4', 'chak', 'user4@gmail.com', 'user4@gmail.com', '12345678', 'Apu4', 4, 1, 'tigerit.com', null, 'ACTIVE', 'user4@gmail.com', '2020-05-10 19:41:22.787147+06', null, null, 0);


    UPDATE USERs_ACTR SET ENCRYPTED_PASSWORD= '$2a$10$PjYTdj.e0eZu0hhSbVqxZeJM9o.d6NO0TxMAATFZggWVokJLuiMLm';
	*/