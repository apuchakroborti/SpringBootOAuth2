package com.apu.springmvc.springsecuritymvc.services;


import com.apu.springmvc.springsecuritymvc.entity.Role;
import com.apu.springmvc.springsecuritymvc.entity.User;
import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.EmployeeBean;
import com.apu.springmvc.springsecuritymvc.models.UserSearchCriteria;
import com.apu.springmvc.springsecuritymvc.repository.RoleRepository;
import com.apu.springmvc.springsecuritymvc.repository.UserRepository;
import com.apu.springmvc.springsecuritymvc.specifications.UserSearchSpecifications;
import com.apu.springmvc.springsecuritymvc.util.Defs;
import com.apu.springmvc.springsecuritymvc.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserDetailService implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

   @Autowired
   @Qualifier("userPasswordEncoder")
   private PasswordEncoder passwordEncoder;

    @Override
    public EmployeeBean save(EmployeeBean employeeBean) throws GenericException {
        //TODO need to use model mapper for copying value from bean to entity
        User user = new User();
        Utils.copyProperty(employeeBean, user);

        user.setPassword(passwordEncoder.encode(employeeBean.getPassword()));
        System.out.println("encrypted Password: "+ passwordEncoder.encode(employeeBean.getPassword()));
        Optional<Role> optionalRole = roleRepository.findById(employeeBean.getUserRoleId());
        if(optionalRole.isPresent()){
            user.setRole(optionalRole.get());
        }
        user.setStatus(true);

        user = userRepository.save(user);
        Utils.copyProperty(user, employeeBean);

        return employeeBean;
    }

    @Override
    public EmployeeBean findByUsername(String username) throws GenericException{

        User user = userRepository.findByUsername(username);
        if(user==null)return null;
        EmployeeBean employeeBean = new EmployeeBean();
        Utils.copyProperty(user, employeeBean);
        return employeeBean;
    }
    @Override
    public EmployeeBean findUserById(Long id) throws GenericException{
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            EmployeeBean employeeBean = new EmployeeBean();
            Utils.copyProperty(optionalUser.get(), employeeBean);
            return employeeBean;
        }
        throw new GenericException(Defs.USER_NOT_FOUND);
    }

    @Override
    public EmployeeBean updateUserById(Long id, EmployeeBean employeeBean) throws GenericException{
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
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);

        Optional<Role> roleOptional = roleRepository.findById(user.getRole().getId());

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if(roleOptional.isPresent()){
            grantedAuthorities.add(new SimpleGrantedAuthority(roleOptional.get().getRoleName()));
        }
        /*for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }*/

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

}