package com.apu.springmvc.springsecuritymvc.validator;


import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.UserBean;
import com.apu.springmvc.springsecuritymvc.services.UserService;
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
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserBean.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserBean userBean = (UserBean) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (userBean.getUsername().length() < 6 || userBean.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        UserBean checkExistingUser = null;
        try{
            checkExistingUser = userService.findByUsername(userBean.getUsername());
        }catch (GenericException e){
            logger.error("Exception occurred while fetching user by username: {}", userBean.getUsername());
            e.printStackTrace();
        }
        if ( checkExistingUser!= null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (userBean.getPassword().length() < 8 || userBean.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!userBean.getPasswordConfirm().equals(userBean.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}