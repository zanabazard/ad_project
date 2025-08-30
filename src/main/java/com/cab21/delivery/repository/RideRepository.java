package com.cab21.delivery.repository;

import com.cab21.delivery.model.Ride;
import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Ride r where r.id = :id")
    Optional<Ride> findByIdForUpdate(@Param("id") Long id);
    @EntityGraph(attributePaths = {"cab", "bookings", "bookings.user"})
    @Query("select r from Ride r where r.id = :id")
    Optional<Ride> findOneWithCabAndBookings(@Param("id") Long id);
}
