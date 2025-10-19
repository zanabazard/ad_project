package com.cab21.delivery.dto.DriverSubscriptionDTOs;

import java.util.Date;

public record DriverSubscriptionCreateRequest(
        Long driverId,
        Long planId,
        Long paymentId,
        Date startsAt,
        Boolean autoRenew 
) {}
