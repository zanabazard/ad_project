package com.cab21.delivery.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

import com.cab21.delivery.model.enums.PaymentStatus;

@Entity
@Table(name = "payments",
       indexes = {
           @Index(name="idx_payments_status", columnList = "status"),
           @Index(name="idx_payments_created_at", columnList = "created_at")
       })
@Data
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "amount_cents")
    private Long amountCents;

    @Column(length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentStatus status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
