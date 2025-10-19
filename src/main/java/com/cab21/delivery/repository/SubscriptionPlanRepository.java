package com.cab21.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab21.delivery.model.SubscriptionPlan;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
    Optional<SubscriptionPlan> findByCode(String code);
    boolean existsByCode(String code);
}


