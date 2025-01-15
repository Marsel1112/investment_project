package org.invest.controler;

import lombok.RequiredArgsConstructor;
import org.invest.dto.SignUser;
import org.invest.dto.UserUpdateForm;
import org.invest.entity.User;
import org.invest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity<String> update(@RequestBody UserUpdateForm userUpdateForm) {
        userService.updateUser(userUpdateForm);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Success");
    }
}
