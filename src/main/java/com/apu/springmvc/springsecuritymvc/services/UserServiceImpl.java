package com.apu.springmvc.springsecuritymvc.services;


import com.apu.springmvc.springsecuritymvc.entity.User;
import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.UserBean;
import com.apu.springmvc.springsecuritymvc.models.UserSearchCriteria;
import com.apu.springmvc.springsecuritymvc.repository.RoleRepository;
import com.apu.springmvc.springsecuritymvc.repository.UserRepository;
import com.apu.springmvc.springsecuritymvc.specifications.UserSearchSpecifications;
import com.apu.springmvc.springsecuritymvc.util.Defs;
import com.apu.springmvc.springsecuritymvc.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserBean save(UserBean userBean) throws GenericException {
        //TODO need to use model mapper for copying value from bean to entity
        User user = new User();
        Utils.copyProperty(userBean, user);

        user.setPassword(bCryptPasswordEncoder.encode(userBean.getPassword()));
        System.out.println("encrypted Password: "+bCryptPasswordEncoder.encode(userBean.getPassword()));
        user.setUserRoleId(1L);
        user.setStatus(true);

        user = userRepository.save(user);
        Utils.copyProperty(user, userBean);

        return userBean;
    }

    @Override
    public UserBean findByUsername(String username) throws GenericException{

        User user = userRepository.findByUsername(username);
        if(user==null)return null;
        UserBean userBean = new UserBean();
        Utils.copyProperty(user, userBean);
        return userBean;
    }
    @Override
    public UserBean findUserById(Long id) throws GenericException{
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            UserBean userBean = new UserBean();
            Utils.copyProperty(optionalUser.get(), userBean);
            return userBean;
        }
        throw new GenericException(Defs.USER_NOT_FOUND);
    }

    @Override
    public UserBean updateUserById(Long id, UserBean userBean) throws GenericException{
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()) throw new GenericException(Defs.USER_NOT_FOUND);
        return null;
    }

    @Override
    public Page<User> getUserList(UserSearchCriteria criteria, @PageableDefault(value = 10)Pageable pageable) throws GenericException{
        Page<User> userPage = userRepository.findAll(
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
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()) throw new GenericException(Defs.USER_NOT_FOUND);

        return true;
    }

}