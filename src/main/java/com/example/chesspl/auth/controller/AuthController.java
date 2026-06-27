package com.example.chesspl.auth.controller;

import com.example.chesspl.auth.dto.LoginRequest;
import com.example.chesspl.auth.dto.RegisterRequest;
import com.example.chesspl.auth.dto.RegisterResponse;
import com.example.chesspl.auth.service.AuthLoginService;
import com.example.chesspl.auth.service.AuthRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthLoginService authLoginService;
    private final AuthRegisterService authRegisterService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid
            @RequestBody
            LoginRequest request
    ) {
        return ResponseEntity.ok(authLoginService.login(request));
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid
            @RequestBody
            RegisterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authRegisterService.register(request));
    }
}