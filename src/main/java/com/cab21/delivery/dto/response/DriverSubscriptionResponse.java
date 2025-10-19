package com.cab21.delivery.dto.response;

import java.util.Date;

import com.cab21.delivery.model.DriverSubscription;

public record DriverSubscriptionResponse(
        Long id,
        Long driverId,
        Long planId,
        Long paymentId,
        Date startsAt,
        Date endsAt,
        String status,
        Boolean autoRenew,
        Date createdAt,
        Date updatedAt
) {
    public static DriverSubscriptionResponse from(DriverSubscription ds) {
        return new DriverSubscriptionResponse(
            ds.getId(),
            ds.getDriverId(),
            ds.getPlan() != null ? ds.getPlan().getId() : null,
            ds.getPaymentId(),
            ds.getStartsAt(),
            ds.getEndsAt(),
            ds.getStatus().name(),
            ds.getAutoRenew(),
            ds.getCreatedAt(),
            ds.getUpdatedAt()
        );
    }
}
