package com.cab21.delivery.service.impl;

import com.cab21.delivery.model.Booking;
import com.cab21.delivery.model.Ride;
import com.cab21.delivery.model.User;
import com.cab21.delivery.repository.BookingRepository;
import com.cab21.delivery.repository.RideRepository;
import com.cab21.delivery.repository.UserRepository;
import com.cab21.delivery.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
    public Long bookSeat(Long rideId, Long userId) {
        // Lock the ride row to prevent overbooking
        Ride ride = rideRepo.findByIdForUpdate(rideId)
                .orElseThrow(() -> new IllegalArgumentException("ride not found"));

        if (!"OPEN".equalsIgnoreCase(ride.getStatus())) {
            throw new ResponseStatusException( HttpStatus.CONFLICT,"Явахад нээлттэй болоогүй байна");
        }
        if (bookingRepo.existsByRideIdAndUserId(rideId, userId)) {
            throw new ResponseStatusException( HttpStatus.CONFLICT,"Аль хэдийн бүртгүүлсэн байна");
        }
        if (ride.getPassengerCount() >= ride.getCapacity()) {
            throw new ResponseStatusException( HttpStatus.CONFLICT,"Хүн дүүрсэн байна (4 passengers max)");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        Booking b = new Booking();
        b.setRide(ride);
        b.setUser(user);
        b.setStatus("BOOKED");
        bookingRepo.save(b);

        ride.setPassengerCount(ride.getPassengerCount() + 1);
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
}
