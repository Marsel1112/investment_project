package org.invest.service;

import lombok.RequiredArgsConstructor;
import org.invest.dto.SignUser;
import org.invest.dto.UserUpdateForm;
import org.invest.entity.Role;
import org.invest.entity.User;
import org.invest.entity.enum_status_model.UserStatus;
import org.invest.repository.RoleRepository;
import org.invest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserByDtoSignUser(SignUser signUser) {
        User user = userRepository.getUserByEmail(signUser.getEmail());

        if(comparePasswords(user,signUser.getPassword()))
            return user;
        else
            throw new RuntimeException("Не правильный пароль!");
    }

    public User createUser(SignUser signUser) {
        return userRepository.save(
                User.builder()
                        .email(signUser.getEmail())
                        .password(passwordEncoder.encode(signUser.getPassword()))
                        .status(UserStatus.ACTIVE)
                        .dateCreated(LocalDateTime.now())
                        .build());
    }

    public void updateUser(UserUpdateForm userUpdateForm) {
        User user = userRepository.getUserByEmail(userUpdateForm.getEmail());

        if(userUpdateForm.getName() != null)
            user.setName(userUpdateForm.getName());
        if(userUpdateForm.getLastName() != null)
            user.setLastName(userUpdateForm.getLastName());
        if(userUpdateForm.getSurName() != null)
            user.setSurname(userUpdateForm.getSurName());
        if(userUpdateForm.getRoleName() != null)
            user.setRole(roleRepository.findByName(userUpdateForm.getRoleName()));

        userRepository.save(user);
    }

    public void deleteUser(Long userIdr) {
        User user = userRepository.getReferenceById(userIdr);
        user.setStatus(UserStatus.REMOVE);
        userRepository.save(user);
    }

    private boolean comparePasswords(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
