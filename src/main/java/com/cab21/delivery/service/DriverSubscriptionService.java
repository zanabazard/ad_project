package com.cab21.delivery.service;

import com.cab21.delivery.dto.DriverSubscriptionDTOs.DriverSubscriptionCreateRequest;
import com.cab21.delivery.dto.DriverSubscriptionDTOs.DriverSubscriptionUpdateRequest;
import com.cab21.delivery.dto.response.DriverSubscriptionResponse;

import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

public interface DriverSubscriptionService {
    DriverSubscriptionResponse create(DriverSubscriptionCreateRequest req);
    DriverSubscriptionResponse update(Long id, DriverSubscriptionUpdateRequest req);
    DriverSubscriptionResponse get(Long id);
    GridResponse list(GridRequest req);
    void delete(Long id);
}
