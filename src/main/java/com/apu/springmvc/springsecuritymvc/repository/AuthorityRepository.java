package com.apu.springmvc.springsecuritymvc.repository;


import com.apu.springmvc.springsecuritymvc.models.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}