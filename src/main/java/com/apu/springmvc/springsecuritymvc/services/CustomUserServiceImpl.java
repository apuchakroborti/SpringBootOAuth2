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
        if(user==null || user.getStatus().equals(false))return null;
        CustomUserDto customUserDto = new CustomUserDto();
        Utils.copyProperty(user, customUserDto);
        return customUserDto;
    }
    @Override
    public CustomUserDto findUserById(Long id) throws GenericException{
        Optional<CustomUser> optionalUser = customUserRepository.findById(id);

        if(!optionalUser.isPresent() || optionalUser.get().getStatus().equals(false)){
            throw new GenericException(Defs.USER_NOT_FOUND);
        }else{
            CustomUserDto employeeBean = new CustomUserDto();
            Utils.copyProperty(optionalUser.get(), employeeBean);
            return employeeBean;
        }
    }

    @Override
    public CustomUserDto updateUserById(Long id, CustomUserDto customUserDto) throws GenericException{
        Optional<CustomUser> loggedInCustomUser = customUserRepository.getLoggedInCustomUser();
        if(loggedInCustomUser.isPresent() && !loggedInCustomUser.get().getId().equals(id)){
            throw new GenericException(Defs.NO_PERMISSION_TO_UPDATE);
        }

        Optional<CustomUser> optionalUser = customUserRepository.findById(id);
        if(!optionalUser.isPresent() || optionalUser.get().getStatus().equals(false)) throw new GenericException(Defs.USER_NOT_FOUND);

        CustomUser customUser = optionalUser.get();
        if(!Utils.isNullOrEmpty(customUserDto.getFirstName())){
            customUser.setFirstName(customUserDto.getFirstName());
        }
        if(!Utils.isNullOrEmpty(customUserDto.getLastName())){
            customUser.setLastName(customUserDto.getLastName());
        }
        customUser = customUserRepository.save(customUser);

        Utils.copyProperty(customUser, customUserDto);
        return customUserDto;
    }

    @Override
    public Page<CustomUser> getUserList(UserSearchCriteria criteria, @PageableDefault(value = 10) Pageable pageable) throws GenericException{
        Optional<CustomUser> loggedInCustomUser = customUserRepository.getLoggedInCustomUser();
        Long id = null;
        if(loggedInCustomUser.isPresent()){
            id = loggedInCustomUser.get().getId();
        }

        Page<CustomUser> userPage = customUserRepository.findAll(
                UserSearchSpecifications.withId(id==null ? criteria.getId() : id)
                        .and(UserSearchSpecifications.withFirstName(criteria.getFirstName()))
                        .and(UserSearchSpecifications.withLastName(criteria.getLastName()))
                        .and(UserSearchSpecifications.withEmail(criteria.getEmail()))
                        .and(UserSearchSpecifications.withPhone(criteria.getPhone()))
                        .and(UserSearchSpecifications.withStatus(true))
                ,pageable
        );
        return userPage;
    }

    @Override
    public Boolean deleteUserById(Long id) throws GenericException{
        Optional<CustomUser> loggedInCustomUser = customUserRepository.getLoggedInCustomUser();
        Optional<CustomUser> optionalUser = customUserRepository.findById(id);
        if(loggedInCustomUser.isPresent() && optionalUser.isPresent() && !loggedInCustomUser.get().getId().equals(optionalUser.get().getId())){
            throw new GenericException(Defs.NO_PERMISSION_TO_DELETE);
        }
        if(!optionalUser.isPresent()) throw new GenericException(Defs.USER_NOT_FOUND);

        CustomUser customUser = optionalUser.get();
        customUser.setStatus(false);
        try {
            customUser = customUserRepository.save(customUser);
            User user = customUser.getOauthUser();
            user.setEnabled(false);
            userRepository.save(user);
        }catch (Exception e){
            throw new GenericException(Defs.EXCEPTION_OCCURRED_WHILE_SAVING_USER_INFO);
        }
        return true;
    }
}
