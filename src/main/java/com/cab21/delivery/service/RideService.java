package com.cab21.delivery.service;

import org.springframework.http.ResponseEntity;

import com.cab21.delivery.dto.RideDto;
import com.cab21.delivery.dto.RideWithBookingDto;
import com.cab21.delivery.dto.request.CreateRideRequest;

import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

public interface RideService {
    ResponseEntity<RideDto> create(CreateRideRequest req);
    ResponseEntity<RideWithBookingDto> get(Long id);
    GridResponse getGrid(GridRequest request);
}
