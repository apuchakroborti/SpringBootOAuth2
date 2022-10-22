package com.apu.springmvc.springsecuritymvc.repository;


import com.apu.springmvc.springsecuritymvc.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long>, JpaSpecificationExecutor<CustomUser> {
    CustomUser findByEmail(String email);
    Optional<CustomUser> findByUserId(String userId);

    @Query("select  cu from CustomUser cu where cu.oauthUser.id = ?#{principal.id}")
    Optional<CustomUser> getLoggedInCustomUser();
}