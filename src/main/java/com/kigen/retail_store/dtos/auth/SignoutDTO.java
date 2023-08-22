package com.kigen.retail_store.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignoutDTO {
    
    @NotBlank
    private String token;

    @NotNull
    private Integer userId;
}
