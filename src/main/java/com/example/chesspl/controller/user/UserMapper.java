package com.example.chesspl.controller.user;

import com.example.chesspl.controller.auth.dto.RegisterRequest;
import com.example.chesspl.controller.auth.dto.RegisterResponse;
import com.example.chesspl.model.User;

public interface UserMapper {
    User toEntity(RegisterRequest request);

    RegisterResponse toResponse(User user, String token);
}
