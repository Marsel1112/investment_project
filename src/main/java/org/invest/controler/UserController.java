package org.invest.controler;

import lombok.RequiredArgsConstructor;
import org.invest.dto.SignUser;
import org.invest.entity.User;
import org.invest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/reg")
    public ResponseEntity<Long> reg(@RequestBody SignUser signUser) {
        User user = userService.createUser(signUser);
        return ResponseEntity.ok(user.getId());
    }
}
