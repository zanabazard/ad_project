package com.cab21.delivery.dto.request;

import lombok.Data;

@Data
public class CreateCabRequest {
    private String plate;
    private String model;
    private String driverName;
    private Long driverId;
    private Integer passengerSeats;

}
