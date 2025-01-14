package org.invest.repository;

import org.invest.entity.AccessToken;
import org.invest.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    AccessToken getByUserId(Long user_id);
    AccessToken getByRefreshToken(RefreshToken refreshToken);
}
