package com.apu.springmvc.springsecuritymvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role extends EntityCommon{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "role_name", nullable = false, length = 50)
        private String roleName;

        @Column(name = "status", nullable = false, length = 15)
        private String status;

        @Column(name = "description", nullable = false, length = 50)
        private String description;
}

