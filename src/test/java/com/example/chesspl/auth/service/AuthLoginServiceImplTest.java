package com.example.chesspl.auth.service;

import com.example.chesspl.auth.dto.LoginRequest;
import com.example.chesspl.auth.dto.LoginResponse;
import com.example.chesspl.security.TokenProvider;
import com.example.chesspl.user.UserRepository;
import com.example.chesspl.security.JwtProviderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthLoginServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthLoginServiceImpl authLoginServiceImpl;

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testUser");
        request.setPassword("correctPassword");

        String expectedToken = "fake-jwt-token-123";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        when(tokenProvider.generateToken("testUser")).thenReturn(expectedToken);

        LoginResponse response = authLoginServiceImpl.login(request);

        assertNotNull(response, "Response shouldnt be null");
        assertEquals(expectedToken, response.getToken(), "token in response must be the same as generated one");

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider, times(1)).generateToken("testUser");
    }

    @Test
    void login_ShouldThrowException_WhenCredentialsAreInvalid() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testUser");
        request.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("wrong login or password"));

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> authLoginServiceImpl.login(request)
        );

        assertEquals("wrong login or password", exception.getMessage());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider, never()).generateToken(anyString());
    }
}