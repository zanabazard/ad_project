package com.cab21.delivery.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cab21.delivery.dto.request.PlanCreateRequest;
import com.cab21.delivery.dto.request.PlanUpdateRequest;
import com.cab21.delivery.model.SubscriptionPlan;
import com.cab21.delivery.repository.SubscriptionPlanRepository;
import com.cab21.delivery.service.SubscriptionPlanService;

import jakarta.transaction.Transactional;
import nm.common.grid.repo.GridRepo;
import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

@Service
@EnableWebMvc
@EnableAsync
public class SubscriptionPlanImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository repo;

    @Autowired
    GridRepo gridRepo;


    public SubscriptionPlanImpl(SubscriptionPlanRepository repo, GridRepo gridRepo) {
        this.repo = repo;
        this.gridRepo = gridRepo;
    }

    @Transactional
    public SubscriptionPlan create(PlanCreateRequest req) {
        if (repo.existsByCode(req.code())) {
            throw new IllegalArgumentException("Plan code already exists: " + req.code());
        }
        var plan = SubscriptionPlan.builder()
                .code(req.code())
                .name(req.name())
                .durationMonths(req.durationMonths())
                .isActive(req.isActive())
                .price(req.price())
                .createdAt(new Date())
                .build();
        return repo.save(plan);
    }

    @Transactional
    public SubscriptionPlan update(Long id, PlanUpdateRequest req) {
        var plan = repo.findById(id).orElseThrow();
        plan.setName(req.name());
        plan.setDurationMonths(req.durationMonths());
        plan.setPrice(req.price());
        plan.setIsActive(req.isActive());
        return repo.save(plan);
    }

    public SubscriptionPlan get(Long id) { return repo.findById(id).orElseThrow(); }

    public GridResponse list(GridRequest request) {
        String sql = """
            SELECT id, code, name, duration_months, is_active, price, DATE_FORMAT(created_at, '%Y-%m-%d %H:%i:%s') AS created_at
            FROM subscription_plans
        """;
        return gridRepo.getDatatable(sql, request, true);
    }

    @Transactional
    public String delete(Long id) { repo.deleteById(id); return "success"; }

}
