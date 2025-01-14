package org.invest.service;

import lombok.RequiredArgsConstructor;
import org.invest.dto.JwtAuthenticationResponse;
import org.invest.dto.LoginUser;
import org.invest.dto.SignUser;
import org.invest.entity.AccessToken;
import org.invest.entity.RefreshToken;
import org.invest.entity.User;
import org.invest.repository.AccessTokenRepository;
import org.invest.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserService userService;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthenticationResponse getJwtAuthenticationResponseInDataBase(SignUser signUser) {
        User user = userService.getUserByDtoSignUser(signUser);
        RefreshToken refreshToken = getValidRefreshToken(refreshTokenRepository.getByUserId(user.getId()));

        if(refreshToken != null) {
            AccessToken accessToken = accessTokenRepository.getByRefreshToken(refreshToken);
            if(accessToken != null && isValidToken(accessToken.getValue())) {
                return new JwtAuthenticationResponse(accessToken.getValue(),refreshToken.getValue());
            }
            return new JwtAuthenticationResponse(
                    getAccessTokenByRefreshToken(refreshToken.getValue()),refreshToken.getValue()
            );
        }
        return insertJwtAuthenticationResponse(signUser);
    }

    public JwtAuthenticationResponse insertJwtAuthenticationResponse(SignUser signUser) {
        User user = userService.getUserByDtoSignUser(signUser);

        RefreshToken refreshToken = refreshTokenRepository.save(
                                    RefreshToken.builder()
                                            .user(user)
                                            .dateCreated(LocalDateTime.now())
                                            .value(jwtService.generateRefreshToken(user.getId(), user.getEmail()))
                                            .build());

        AccessToken accessToken = accessTokenRepository.save(
                                    AccessToken.builder()
                                            .user(user)
                                            .dateCreated(LocalDateTime.now())
                                            .refreshToken(refreshToken)
                                            .value(jwtService.generateAccessToken(user.getId(), user.getEmail()))
                                            .build());

        return new JwtAuthenticationResponse(accessToken.getValue(),refreshToken.getValue());
    }

    public String getAccessTokenByRefreshToken(String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.getByValue(refreshToken);
        AccessToken accessToken = accessTokenRepository.save(
                                    AccessToken.builder()
                                            .user(refreshTokenEntity.getUser())
                                            .dateCreated(LocalDateTime.now())
                                            .refreshToken(refreshTokenEntity)
                                            .value(jwtService.refreshAccessToken(refreshToken))
                                            .build());
        return accessToken.getValue();
    }

    private RefreshToken getValidRefreshToken(List<RefreshToken> refreshTokenList) {
        return refreshTokenList.stream()
                .filter(refreshToken -> jwtService.IsValidToken(refreshToken.getValue()))
                .findFirst()
                .orElse(null);
    }

    private boolean isValidToken(String token) {
        return jwtService.IsValidToken(token);
    }


}
