package org.invest.controler;

import org.invest.dto.*;
import org.invest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtAuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SignUser signUser){
        return ResponseEntity.ok(authService.getJwtAuthenticationResponseInDataBase(signUser));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> refresh(@RequestBody RefreshTokenRequest refreshToken){
        AccessTokenResponse tokenResponse =
                new AccessTokenResponse(authService.getAccessTokenByRefreshToken(refreshToken.getRefreshToken()));

        return ResponseEntity.ok(tokenResponse);
    }
}
