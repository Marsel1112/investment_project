package org.invest.controler;

import lombok.RequiredArgsConstructor;
import org.invest.dto.RegisterUserDto;
import org.invest.entity.User;
import org.invest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody RegisterUserDto registerUserDto) {
        User user = userService.createUser(registerUserDto);
        return ResponseEntity.ok(user.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody RegisterUserDto registerUserDto) {
        User user = userService.createUser(registerUserDto);
        return ResponseEntity.ok(user.getId());
    }

    @PostMapping("/saved")
    public ResponseEntity<Long> saved(@RequestBody RegisterUserDto registerUserDto) {
        User user = userService.createUser(registerUserDto);
        return ResponseEntity.ok(user.getId());
    }

    @PostMapping("/save")
    public ResponseEntity<Long> save(@RequestBody RegisterUserDto registerUserDto) {
        User user = userService.createUser(registerUserDto);
        return ResponseEntity.ok(user.getId());
    }
}
