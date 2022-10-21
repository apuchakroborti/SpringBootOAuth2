package com.apu.springmvc.springsecuritymvc.security;


import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.CustomUserDto;
import com.apu.springmvc.springsecuritymvc.models.security.User;
import com.apu.springmvc.springsecuritymvc.models.security.Authority;
import com.apu.springmvc.springsecuritymvc.services.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private CustomUserService customUserService;


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        System.out.println("CustomTokenEnhancer enhance called");
        User user = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();

        CustomUserDto loginInfoDto = null;
        try {
            loginInfoDto = customUserService.findByUsername(user.getUsername());
        } catch (GenericException e) {
            e.printStackTrace();
        }
        if(loginInfoDto != null) {
            additionalInfo.put("userId", loginInfoDto.getId());
        }

        additionalInfo.put("authorities", user.getAuthorities()
                .stream().map(Authority::getAuthority)
                .collect(Collectors.toList()));

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }

}