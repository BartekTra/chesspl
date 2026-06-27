package com.example.chesspl.service.auth;

import com.example.chesspl.controller.auth.dto.RegisterRequest;
import com.example.chesspl.controller.auth.dto.RegisterResponse;
import com.example.chesspl.controller.user.DefaultUserMapper;
import com.example.chesspl.exception.UserAlreadyExistsException;
import com.example.chesspl.model.User;
import com.example.chesspl.repository.user.UserRepository;
import com.example.chesspl.security.JwtProviderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthRegisterServiceImplTest {

    // should throw UserAlreadyExistsException when Username is already taken - todo
    // should throw UserAlreadyExistsException when Email is already taken - todo
    // should save user to db and return it with generated token to user as json

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProviderImpl jwtProvider;

    @Mock
    private DefaultUserMapper defaultUserMapper;

    @InjectMocks
    private AuthRegisterServiceImpl authRegisterServiceImpl;

    private RegisterRequest request;
    @BeforeEach
    void setUp() {
        request = new RegisterRequest();
        request.setUsername("TestowyUzytkownik");
        request.setAge(16);
        request.setEmail("bartektrap@trapinsky.com");
        request.setDisplayName("DisplayTestName");
        request.setPassword("TestoweHaslo");
    }

    @Test
    void register_ShouldThrowUsernameException_WhenUsernameAlreadyExists() {
        // when && then
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> authRegisterServiceImpl.register(request)
        );

        assertEquals("error.auth.register.username_exists", exception.getMessage());
    }

    @Test
    void register_ShouldThrowEmailException_WhenEmailAlreadyExists() {
        // when && then
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> authRegisterServiceImpl.register(request)
        );

        assertEquals("error.auth.register.email_exists", exception.getMessage());
    }

    @Test
    void register_ShouldSaveUserAndReturnItWithToken_WhenRegistrationSuccessful() {
        User unmappedUser = new User();

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername(request.getUsername());
        savedUser.setPassword(request.getPassword());
        savedUser.setEmail(request.getEmail());
        savedUser.setAge(request.getAge());
        savedUser.setDisplayName(request.getDisplayName());

        String mockToken = "super.tajny.token.jwt";
        RegisterResponse expectedResponse = new RegisterResponse(
                1L,
                request.getUsername(),
                request.getDisplayName(),
                request.getAge(),
                request.getEmail(),
                mockToken
                );

        when(defaultUserMapper.toEntity(request)).thenReturn(unmappedUser);

        when(userRepository.save(unmappedUser)).thenReturn(savedUser);

        when(jwtProvider.generateToken(request.getUsername())).thenReturn(mockToken);

        when(defaultUserMapper.toResponse(savedUser, mockToken)).thenReturn(expectedResponse);

        RegisterResponse actualResponse = authRegisterServiceImpl.register(request);

        assertEquals(expectedResponse, actualResponse);

        verify(userRepository).save(unmappedUser);
    }
}