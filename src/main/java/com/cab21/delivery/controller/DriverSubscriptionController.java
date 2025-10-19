package com.cab21.delivery.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab21.delivery.dto.DriverSubscriptionDTOs.DriverSubscriptionCreateRequest;
import com.cab21.delivery.dto.DriverSubscriptionDTOs.DriverSubscriptionUpdateRequest;
import com.cab21.delivery.dto.response.DriverSubscriptionResponse;
import com.cab21.delivery.service.DriverSubscriptionService;

import lombok.RequiredArgsConstructor;
import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

@RestController
@RequestMapping("/api/driver-subscriptions")
@RequiredArgsConstructor
public class DriverSubscriptionController {
    private final DriverSubscriptionService service;


    /***
     * Идэвхтэй жолоочийн эрх үүсгэх сервис
     */
    @PostMapping("/create")
    public DriverSubscriptionResponse create(@RequestBody DriverSubscriptionCreateRequest req) {
        return service.create(req);
    }
    /***
     * Идэвхтэй жолоочийн эрх шинэчлэх сервис
     */
    @PutMapping("/{id}")
    public DriverSubscriptionResponse update(@PathVariable Long id, @RequestBody DriverSubscriptionUpdateRequest req) {
        return service.update(id, req);
    }
    /***
     * Идэвхтэй жолоочийн эрх жагсаалтаар авах сервис
     */
    @PostMapping("/list")
    public GridResponse list(@RequestBody GridRequest req) {
        return service.list(req);
    }   
    /***
     * Идэвхтэй жолоочийн эрх устгах сервис
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
