package com.cab21.delivery.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cab21.delivery.model.Payment;
import com.cab21.delivery.model.enums.PaymentStatus;
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findByStatus(PaymentStatus status, Pageable pageable);
    Page<Payment> findByDriverId(Long driverId, Pageable pageable);
    Page<Payment> findByPlanId(Long planId, Pageable pageable);
}
