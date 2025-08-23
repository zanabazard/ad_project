package com.cab21.delivery.service;

import com.cab21.delivery.dto.RideDto;
import com.cab21.delivery.dto.request.CreateRideRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface RideService {
    ResponseEntity<RideDto> create(CreateRideRequest req);
    ResponseEntity<RideDto> get(Long id);
}
