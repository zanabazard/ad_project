package com.cab21.delivery.model;

import java.io.Serializable;
import java.util.Date;

import com.cab21.delivery.model.enums.DriverSubscriptionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "driver_subscriptions")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)     // ✅ JPA needs this
@AllArgsConstructor                                   // for @Builder
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)     // avoid touching LAZY fields
public class DriverSubscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "driver_id", nullable = false)
    private Long driverId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private SubscriptionPlan plan;

    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "starts_at", nullable = false)
    private Date startsAt;

    @Column(name = "ends_at", nullable = false)
    private Date endsAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16, nullable = false)
    private DriverSubscriptionStatus status;

    @Builder.Default                                       // ✅ keep default when using builder
    @Column(name = "auto_renew", nullable = false)
    private Boolean autoRenew = Boolean.FALSE;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = new Date();
        if (status == null) status = DriverSubscriptionStatus.PENDING;  // optional: sane default
        if (autoRenew == null) autoRenew = Boolean.FALSE;               // belt & suspenders
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = new Date();
    }
}
