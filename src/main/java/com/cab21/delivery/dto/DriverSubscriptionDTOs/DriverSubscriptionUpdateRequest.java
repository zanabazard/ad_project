package com.cab21.delivery.dto.DriverSubscriptionDTOs;

import com.cab21.delivery.model.enums.DriverSubscriptionStatus;

public record DriverSubscriptionUpdateRequest(
        DriverSubscriptionStatus status,
        Boolean autoRenew
) {}
