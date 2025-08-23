package com.cab21.delivery.dto;

import com.cab21.delivery.model.Ride;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideDto {
    private Long id;
    private Long cabId;
    private Date startTime;
    private String startPlace;
    private String endPlace;
    private String status;
    private int capacity;
    private int passengerCount;

    public static RideDto from(Ride r) {
        return RideDto.builder()
                .id(r.getId())
                .cabId(r.getCab().getId())
                .startTime(r.getStartTime())
                .startPlace(r.getStartPlace())
                .endPlace(r.getEndPlace())
                .status(r.getStatus())
                .capacity(r.getCapacity())
                .passengerCount(r.getPassengerCount())
                .build();
    }
}
