package com.apu.springmvc.springsecuritymvc.models.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@Table(name = "oauth_authority")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}

/*
insert into oauth_authority(id, name) values (1, "ADMIN");
insert into oauth_authority(id, name) values (2, "USER");
* */