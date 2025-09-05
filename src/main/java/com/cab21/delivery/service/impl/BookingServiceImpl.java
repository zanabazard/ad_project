package com.cab21.delivery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
import nm.common.grid.repo.GridRepo;
import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

@Service
@RequiredArgsConstructor
@EnableWebMvc
@EnableAsync
public class BookingServiceImpl implements BookingService {

    private final RideRepository rideRepo;
    private final BookingRepository bookingRepo;
    private final UserRepository userRepo;
    @Autowired
    GridRepo gridRepo;

    @Override
    @Transactional
    public Long bookSeat(BookingRequest request, Long userId) {
        // Lock the ride row to prevent overbooking
        Ride ride = rideRepo.findByIdForUpdate(request.getRideId())
                .orElseThrow(() -> new IllegalArgumentException("ride not found"));
        if (ride.getDriverUserId() == userId) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Өөрийн үүсгэсэн зар дээр өөрөө бүртгүүлж болохгүй");
        }
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

        // ride.setPassengerCount(ride.getPassengerCount() + request.getSeatCount());
        // if (ride.getPassengerCount() == ride.getCapacity()) {
        //     ride.setStatus("FULL");
        // }
        // rideRepo.save(ride);
        return b.getId();
    }

    @Override
    public ResponseEntity<String> approveBooking(Long bookingId) {
        Booking b = bookingRepo.findByIdAndStatus(bookingId, "BOOKED")
                .orElseThrow(() -> new IllegalArgumentException("active booking not found"));

        Ride r = b.getRide();
        if (!"OPEN".equalsIgnoreCase(r.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ride is not OPEN");
        }

        b.setStatus("APPROVED");
        bookingRepo.save(b);

        r.setPassengerCount(r.getPassengerCount() + b.getSeat());
        if (r.getPassengerCount() >= r.getCapacity()) {
            r.setStatus("FULL");
        }
        rideRepo.save(r);

        return ResponseEntity.ok("Амжилттай баталгаажлаа");
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

    @Override
    public GridResponse getGrid(GridRequest request) {
   String sql = """
       SELECT
            b.id AS id,                 
            b.ride_id AS ride_id,
            b.user_id AS user_id,
            b.status AS status,
            b.seat AS seat,
            DATE_FORMAT(b.created_at, '%Y-%m-%d %H:%i:%s') as created_at,
            u.first_name AS first_name,
            u.last_name AS last_name,
            u.phone AS phone,
        FROM bookings b
        LEFT JOIN rides r ON b.ride_id = r.id
        LEFT JOIN users u ON u.id = b.user_id
        GROUP BY b.id
        """;
        return gridRepo.getDatatable(sql, request, true);
    }
}
