package org.invest.service;

import org.invest.dto.LoginUser;
import org.invest.dto.RegisterUserDto;
import org.invest.entity.Role;
import org.invest.entity.User;
import org.invest.entity.enum_status_model.RoleStatus;
import org.invest.exception.UserResours.NotFoundUserException;
import org.invest.exception.UserResours.UserDuplicatedException;
import org.invest.repository.RoleRepository;
import org.invest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private LoginUser testLoginUser;
    private RegisterUserDto testRegisterUserDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");

        testLoginUser = new LoginUser();
        testLoginUser.setEmail("test@example.com");
        testLoginUser.setPassword("password");

        testRegisterUserDto = new RegisterUserDto();
        testRegisterUserDto.setEmail("newuser@example.com");
        testRegisterUserDto.setName("New User");
        testRegisterUserDto.setPassword("newPassword");
    }

    @Test
    void testGetUserByUserAndPassword_Success() {
        when(userRepository.getUserByEmail(testLoginUser.getEmail())).thenReturn(testUser);
        when(passwordEncoder.matches(testLoginUser.getPassword(), testUser.getPassword())).thenReturn(true);

        User user = userService.getUserByUserAndPassword(testLoginUser);

        assertNotNull(user);
        assertEquals(testUser.getEmail(), user.getEmail());
        verify(userRepository).getUserByEmail(testLoginUser.getEmail());
        verify(passwordEncoder).matches(testLoginUser.getPassword(), testUser.getPassword());
    }

    @Test
    void testGetUserByUserAndPassword_Failure() {
        when(userRepository.getUserByEmail(testLoginUser.getEmail())).thenReturn(null);

        assertThrows(NotFoundUserException.class, () -> {
            userService.getUserByUserAndPassword(testLoginUser);
        });
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.existsByEmail(testRegisterUserDto.getEmail())).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Role.builder()
                .dateCreated(LocalDateTime.now())
                .status(RoleStatus.ACTIVE)
                .name("USER")
                .build());

        User testUser = User.builder()
                .email(testRegisterUserDto.getEmail())
                .name(testRegisterUserDto.getName())
                .password("encodedPassword")
                .dateCreated(LocalDateTime.now())
                .role(Role.builder().name("USER").build())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User createdUser = userService.createUser(testRegisterUserDto);

        assertNotNull(createdUser);
        assertEquals(testRegisterUserDto.getEmail(), createdUser.getEmail());
        verify(userRepository).existsByEmail(testRegisterUserDto.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(testRegisterUserDto.getEmail())).thenReturn(true);

        assertThrows(UserDuplicatedException.class, () -> {
            userService.createUser(testRegisterUserDto);
        });
    }
}
