package com.apu.springmvc.springsecuritymvc.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeResponseDto {
    private Boolean status;
    private String message;
}
