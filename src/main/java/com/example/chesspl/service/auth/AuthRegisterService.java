package com.example.chesspl.service.auth;

import com.example.chesspl.controller.auth.dto.RegisterRequest;
import com.example.chesspl.controller.auth.dto.RegisterResponse;

public interface AuthRegisterService {
    public RegisterResponse register(RegisterRequest request);
}
