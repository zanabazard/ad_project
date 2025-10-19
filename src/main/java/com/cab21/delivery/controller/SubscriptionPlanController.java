package com.cab21.delivery.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab21.delivery.dto.request.PlanCreateRequest;
import com.cab21.delivery.dto.request.PlanUpdateRequest;
import com.cab21.delivery.model.SubscriptionPlan;
import com.cab21.delivery.service.SubscriptionPlanService;

import lombok.RequiredArgsConstructor;
import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

@RestController
@RequestMapping("/api/subscription-plans")
@RequiredArgsConstructor
public class SubscriptionPlanController {
        private final SubscriptionPlanService service;
    /***
     * Жолоочийн үйлчилгээний төлөвлөгөө үүсгэх сервис
     */
    @PostMapping("/create")
    public SubscriptionPlan create(@RequestBody PlanCreateRequest req) {
        return service.create(req);
    }
    /***
     * Жолоочийн үйлчилгээний төлөвлөгөө шинэчлэх сервис
     */
    @PutMapping("/{id}")
    public SubscriptionPlan update(@PathVariable Long id, @RequestBody PlanUpdateRequest req) {
        return service.update(id, req);
    }
    /***
     * Жолоочийн үйлчилгээний төлөвлөгөө мэдээлэл авах сервис
     */
    @GetMapping("/{id}")
    public SubscriptionPlan get(@PathVariable Long id) { return service.get(id); }
    /***
     * Жолоочийн үйлчилгээний төлөвлөгөө жагсаалтаар мэдээлэл авах сервис
     */
    @PostMapping("/list")
    public GridResponse list(@RequestBody GridRequest request) {
        return service.list(request);
    }
    /***
     * Жолоочийн үйлчилгээний төлөвлөгөө устгах сервис
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) { 
        service.delete(id); 
        return "Deleted successfully";
    }
}
