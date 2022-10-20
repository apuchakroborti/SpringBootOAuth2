package com.apu.springmvc.springsecuritymvc.security;


import com.apu.springmvc.springsecuritymvc.entity.User;
import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.EmployeeBean;
import com.apu.springmvc.springsecuritymvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UserService userService;


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();

        EmployeeBean loginInfoDto = null;
        try {
            loginInfoDto = userService.findByUsername(user.getUsername());
        } catch (GenericException e) {
            e.printStackTrace();
        }
        if(loginInfoDto != null) {
            additionalInfo.put("id", loginInfoDto.getId());
        }

        additionalInfo.put("authorities", user.getRole().getRoleName());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }

}