package com.cab21.delivery.dto.request;

import java.math.BigDecimal;
import java.util.Date;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CreateRideRequest {
    @NotNull
    private Long cabId;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX")
    private Date startTime;

    private String startPlace;
    private String endPlace;
    private Integer passengerSeat;
    private BigDecimal ticketPrice;
    private String status;
}
