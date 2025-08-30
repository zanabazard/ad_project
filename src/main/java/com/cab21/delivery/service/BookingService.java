package com.cab21.delivery.service;

import org.springframework.http.ResponseEntity;
import com.cab21.delivery.dto.BookingDto;
import com.cab21.delivery.dto.request.BookingRequest;

public interface BookingService {
    Long bookSeat(BookingRequest request, Long userId);
    void cancelSeat(Long bookingId, Long userId);
    ResponseEntity<Void> cancelByDriver(BookingDto bookingDto);
}
