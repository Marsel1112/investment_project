
package org.invest.controler;

import lombok.RequiredArgsConstructor;
import org.invest.dto.*;
import org.invest.entity.User;
import org.invest.service.dailyOpenClose.DailyOpenCloseService;
import org.invest.service.dailyOpenClose.TickerService;
import org.invest.service.auth.AuthService;
import org.invest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final DailyOpenCloseService dailyOpenCloseService;
    private final TickerService tickerService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody RegisterUserDto registerUserDto) {
        User user = userService.createUser(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginUser loginUser) {
        JwtAuthenticationResponse response = authService.getJwtAuthenticationResponse(loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/saved/{ticker}")
    public ResponseEntity<List<DailyOpenCloseDto>> saved(@PathVariable String ticker) {
        tickerService.validateTicker(ticker);
        Long userId = getUserId();

        List<DailyOpenCloseDto> dailyOpenCloseDtoList = dailyOpenCloseService.getDailyOpenClosesByTicker(ticker, userId);

        return ResponseEntity.ok(dailyOpenCloseDtoList);
    }

    @PostMapping("/save")
    public ResponseEntity<List<DailyOpenCloseDto>> save(@RequestBody DailyDtoBetweenDates betweenDates) {
        tickerService.validateTicker(betweenDates.getTicker());
        Long userId = getUserId();

        List<DailyOpenCloseDto> dailyOpenCloseDtoList =
                dailyOpenCloseService.getDailyOpenClosesBetweenDates(betweenDates, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(dailyOpenCloseDtoList);
    }

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return principal.getUserId();
    }

}
