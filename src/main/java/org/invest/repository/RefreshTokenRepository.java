package org.invest.repository;

import org.invest.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken getByValue(String value);
    List<RefreshToken> getByUserId(Long user_id);
}
