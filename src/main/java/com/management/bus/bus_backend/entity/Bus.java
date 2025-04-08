package com.management.bus.bus_backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "api_bus")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer busNumber;

    private String licensePlate;

    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String features;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

}
