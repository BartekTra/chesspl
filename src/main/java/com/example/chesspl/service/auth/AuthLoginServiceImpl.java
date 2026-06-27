package com.example.chesspl.service.auth;

import com.example.chesspl.controller.auth.dto.LoginRequest;
import com.example.chesspl.controller.auth.dto.LoginResponse;
import com.example.chesspl.repository.user.UserRepository;
import com.example.chesspl.security.JwtProviderImpl;
import com.example.chesspl.security.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthLoginServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), passwordEncoder.encode(request.getPassword()))
        );
        return new LoginResponse(tokenProvider.generateToken(request.getUsername()));
    }
}
