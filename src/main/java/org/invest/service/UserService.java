package org.invest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.invest.dto.LoginUser;
import org.invest.dto.RegisterUserDto;
import org.invest.entity.DailyOpenClose;
import org.invest.entity.Role;
import org.invest.entity.User;
import org.invest.exception.UserResours.NotFoundUserException;
import org.invest.exception.UserResours.UserDuplicatedException;
import org.invest.repository.RoleRepository;
import org.invest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserByUserAndPassword(LoginUser loginUser) {
        User user = userRepository.getUserByEmail(loginUser.getEmail());
        if(user != null) {
            if (comparePasswords(user, loginUser.getPassword())) {
                return user;
            }
        }
        throw new NotFoundUserException("Неправильные данные", loginUser);
    }

    public User createUser(RegisterUserDto registerUserDto) {
        if(userRepository.existsByEmail(registerUserDto.getEmail()))
            throw new UserDuplicatedException("Такая почта уже существует");

        return userRepository.save(
                User.builder()
                        .email(registerUserDto.getEmail())
                        .name(registerUserDto.getName())
                        .password(passwordEncoder.encode(registerUserDto.getPassword()))
                        .dateCreated(LocalDateTime.now())
                        .role(getBasikRole())
                        .build());
    }

    private Role getBasikRole(){
        return roleRepository.findByName("USER");
    }

    private boolean comparePasswords(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
