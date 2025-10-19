package com.cab21.delivery.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

import com.cab21.delivery.model.enums.ActorType;
import com.cab21.delivery.model.enums.PaymentEvent;
import com.cab21.delivery.model.enums.PaymentStatus;

@Entity
@Table(name = "payment_logs", indexes = {
        @Index(name="idx_payment_logs_payment", columnList = "payment_id"),
        @Index(name="idx_payment_logs_created_at", columnList = "created_at")
})
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class PaymentLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id", nullable = false)
    private Long paymentId; 

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private PaymentStatus status;

    @Column(length = 255)
    private String message;

    @Lob
    @Column(name = "data_json")
    private String dataJson;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor_type", length = 16, nullable = false)
    private ActorType actorType;

    @Column(name = "actor_id")
    private Long actorId;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "event", length = 20)
    private PaymentEvent event;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = new Date();
    }
}
