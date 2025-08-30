package com.cab21.delivery.dto.request;

import lombok.Data;

@Data
public class BookingRequest {
    private Long rideId;
    private Long seatCount;
}
