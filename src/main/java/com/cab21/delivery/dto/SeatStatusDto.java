package com.cab21.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatStatusDto {
    private int capacity;
    private int taken;
    private int available;
}
