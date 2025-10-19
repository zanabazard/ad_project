package com.cab21.delivery.dto.request;

import java.math.BigDecimal;

public record PlanUpdateRequest(
        String name,
        short durationMonths,
        boolean isActive,
        BigDecimal price
) {}
