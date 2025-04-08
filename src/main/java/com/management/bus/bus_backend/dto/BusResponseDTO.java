package com.management.bus.bus_backend.dto;

import com.management.bus.bus_backend.entity.Bus;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BusResponseDTO(

    Long id,

    @JsonProperty("bus_number")
    Integer busNumber,

    @JsonProperty("license_plate")
    String licensePlate,

    
    String features,
    
    Boolean status,
    
    @JsonProperty("brand_name")
    String brandName

) {

    public static BusResponseDTO from(Bus bus) {
        return new BusResponseDTO(
            bus.getId(),
            bus.getBusNumber(),
            bus.getLicensePlate(),
            bus.getFeatures(),
            bus.getStatus(),
            bus.getBrand().getName()
        );
    }

}
