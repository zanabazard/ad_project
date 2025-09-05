package com.cab21.delivery.service;

import org.springframework.http.ResponseEntity;

import com.cab21.delivery.dto.BookingDto;
import com.cab21.delivery.dto.request.BookingRequest;

import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

public interface BookingService {
    Long bookSeat(BookingRequest request, Long userId);
    void cancelSeat(Long bookingId, Long userId);
    ResponseEntity<Void> cancelByDriver(BookingDto bookingDto);
    ResponseEntity<String> approveBooking(Long bookingId);
    GridResponse getGrid(GridRequest request);
}
