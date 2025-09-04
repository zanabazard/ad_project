package com.cab21.delivery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import nm.common.grid.repo.GridRepo;
import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

@Service
@EnableWebMvc
@EnableAsync
public class RideImpl implements RideService {

    private final RideRepository rideRepo;

    private final CabRepository cabRepo;

    private final BookingRepository bookingRepo;

    @Autowired
    GridRepo gridRepo;

    public RideImpl(RideRepository rideRepo, CabRepository cabRepo, BookingRepository bookingRepo, GridRepo gridRepo) {
        this.rideRepo = rideRepo;
        this.cabRepo = cabRepo;
        this.bookingRepo = bookingRepo;
        this.gridRepo = gridRepo;
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
        r.setTicketPrice(req.getTicketPrice());
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

    @Override
    public GridResponse getGrid(GridRequest request) {
   String sql = """
       SELECT
            r.id AS id,                 
            r.id AS ride_id,
            r.driver_user_id,
            DATE_FORMAT(r.start_time, '%Y-%m-%d %H:%i:%s') as start_time,
            r.start_place,
            r.end_place,
            r.ticket_price,
            r.status AS ride_status,
            b.id AS booking_id,
            b.user_id AS booking_user_id,
            b.status AS booking_status,
            c.plate,
            c.model,
            r.capacity,
            r.passenger_count,
            u.phone
        FROM rides r
        LEFT JOIN bookings b ON b.ride_id = r.id
        LEFT JOIN cabs c ON c.id = r.cab_id
        LEFT JOIN users u ON u.id = r.driver_user_id
        """;
        return gridRepo.getDatatable(sql, request, true);
    }

    @Override
    public ResponseEntity<String> deleteRide(Long id) {
        Ride r = rideRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ride not found"));
        rideRepo.delete(r);
        return ResponseEntity.ok("Ride deleted successfully");
    }

    @Override
    public ResponseEntity<RideDto> updateRide(Long id, CreateRideRequest req) {
        Ride r = rideRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ride not found"));

        Cab cab = cabRepo.findById(req.getCabId())
                .orElseThrow(() -> new IllegalArgumentException("cab not found"));

        r.setCab(cab);
        r.setCapacity(req.getPassengerSeat());
        r.setStartTime(req.getStartTime());
        r.setStartPlace(req.getStartPlace());
        r.setEndPlace(req.getEndPlace());
        r.setTicketPrice(req.getTicketPrice());
        r.setStatus(req.getStatus());

        rideRepo.save(r);
        return ResponseEntity.ok(RideDto.from(r));
    }
}
