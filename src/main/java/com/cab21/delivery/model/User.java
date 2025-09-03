package com.cab21.delivery.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "users",
       uniqueConstraints = {
         @UniqueConstraint(columnNames = "username"),
         @UniqueConstraint(columnNames = "email")
       })
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(name = "first_name",nullable = false, length = 255)
    private String firstName;

    @Column(name = "last_name",nullable = false, length = 255)
    private String lastName;

    @Column(name = "birthday",nullable = true)
    private Date birthday;

    @Column(name = "registry_number",nullable = false, length = 255)
    private String registryNumber;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 10)
    private String role;

    @Column(length = 45)
    private String phone;

    @Column(length = 100)
    private int status;

    @Column(length = 100)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
