package com.example.chesspl.service.auth;

import com.example.chesspl.controller.auth.dto.RegisterRequest;
import com.example.chesspl.controller.auth.dto.RegisterResponse;
import com.example.chesspl.controller.user.UserMapper;
import com.example.chesspl.exception.UserAlreadyExistsException;
import com.example.chesspl.repository.user.UserRepository;
import com.example.chesspl.security.JwtProviderImpl;
import com.example.chesspl.security.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthRegisterServiceImpl {

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
