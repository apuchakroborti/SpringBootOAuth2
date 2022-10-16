package com.apu.springmvc.springsecuritymvc.services;


import com.apu.springmvc.springsecuritymvc.entity.User;
import com.apu.springmvc.springsecuritymvc.models.UserBean;
import com.apu.springmvc.springsecuritymvc.repository.RoleRepository;
import com.apu.springmvc.springsecuritymvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(UserBean userBean) {
        //TODO need to use model mapper for copying value from bean to entity
        User user = this.getUserEntity(userBean);

        user.setPassword(bCryptPasswordEncoder.encode(userBean.getPassword()));
        user.setUserRoleId(1L);
        user.setStatus(true);

        userRepository.save(user);
    }

    @Override
    public UserBean findByUsername(String username) {

        User user = userRepository.findByUsername(username);
        if(user==null)return null;
        return this.getUserBean(user);
    }

    private User getUserEntity(UserBean userBean){
        User user = new User();

        if(userBean.getFirstName()!=null){
            user.setFirstName(userBean.getFirstName());
        }
        if(userBean.getLastName()!=null){
            user.setLastName(userBean.getLastName());
        }
        user.setUsername(userBean.getUsername()!=null?userBean.getUsername():null);
        user.setEmail(userBean.getEmail()!=null?userBean.getEmail(): null);
//        user.setUserRoleId(userBean.getUserRoleId());
        user.setPhone(userBean.getPhone()!=null?userBean.getPhone():null);
        if(userBean.getDateOfBirth()!=null){
            user.setDateOfBirth(userBean.getDateOfBirth());
        }
        user.setAddressId(userBean.getAddressId()!=null?userBean.getAddressId():null);
        user.setStatus(userBean.getStatus()!=null?userBean.getStatus():null);
        return user;
    }
    private UserBean getUserBean(User user){
        UserBean userBean = new UserBean();

        if(user.getFirstName()!=null){
            userBean.setFirstName(user.getFirstName());
        }
        if(user.getLastName()!=null){
            userBean.setLastName(user.getLastName());
        }
        if(user.getUsername()!=null){
            userBean.setUsername(user.getUsername());
        }
        if(user.getEmail()!=null){
            userBean.setEmail(user.getEmail());
        }
        if(user.getPhone()!=null){
            userBean.setPhone(user.getPhone());
        }
        if(user.getDateOfBirth()!=null){
            userBean.setDateOfBirth(user.getDateOfBirth());
        }
//        userBean.setAddressId(user.getAddressId());
//        userBean.setStatus(user.getStatus());
        return userBean;
    }
}