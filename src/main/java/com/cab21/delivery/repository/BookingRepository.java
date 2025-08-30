package com.cab21.delivery.repository;

import com.cab21.delivery.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByRideIdAndUserId(Long rideId, Long userId);
    long countByRideIdAndStatus(Long rideId, String status);
    List<Booking> findByRideId(Long rideId);
    Optional<Booking> findByIdAndStatus(Long id,  String status);
}
