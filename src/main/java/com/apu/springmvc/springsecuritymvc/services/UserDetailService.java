package com.apu.springmvc.springsecuritymvc.services;


import com.apu.springmvc.springsecuritymvc.entity.CustomUser;
import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.request.PasswordChangeRequestDto;
import com.apu.springmvc.springsecuritymvc.models.request.PasswordResetRequestDto;
import com.apu.springmvc.springsecuritymvc.models.response.PasswordChangeResponseDto;
import com.apu.springmvc.springsecuritymvc.repository.AuthorityRepository;
import com.apu.springmvc.springsecuritymvc.repository.CustomUserRepository;
import com.apu.springmvc.springsecuritymvc.repository.UserRepository;
import com.apu.springmvc.springsecuritymvc.util.Defs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apu.springmvc.springsecuritymvc.models.security.User;
import java.util.Optional;


@Service
public class UserDetailService implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserRepository customUserRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

   @Autowired
   @Qualifier("userPasswordEncoder")
   private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        System.out.println("loadUserByUsername called");
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isPresent()){
            if(!optionalUser.get().isEnabled()){
                throw new UsernameNotFoundException(Defs.USER_INACTIVE+": "+username);
            }
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(Defs.USER_NOT_FOUND+": "+username);
    }

    @Override
    public PasswordChangeResponseDto changeUserPassword(PasswordChangeRequestDto passwordChangeRequestDto) throws GenericException {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = currentUser.getUsername();

        UserDetails userDetails = loadUserByUsername(currentUsername);
        String currentPassword = userDetails.getPassword();
        User user = (User) userDetails;

        if(passwordEncoder.matches(passwordChangeRequestDto.getCurrentPassword(), currentPassword)) {
            user.setPassword(passwordEncoder.encode(passwordChangeRequestDto.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new GenericException(Defs.PASSWORD_MISMATCHED);
        }

        return new PasswordChangeResponseDto(true, Defs.PASSWORD_CHANGED_SUCCESSFUL);
    }

    @Override
    public PasswordChangeResponseDto resetPassword(PasswordResetRequestDto passwordResetRequestDto) throws GenericException{

        CustomUser employee = customUserRepository.findByEmail(passwordResetRequestDto.getUsername());
        if(employee==null || employee.getStatus().equals(false))throw new GenericException(Defs.USER_NOT_FOUND);

        UserDetails userDetails = loadUserByUsername(passwordResetRequestDto.getUsername());
        User user = (User) userDetails;

        user.setPassword(passwordEncoder.encode("apu12345"));
        userRepository.save(user);

        return new PasswordChangeResponseDto(true, Defs.PASSWORD_CHANGED_SUCCESSFUL+": the new Password is: apu12345");
    }
}