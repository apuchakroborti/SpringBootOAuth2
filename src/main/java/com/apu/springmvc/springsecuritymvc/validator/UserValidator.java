package com.apu.springmvc.springsecuritymvc.validator;


import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.EmployeeBean;
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
        return EmployeeBean.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EmployeeBean employeeBean = (EmployeeBean) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (employeeBean.getUsername().length() < 6 || employeeBean.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        EmployeeBean checkExistingUser = null;
        try{
            checkExistingUser = userService.findByUsername(employeeBean.getUsername());
        }catch (GenericException e){
            logger.error("Exception occurred while fetching user by username: {}", employeeBean.getUsername());
            e.printStackTrace();
        }
        if ( checkExistingUser!= null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (employeeBean.getPassword().length() < 8 || employeeBean.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!employeeBean.getPasswordConfirm().equals(employeeBean.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}