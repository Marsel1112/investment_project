package org.invest.service.auth;

import lombok.RequiredArgsConstructor;
import org.invest.dto.JwtAuthenticationResponse;
import org.invest.dto.LoginUser;
import org.invest.entity.AccessToken;
import org.invest.entity.RefreshToken;
import org.invest.entity.User;
import org.invest.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthServiceFacade authServiceFacade;

    public JwtAuthenticationResponse getJwtAuthenticationResponse(LoginUser loginUser) {
        User user = userService.getUserByUserAndPassword(loginUser);

        JwtAuthenticationResponse jwtAuthenticationResponse =
                authServiceFacade.getValidJwtAuthenticationResponseFromDataBase(user);

        if(jwtAuthenticationResponse != null)
            return jwtAuthenticationResponse;

        RefreshToken refreshToken = authServiceFacade.getValidRefreshTokenFromDataBase(user);

        if(refreshToken != null) {
            AccessToken accessToken = authServiceFacade.getNewAccessTokenByRefreshToken(refreshToken);
            return new JwtAuthenticationResponse(accessToken.getValue(),refreshToken.getValue());
        }

        return authServiceFacade.insertJwtAuthenticationResponse(user);
    }
}
