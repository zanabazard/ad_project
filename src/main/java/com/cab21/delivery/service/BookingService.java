package com.cab21.delivery.service;

import com.cab21.delivery.dto.request.BookingRequest;

public interface BookingService {
    Long bookSeat(BookingRequest request, Long userId);
    void cancelSeat(Long bookingId, Long userId);
}
