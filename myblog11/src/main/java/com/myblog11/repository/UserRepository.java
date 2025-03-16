package com.myblog11.repository;

import com.myblog11.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username); // Use `username` instead of `userName`
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email); // This is already correct

    Boolean existsByUsername(String username); // Use `existsByUsername` instead of `existByUserName`
    Boolean existsByEmail(String email); // This method is fine
}
