package org.invest.repository;

import org.invest.entity.AccessToken;
import org.invest.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    List<AccessToken> getByUserId(Long user_id);
    AccessToken getByRefreshToken(RefreshToken refreshToken);
}
