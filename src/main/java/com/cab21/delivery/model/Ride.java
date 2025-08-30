package com.cab21.delivery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "rides")
@Data
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cab_id")
    private Cab cab;

    @OneToMany(mappedBy = "ride", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference 
    private List<Booking> bookings = new ArrayList<>();

    @Column(name = "driver_user_id")
    private Long driverUserId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "start_place")
    private String startPlace;
    @Column(name = "end_place")
    private String endPlace;

    @Column(nullable = false)
    private int capacity = 4;

    @Column(name = "passenger_count", nullable = false)
    private int passengerCount = 0;

    @Column(nullable = false, length = 32)
    private String status = "OPEN";
}
