package com.apu.springmvc.springsecuritymvc.services;


import com.apu.springmvc.springsecuritymvc.exceptions.GenericException;
import com.apu.springmvc.springsecuritymvc.models.request.PasswordChangeRequestDto;
import com.apu.springmvc.springsecuritymvc.models.request.PasswordResetRequestDto;
import com.apu.springmvc.springsecuritymvc.models.response.PasswordChangeResponseDto;

public interface UserService {
    PasswordChangeResponseDto changeUserPassword(PasswordChangeRequestDto passwordChangeRequestDto) throws GenericException;
    PasswordChangeResponseDto resetPassword(PasswordResetRequestDto passwordResetRequestDto) throws GenericException;
}