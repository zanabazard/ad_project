package com.cab21.delivery.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cab21.delivery.dto.RideDto;
import com.cab21.delivery.dto.RideWithBookingDto;
import com.cab21.delivery.dto.request.CreateRideRequest;
import com.cab21.delivery.model.Booking;
import com.cab21.delivery.model.Cab;
import com.cab21.delivery.model.Ride;
import com.cab21.delivery.repository.BookingRepository;
import com.cab21.delivery.repository.CabRepository;
import com.cab21.delivery.repository.RideRepository;
import com.cab21.delivery.service.RideService;

@Service
@EnableWebMvc
@EnableAsync
public class RideImpl implements RideService {

    private final RideRepository rideRepo;

    private final CabRepository cabRepo;

    private final BookingRepository bookingRepo;

    public RideImpl(RideRepository rideRepo, CabRepository cabRepo, BookingRepository bookingRepo) {
        this.rideRepo = rideRepo;
        this.cabRepo = cabRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public ResponseEntity<RideDto> create(CreateRideRequest req) {
        Cab cab = cabRepo.findById(req.getCabId())
                .orElseThrow(() -> new IllegalArgumentException("cab not found"));

        Ride r = new Ride();
        r.setCab(cab);
        r.setCapacity(req.getPassengerSeat());
        r.setStartTime(req.getStartTime());
        r.setStartPlace(req.getStartPlace());
        r.setEndPlace(req.getEndPlace());
        r.setStatus("OPEN");

        rideRepo.save(r);
        return ResponseEntity.ok(RideDto.from(r));
    }

    public ResponseEntity<RideWithBookingDto> get(@PathVariable Long id) {
        Ride r = rideRepo.findOneWithCabAndBookings(id)
                .orElseThrow(() -> new IllegalArgumentException("ride not found"));
        List<Booking> bookings = bookingRepo.findByRideId(r.getId());
        return ResponseEntity.ok(RideWithBookingDto.from(r, bookings));
    }

}
