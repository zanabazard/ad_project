package com.cab21.delivery.service;

public interface BookingService {
    Long bookSeat(Long rideId, Long userId);
    void cancelSeat(Long bookingId, Long userId);
}
