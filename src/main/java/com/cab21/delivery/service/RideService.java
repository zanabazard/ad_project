package com.cab21.delivery.service;

import org.springframework.http.ResponseEntity;

import com.cab21.delivery.dto.RideDto;
import com.cab21.delivery.dto.request.CreateRideRequest;

public interface RideService {
    ResponseEntity<RideDto> create(CreateRideRequest req);
    ResponseEntity<RideDto> get(Long id);
}
