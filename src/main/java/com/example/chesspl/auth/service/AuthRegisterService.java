package com.example.chesspl.auth.service;

import com.example.chesspl.auth.dto.RegisterRequest;
import com.example.chesspl.auth.dto.RegisterResponse;

public interface AuthRegisterService {
    public RegisterResponse register(RegisterRequest request);
}
