package com.cab21.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab21.delivery.model.User;

public interface  UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String newUsername, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUsernameAndStatus(String username, int i);

    boolean existsByEmailAndStatus(String email, int i);
}
