package com.cab21.delivery.repository;

import com.cab21.delivery.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByRideIdAndUserId(Long rideId, Long userId);
    long countByRideIdAndStatus(Long rideId, String status);
}
