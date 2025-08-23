package com.cab21.delivery.dto;

import com.cab21.delivery.model.Cab;
import lombok.Data;

@Data
public class CabDto {
    private Long id;
    private String plate;
    private String model;
    private String driverName;
    private Integer passengerSeats;
    private Integer status;

    public static CabDto from(Cab c) {
        CabDto d = new CabDto();
        d.setId(c.getId());
        d.setPlate(c.getPlate());
        d.setModel(c.getModel());
        d.setDriverName(c.getDriverName());
        d.setPassengerSeats(c.getPassengerSeats());
        d.setStatus(c.getStatus());
        return d;
    }
}
