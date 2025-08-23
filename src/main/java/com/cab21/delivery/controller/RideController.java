package com.cab21.delivery.controller;

import com.cab21.delivery.dto.RideDto;
import com.cab21.delivery.dto.request.CreateRideRequest;
import com.cab21.delivery.model.Ride;
import com.cab21.delivery.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<RideDto> get(@PathVariable Long id) {
        return rideService.get(id);
    }
}
