package com.cab21.delivery.repository;

import com.cab21.delivery.model.DriverSubscription;
import com.cab21.delivery.model.enums.DriverSubscriptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DriverSubscriptionRepository extends JpaRepository<DriverSubscription, Long> {
    Page<DriverSubscription> findByDriverId(Long driverId, Pageable pageable);
    Page<DriverSubscription> findByStatus(DriverSubscriptionStatus status, Pageable pageable);
}
