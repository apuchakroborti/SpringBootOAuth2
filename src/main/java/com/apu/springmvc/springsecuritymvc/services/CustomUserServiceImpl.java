package com.apu.springmvc.springsecuritymvc.services;

import com.apu.springmvc.springsecuritymvc.entity.CustomUser;
import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.CustomUserDto;
import com.apu.springmvc.springsecuritymvc.models.UserSearchCriteria;
import com.apu.springmvc.springsecuritymvc.models.security.Authority;
import com.apu.springmvc.springsecuritymvc.models.security.User;
import com.apu.springmvc.springsecuritymvc.repository.AuthorityRepository;
import com.apu.springmvc.springsecuritymvc.repository.CustomUserRepository;
import com.apu.springmvc.springsecuritymvc.repository.UserRepository;
import com.apu.springmvc.springsecuritymvc.specifications.UserSearchSpecifications;
import com.apu.springmvc.springsecuritymvc.util.Defs;
import com.apu.springmvc.springsecuritymvc.util.Role;
import com.apu.springmvc.springsecuritymvc.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CustomUserServiceImpl implements CustomUserService{

    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    @Qualifier("userPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    private User addOauthUser(CustomUser customUser, String password) throws GenericException{
        Optional<User> optionalUser = userRepository.findByUsername(customUser.getEmail());
        if(optionalUser.isPresent())throw new GenericException(Defs.USER_ALREADY_EXISTS);

        User user = new User();
        user.setUsername(customUser.getEmail());
        user.setEnabled(true);

        Authority authority = authorityRepository.findByName(Role.USER.getValue());
        user.setAuthorities(Arrays.asList(authority));
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        return user;
    }
    @Override
    public CustomUserDto addUser(CustomUserDto customUserDto) throws GenericException {
        Optional<CustomUser> optionalCustomUser = customUserRepository.findByUserId(customUserDto.getUserId());
        if(optionalCustomUser.isPresent())throw new GenericException(Defs.USER_ALREADY_EXISTS);

        CustomUser customUser = new CustomUser();
        Utils.copyProperty(customUserDto, customUser);
        User user = addOauthUser(customUser, customUserDto.getPassword());
        customUser.setOauthUser(user);
        customUser.setStatus(true);

        customUser.setCreatedBy(1l);
        customUser.setCreateTime(LocalDateTime.now());

        customUser = customUserRepository.save(customUser);

        Utils.copyProperty(customUser, customUserDto);

        return customUserDto;
    }

    @Override
    public CustomUserDto findByUsername(String username) throws GenericException{

        CustomUser user = customUserRepository.findByEmail(username);
        if(user==null)return null;
        CustomUserDto customUserDto = new CustomUserDto();
        Utils.copyProperty(user, customUserDto);
        return customUserDto;
    }
    @Override
    public CustomUserDto findUserById(Long id) throws GenericException{
        Optional<CustomUser> optionalUser = customUserRepository.findById(id);

        if(optionalUser.isPresent()){
            CustomUserDto employeeBean = new CustomUserDto();
            Utils.copyProperty(optionalUser.get(), employeeBean);
            return employeeBean;
        }
        throw new GenericException(Defs.USER_NOT_FOUND);
    }

    @Override
    public CustomUserDto updateUserById(Long id, CustomUserDto employeeBean) throws GenericException{
        Optional<CustomUser> optionalUser = customUserRepository.findById(id);
        if(!optionalUser.isPresent()) throw new GenericException(Defs.USER_NOT_FOUND);
        return null;
    }

    @Override
    public Page<CustomUser> getUserList(UserSearchCriteria criteria, @PageableDefault(value = 10) Pageable pageable) throws GenericException{
        Page<CustomUser> userPage = customUserRepository.findAll(
                UserSearchSpecifications.withId(criteria.getId())
                        .and(UserSearchSpecifications.withFirstName(criteria.getFirstName()))
                        .and(UserSearchSpecifications.withLastName(criteria.getLastName()))
                        .and(UserSearchSpecifications.withUsername(criteria.getUsername()))
                        .and(UserSearchSpecifications.withEmail(criteria.getEmail()))
                        .and(UserSearchSpecifications.withPhone(criteria.getPhone()))
                ,pageable
        );
        return null;
    }

    @Override
    public Boolean deleteUserById(Long id) throws GenericException{
        Optional<CustomUser> optionalUser = customUserRepository.findById(id);
        if(!optionalUser.isPresent()) throw new GenericException(Defs.USER_NOT_FOUND);

        return true;
    }
}
