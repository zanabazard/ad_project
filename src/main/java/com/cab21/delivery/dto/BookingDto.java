package com.cab21.delivery.dto;
import lombok.Builder;
import lombok.Data;
import com.cab21.delivery.model.Booking;

@Data 
@Builder
public class BookingDto {
    private Long id;
    private Long rideId;     
    private Long userId;     

    public static BookingDto from(Booking b) {
        return BookingDto.builder()
            .id(b.getId())
            .rideId(b.getRide().getId())
            .userId(b.getUser().getId())
            .build();
    }
}
