package com.cab21.delivery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "soums")
@Data
public class Soum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "value", length = 10, nullable = false)
    private String value;

    @Column(name = "parent_id", length = 10, nullable = false)
    private String parentId;

   
}
