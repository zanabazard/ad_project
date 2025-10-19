package com.cab21.delivery.dto.request;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CreateRideRequest {
    
    private Long cabId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    private Date startTime;

    private String startPlace;
    private String endPlace;
    private int passengerSeat;
    private BigDecimal ticketPrice;
    private String status;
}
