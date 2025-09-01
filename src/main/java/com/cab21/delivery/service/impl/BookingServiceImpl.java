package com.cab21.delivery.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cab21.delivery.dto.BookingDto;
import com.cab21.delivery.dto.request.BookingRequest;
import com.cab21.delivery.model.Booking;
import com.cab21.delivery.model.Ride;
import com.cab21.delivery.model.User;
import com.cab21.delivery.repository.BookingRepository;
import com.cab21.delivery.repository.RideRepository;
import com.cab21.delivery.repository.UserRepository;
import com.cab21.delivery.service.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableWebMvc
@EnableAsync
public class BookingServiceImpl implements BookingService {

    private final RideRepository rideRepo;
    private final BookingRepository bookingRepo;
    private final UserRepository userRepo;

    @Override
    @Transactional
    public Long bookSeat(BookingRequest request, Long userId) {
        // Lock the ride row to prevent overbooking
        Ride ride = rideRepo.findByIdForUpdate(request.getRideId())
                .orElseThrow(() -> new IllegalArgumentException("ride not found"));

        if (!"OPEN".equalsIgnoreCase(ride.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Явахад нээлттэй болоогүй байна");
        }
        if (bookingRepo.existsByRideIdAndUserId(request.getRideId(), userId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Аль хэдийн бүртгүүлсэн байна");
        }
        if (ride.getCapacity() - ride.getPassengerCount() < request.getSeatCount()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Хүн дүүрсэн байна ( Ихдээ " + ride.getCapacity() + " зорчигчтой байх боломжтой)");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        Booking b = new Booking();
        b.setRide(ride);
        b.setUser(user);
        b.setSeat(request.getSeatCount());
        b.setStatus("BOOKED");
        bookingRepo.save(b);

        ride.setPassengerCount(ride.getPassengerCount() + request.getSeatCount());
        if (ride.getPassengerCount() == ride.getCapacity()) {
            ride.setStatus("FULL");
        }
        rideRepo.save(ride);
        return b.getId();
    }

    @Override
    @Transactional
    public void cancelSeat(Long bookingId, Long userId) {
        Booking b = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("booking not found"));
        if (!b.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("not your booking");
        }

        Ride ride = rideRepo.findByIdForUpdate(b.getRide().getId())
                .orElseThrow(() -> new IllegalArgumentException("ride not found"));

        if ("BOOKED".equalsIgnoreCase(b.getStatus())) {
            b.setStatus("CANCELLED");
            bookingRepo.save(b);
            ride.setPassengerCount(Math.max(0, ride.getPassengerCount() - 1));
        }
    }

    public ResponseEntity<Void> cancelByDriver(BookingDto bookingDto) {
    Booking b = bookingRepo.findByIdAndStatus(bookingDto.getId(),  "BOOKED")
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Active booking not found"));

        Ride r = b.getRide();
        if (!"OPEN".equalsIgnoreCase(r.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ride is not OPEN");
        }

        b.setStatus("CANCELLED_BY_DRIVER");
        bookingRepo.save(b);

        r.setPassengerCount(Math.max(0, r.getPassengerCount() - 1));
        rideRepo.save(r);

        return ResponseEntity.noContent().build();
    }
}
