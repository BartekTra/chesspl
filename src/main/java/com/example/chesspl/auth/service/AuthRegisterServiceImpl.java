package com.example.chesspl.auth.service;

import com.example.chesspl.auth.dto.RegisterRequest;
import com.example.chesspl.auth.dto.RegisterResponse;
import com.example.chesspl.core.exception.UserAlreadyExistsException;
import com.example.chesspl.security.TokenProvider;
import com.example.chesspl.user.UserMapper;
import com.example.chesspl.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@AllArgsConstructor
public class AuthRegisterServiceImpl implements AuthRegisterService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final UserMapper userMapper;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        validateUserDoesNotExist(request);

        return userMapper.toResponse(
                userRepository.save(
                        userMapper.toEntity(request)),
                tokenProvider.generateToken(request.getUsername()
                )
        );
    }

    private void validateUserDoesNotExist(RegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("error.auth.register.username_exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("error.auth.register.email_exists");
        }
    }
}
