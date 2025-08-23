package com.cab21.delivery.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "cabs")
@Data
public class Cab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=16, unique=true)
    private String plate;

    @Column(nullable=false, length=64)
    private String model;

    @Column(name="driver_name", nullable=false, length=64)
    private String driverName;

    @Column(name="driver_id", nullable=false, length=64)
    private Long driverId;

    @Column(name="passenger_seats", nullable=false)
    private Integer passengerSeats = 4;

    @Column(nullable=false)
    private Integer status = 1;

    @CreationTimestamp
    @Column(name = "created_at",updatable=false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
