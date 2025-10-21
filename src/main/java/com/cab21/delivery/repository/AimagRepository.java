package com.cab21.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cab21.delivery.model.Aimags;

@Repository
public interface AimagRepository extends JpaRepository<Aimags, Integer> {
    Optional<Aimags> findById(Integer id);
    boolean existsByValue(String value);
}
