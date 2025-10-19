package com.cab21.delivery.service;

import com.cab21.delivery.dto.request.PlanCreateRequest;
import com.cab21.delivery.dto.request.PlanUpdateRequest;
import com.cab21.delivery.model.SubscriptionPlan;

import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

public interface  SubscriptionPlanService {
    SubscriptionPlan create(PlanCreateRequest req);
    SubscriptionPlan update(Long id, PlanUpdateRequest req);
    SubscriptionPlan get(Long id);
    GridResponse list(GridRequest request);
    String delete(Long id);
}
