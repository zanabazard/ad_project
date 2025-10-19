package com.cab21.delivery.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cab21.delivery.model.Payment;
import com.cab21.delivery.model.PaymentLog;
import com.cab21.delivery.model.enums.PaymentStatus;
import com.cab21.delivery.repository.PaymentLogRepository;
import com.cab21.delivery.repository.PaymentRepository;
import com.cab21.delivery.service.PaymentService;
@Service
@EnableWebMvc
@EnableAsync
public class PaymentImpl implements PaymentService {
    private final PaymentRepository paymentRepo;
    private final PaymentLogRepository logRepo;

    public PaymentImpl(PaymentRepository paymentRepo, PaymentLogRepository logRepo) {
        this.paymentRepo = paymentRepo;
        this.logRepo = logRepo;
    }

    public Page<Payment> listPayments(PaymentStatus status, Long driverId, Long planId, Pageable pageable) {
        if (status != null) return paymentRepo.findByStatus(status, pageable);
        if (driverId != null) return paymentRepo.findByDriverId(driverId, pageable);
        if (planId != null) return paymentRepo.findByPlanId(planId, pageable);
        return paymentRepo.findAll(pageable);
    }

    public Page<PaymentLog> listPaymentLogs(Long paymentId, Pageable pageable) {
        return logRepo.findByPaymentId(paymentId, pageable);
    }
}
