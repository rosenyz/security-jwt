package com.roseny.securityjwt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
