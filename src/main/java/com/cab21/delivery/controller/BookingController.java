package com.cab21.delivery.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab21.delivery.dto.BookingResult;
import com.cab21.delivery.dto.SeatStatusDto;
import com.cab21.delivery.dto.BookingDto;
import com.cab21.delivery.dto.request.BookingRequest;
import com.cab21.delivery.model.Ride;
import com.cab21.delivery.model.User;
import com.cab21.delivery.repository.RideRepository;
import com.cab21.delivery.repository.UserRepository;
import com.cab21.delivery.service.BookingService;

import lombok.RequiredArgsConstructor;

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
    @PostMapping("/rides/book")
    public ResponseEntity<BookingResult> book(@RequestBody BookingRequest request, Authentication auth) {
        User me = userRepo.findByUsernameAndStatus(auth.getName(), 1)
                .orElseThrow(() -> new IllegalArgumentException("user not found or inactive"));
        Long bookingId = bookingService.bookSeat(request, me.getId());
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

    /***
     * 	   Зар-с хасах сервис
     */
    @PatchMapping("/bookings/{bookingId}/cancel-by-driver")
    public ResponseEntity<Void> cancelByDriver(@RequestBody BookingDto bookingDto) {
    return bookingService.cancelByDriver(bookingDto);
    }
}
