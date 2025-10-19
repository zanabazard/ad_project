package com.cab21.delivery.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cab21.delivery.model.Payment;
import com.cab21.delivery.model.PaymentLog;
import com.cab21.delivery.model.enums.PaymentStatus;
import com.cab21.delivery.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentService service;

    @GetMapping
    public Page<Payment> listPayments(@RequestParam(required = false) PaymentStatus status,
                                      @RequestParam(required = false) Long driverId,
                                      @RequestParam(required = false) Long planId,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size) {
        return service.listPayments(status, driverId, planId, Pageable.ofSize(size).withPage(page));
    }

    @GetMapping("/{paymentId}/logs")
    public Page<PaymentLog> listLogs(@PathVariable Long paymentId,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "50") int size) {
        return service.listPaymentLogs(paymentId, Pageable.ofSize(size).withPage(page));
    }
}
