
package org.invest.controler;

import lombok.RequiredArgsConstructor;
import org.invest.client.PolygonClient;
import org.invest.dto.*;
import org.invest.entity.User;
import org.invest.service.DailyOpenCloseService;
import org.invest.service.auth.AuthService;
import org.invest.service.UserService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final DailyOpenCloseService dailyOpenCloseService;
    private final PolygonClient feignClient;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody RegisterUserDto registerUserDto) {
        User user = userService.createUser(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginUser loginUser) {
        JwtAuthenticationResponse jwtAuthenticationResponse =
                authService.getJwtAuthenticationResponse(loginUser);
        return ResponseEntity.ok(jwtAuthenticationResponse);
    }

    @GetMapping("/saved")
    public ResponseEntity<List<DailyOpenCloseDto>> saved(@RequestBody TickerDto tickerDto) {
        Long userId = getUserId();

        List<DailyOpenCloseDto> dailyOpenCloseDtos = dailyOpenCloseService.getDailyOpenClosesByTicker(tickerDto, userId);

        return ResponseEntity.ok(dailyOpenCloseDtos);
    }

    @PostMapping("/save")
    public ResponseEntity<List<DailyOpenCloseDto>> save(@RequestBody DailyOpenCloseDtoBetweenDates betweenDates) {
        Long userId = getUserId();

        List<DailyOpenCloseDto> dailyOpenCloseDtos =
                dailyOpenCloseService.getDailyOpenClosesBetweenDates(betweenDates, userId);

        return ResponseEntity.ok(dailyOpenCloseDtos);
    }

    @PostMapping("/test")
    public ResponseEntity<DailyOpenCloseDto> test(@RequestBody DailyOpenCloseDtoBetweenDates betweenDates) {

        System.out.println(betweenDates.getOpenDate().toString());
        var g = feignClient.getPolygon(betweenDates.getTicker(), betweenDates.getOpenDate());




        return ResponseEntity.ok(g);
    }

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return principal.getUserId();
    }

}
