package com.cab21.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab21.delivery.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String newUsername, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUsernameAndStatus(String username, int i);

    boolean existsByEmailAndStatus(String email, int i);

    Optional<User> findByUsernameAndStatus(String username, Integer status);

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.username = :username and u.status = 1")
    Optional<User> findActiveByUsername(@Param("username") String username);

    Optional<User> findByPhone(String phone);
}
