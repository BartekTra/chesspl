package com.example.chesspl.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "{validation.username.notblank}")
    @Email(message = "{validation.username.size}")
    private String email;

    @NotBlank(message = "{validation.password.notblank}")
    @Size(min = 8, message = "{validation.password.size}")
    private String password;
}