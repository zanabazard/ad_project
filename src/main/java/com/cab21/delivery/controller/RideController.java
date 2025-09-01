package com.cab21.delivery.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab21.delivery.dto.RideDto;
import com.cab21.delivery.dto.RideWithBookingDto;
import com.cab21.delivery.dto.request.CreateRideRequest;
import com.cab21.delivery.service.RideService;

import lombok.RequiredArgsConstructor;
import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    /***
     * 	   Зар үүсгэх
     */
    @PostMapping("/create")
    public ResponseEntity<RideDto> create(@RequestBody CreateRideRequest req) {
        return rideService.create(req);
    }
    /***
     * 	   Зарыг id-аар нь авах сервис
     */
    @GetMapping("/{id}")
    public ResponseEntity<RideWithBookingDto> get(@PathVariable Long id) {
        return rideService.get(id);
    }

    @PostMapping("/checklist/grid")
    public GridResponse getInspectionGrid(@RequestBody GridRequest request) {
        return rideService.getGrid(request);
    }

}
