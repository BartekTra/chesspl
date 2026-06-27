package com.example.chesspl.service.auth;

import com.example.chesspl.controller.auth.dto.LoginRequest;
import com.example.chesspl.controller.auth.dto.LoginResponse;


public interface AuthLoginService {
    public LoginResponse login(LoginRequest request);
}
