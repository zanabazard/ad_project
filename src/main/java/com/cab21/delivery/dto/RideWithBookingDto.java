package com.cab21.delivery.dto;

import com.cab21.delivery.model.Ride;
import com.cab21.delivery.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import java.util.Date;

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
                .booking(b)
                .build();
    }
}
