package com.cab21.delivery.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cab21.delivery.dto.DriverSubscriptionDTOs.DriverSubscriptionCreateRequest;
import com.cab21.delivery.dto.DriverSubscriptionDTOs.DriverSubscriptionUpdateRequest;
import com.cab21.delivery.dto.response.DriverSubscriptionResponse;
import com.cab21.delivery.model.DriverSubscription;
import com.cab21.delivery.model.enums.DriverSubscriptionStatus;
import com.cab21.delivery.repository.DriverSubscriptionRepository;
import com.cab21.delivery.repository.SubscriptionPlanRepository;
import com.cab21.delivery.service.DriverSubscriptionService;

import nm.common.grid.repo.GridRepo;
import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

@Service
@EnableWebMvc
@EnableAsync
public class DriverSubscriptionImpl implements DriverSubscriptionService {
    private final DriverSubscriptionRepository repo;
    private final SubscriptionPlanRepository planRepo;
    @Autowired
    GridRepo gridRepo;
    public DriverSubscriptionImpl(DriverSubscriptionRepository repo, SubscriptionPlanRepository planRepo, GridRepo gridRepo) {
        this.repo = repo;
        this.planRepo = planRepo;
        this.gridRepo = gridRepo;
    }

    @Transactional
    public DriverSubscriptionResponse create(DriverSubscriptionCreateRequest req) {
        var plan = planRepo.findById(req.planId()).orElseThrow();

        var cal = Calendar.getInstance();
        cal.setTime(req.startsAt());
        cal.add(Calendar.MONTH, plan.getDurationMonths());
        var ends = cal.getTime();

        var entity = DriverSubscription.builder()
                .driverId(req.driverId())
                .plan(plan)
                .paymentId(req.paymentId())
                .startsAt(req.startsAt())
                .endsAt(ends)
                .status(DriverSubscriptionStatus.ACTIVE)
                .createdAt(new Date())
                .updatedAt(new Date())
                .autoRenew(Boolean.TRUE.equals(req.autoRenew()))
                .build();

        DriverSubscription saved = repo.save(entity);          
        return DriverSubscriptionResponse.from(saved);          
    }

    @Transactional
    public DriverSubscriptionResponse update(Long id, DriverSubscriptionUpdateRequest req) {
        DriverSubscription ds = repo.findById(id).orElseThrow(); 
        ds.setStatus(req.status());
        if (req.autoRenew() != null) ds.setAutoRenew(req.autoRenew());

        DriverSubscription saved = repo.save(ds);                
        return DriverSubscriptionResponse.from(saved);          
    }

    public DriverSubscriptionResponse get(Long id) {
        DriverSubscription ds = repo.findById(id).orElseThrow();
        return DriverSubscriptionResponse.from(ds);
    }

    @Transactional(readOnly = true)
    public GridResponse list(GridRequest req) {
       String sql = """
            SELECT id, driver_id, plan_id, DATE_FORMAT(starts_at, '%Y-%m-%d %H:%i:%s') AS starts_at, DATE_FORMAT(ends_at, '%Y-%m-%d %H:%i:%s') AS ends_at, status, auto_renew, DATE_FORMAT(created_at, '%Y-%m-%d %H:%i:%s') AS created_at
            FROM driver_subscriptions
        """;
        return gridRepo.getDatatable(sql, req, true);
    }


    @Transactional
    public void delete(Long id) { repo.deleteById(id); }
}