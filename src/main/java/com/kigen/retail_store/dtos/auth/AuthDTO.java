package com.kigen.retail_store.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDTO {
    
    @NotBlank
    private String userContact;

    @NotBlank
    private String password;
}
