package com.cab21.delivery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(
        name = "bookings",
        uniqueConstraints = @UniqueConstraint(name="uq_booking_ride_user", columnNames = {"ride_id","user_id"})
)
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 32)
    private String status = "BOOKED";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;
}
