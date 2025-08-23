package com.cab21.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingResult {
    private Long bookingId;
    private String status; // "BOOKED"
}
