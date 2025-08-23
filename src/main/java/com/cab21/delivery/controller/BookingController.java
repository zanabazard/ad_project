package com.cab21.delivery.controller;

import com.cab21.delivery.dto.BookingResult;
import com.cab21.delivery.dto.SeatStatusDto;
import com.cab21.delivery.model.Ride;
import com.cab21.delivery.model.User;
import com.cab21.delivery.repository.RideRepository;
import com.cab21.delivery.repository.UserRepository;
import com.cab21.delivery.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserRepository userRepo;
    private final RideRepository rideRepo;

    /***
     * 	   Зар-нд бүртгүүлэх сервис
     */
    @PostMapping("/rides/{rideId}/book")
    public ResponseEntity<BookingResult> book(@PathVariable Long rideId, Authentication auth) {
        User me = userRepo.findByUsernameAndStatus(auth.getName(), 1)
                .orElseThrow(() -> new IllegalArgumentException("user not found or inactive"));
        Long bookingId = bookingService.bookSeat(rideId, me.getId());
        return ResponseEntity.ok(new BookingResult(bookingId, "BOOKED"));
    }
    /***
     * 	   Бүртгүүлсэн хүн хасах сервис
     */
    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<Void> cancel(@PathVariable Long bookingId, Authentication auth) {
        User me = userRepo.findByUsernameAndStatus(auth.getName(), 1)
                .orElseThrow(() -> new IllegalArgumentException("user not found or inactive"));
        bookingService.cancelSeat(bookingId, me.getId());
        return ResponseEntity.noContent().build();
    }

    /***
     * 	   Зар-нд хэдэн суудал байгааг авах сервис
     */
    @GetMapping("/rides/{rideId}/seats")
    public ResponseEntity<SeatStatusDto> seats(@PathVariable Long rideId) {
        Ride r = rideRepo.findById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("ride not found"));
        return ResponseEntity.ok(new SeatStatusDto(
                r.getCapacity(),
                r.getPassengerCount(),
                Math.max(0, r.getCapacity() - r.getPassengerCount())
        ));
    }
}
