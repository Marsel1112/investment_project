package org.invest.repository;

import org.invest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
    Boolean existsByEmail(String email);
    User getUserById(Long id);
}
