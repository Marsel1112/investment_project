package org.invest.service.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.invest.dto.JwtAuthenticationResponse;
import org.invest.entity.AccessToken;
import org.invest.entity.RefreshToken;
import org.invest.entity.User;
import org.invest.repository.AccessTokenRepository;
import org.invest.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceFacade {
    private final JwtService jwtService;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    protected JwtAuthenticationResponse getValidJwtAuthenticationResponseFromDataBase(User user) {
        List<AccessToken> accessTokenList = accessTokenRepository.getByUserId(user.getId());

        AccessToken validAccessToken = accessTokenList.stream()
                .filter(accessToken -> jwtService.IsValidToken(accessToken.getValue()))
                .findFirst()
                .orElse(null);

        if(validAccessToken == null)
            return null;

        RefreshToken refreshToken = validAccessToken.getRefreshToken();

        return new JwtAuthenticationResponse(validAccessToken.getValue(),refreshToken.getValue());
    }

    public AccessToken getNewAccessTokenByRefreshToken(RefreshToken refreshToken) {
        return accessTokenRepository.save(
                AccessToken.builder()
                        .user(refreshToken.getUser())
                        .dateCreated(LocalDateTime.now())
                        .refreshToken(refreshToken)
                        .value(jwtService.refreshAccessToken(refreshToken.getValue()))
                        .build());
    }

    public JwtAuthenticationResponse insertJwtAuthenticationResponse(User user) {
        RefreshToken refreshToken = refreshTokenRepository.save(
                RefreshToken.builder()
                        .user(user)
                        .dateCreated(LocalDateTime.now())
                        .value(jwtService.generateRefreshToken(user.getId(), user.getEmail()))
                        .build());

        AccessToken accessToken = getNewAccessTokenByRefreshToken(refreshToken);

        return new JwtAuthenticationResponse(accessToken.getValue(),refreshToken.getValue());
    }

    public RefreshToken getValidRefreshTokenFromDataBase(User user) {
        List<RefreshToken> refreshTokenList = refreshTokenRepository.getByUserId(user.getId());

        return refreshTokenList.stream()
                .filter(refreshToken -> jwtService.IsValidToken(refreshToken.getValue()))
                .findFirst()
                .orElse(null);
    }


}
