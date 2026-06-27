package com.example.chesspl.user;

import com.example.chesspl.auth.dto.RegisterRequest;
import com.example.chesspl.auth.dto.RegisterResponse;

public interface UserMapper {
    User toEntity(RegisterRequest request);

    RegisterResponse toResponse(User user, String token);
}
