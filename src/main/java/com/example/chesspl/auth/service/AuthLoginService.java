package com.example.chesspl.auth.service;


import com.example.chesspl.auth.dto.LoginRequest;
import com.example.chesspl.auth.dto.LoginResponse;

public interface AuthLoginService {
    public LoginResponse login(LoginRequest request);
}
