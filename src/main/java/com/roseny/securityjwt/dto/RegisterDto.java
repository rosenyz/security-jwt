package com.roseny.securityjwt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;
}
