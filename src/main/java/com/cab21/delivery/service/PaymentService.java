package com.cab21.delivery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cab21.delivery.model.Payment;
import com.cab21.delivery.model.PaymentLog;
import com.cab21.delivery.model.enums.PaymentStatus;

public interface PaymentService {
    Page<Payment> listPayments( PaymentStatus status,  Long driverId,Long planId, Pageable pageable);
    Page<PaymentLog> listPaymentLogs( Long paymentId, Pageable pageable);
                                    
}
