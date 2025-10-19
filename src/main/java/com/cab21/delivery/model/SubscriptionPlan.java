package com.cab21.delivery.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "subscription_plans")
@Data
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false, unique = true)
    private String code;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(name = "duration_months", nullable = false)
    private Short durationMonths;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;
 
}