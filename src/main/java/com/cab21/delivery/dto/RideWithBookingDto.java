package com.cab21.delivery.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cab21.delivery.model.Booking;
import com.cab21.delivery.model.Ride;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideWithBookingDto {
    private Long id;
    private Long cabId;
    private Date startTime;
    private String startPlace;
    private String endPlace;
    private String status;
    private int capacity;
    private int passengerCount;
    private BigDecimal ticketPrice;
    private List<Booking> booking;

    public static RideWithBookingDto from(Ride r, List<Booking> b) {
        return RideWithBookingDto.builder()
                .id(r.getId())
                .cabId(r.getCab().getId())
                .startTime(r.getStartTime())
                .startPlace(r.getStartPlace())
                .endPlace(r.getEndPlace())
                .status(r.getStatus())
                .capacity(r.getCapacity())
                .passengerCount(r.getPassengerCount())
                .ticketPrice(r.getTicketPrice())
                .booking(b)
                .build();
    }
}
