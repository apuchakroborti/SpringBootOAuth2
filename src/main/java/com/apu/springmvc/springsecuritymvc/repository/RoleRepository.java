package com.apu.springmvc.springsecuritymvc.repository;


import com.apu.springmvc.springsecuritymvc.dto.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}