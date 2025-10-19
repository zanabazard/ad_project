package com.cab21.delivery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cab21.delivery.model.PaymentLog;

public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {
    Page<PaymentLog> findByPaymentId(Long paymentId, Pageable pageable);
}
