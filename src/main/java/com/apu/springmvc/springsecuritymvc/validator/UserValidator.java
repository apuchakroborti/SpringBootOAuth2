package com.apu.springmvc.springsecuritymvc.validator;


import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.CustomUserDto;
import com.apu.springmvc.springsecuritymvc.services.CustomUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private static final Logger logger= LoggerFactory.getLogger(UserValidator.class);

    @Autowired
    private CustomUserService customUserService;

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomUserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CustomUserDto customUserDto = (CustomUserDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (customUserDto.getUsername().length() < 6 || customUserDto.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        CustomUserDto checkExistingUser = null;
        try{
            checkExistingUser = customUserService.findByUsername(customUserDto.getUsername());
        }catch (GenericException e){
            logger.error("Exception occurred while fetching user by username: {}", customUserDto.getUsername());
            e.printStackTrace();
        }
        if ( checkExistingUser!= null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (customUserDto.getPassword().length() < 8 || customUserDto.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!customUserDto.getPassword().equals(customUserDto.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}