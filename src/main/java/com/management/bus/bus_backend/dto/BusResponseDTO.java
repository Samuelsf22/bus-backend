package com.management.bus.bus_backend.dto;

import com.management.bus.bus_backend.entity.Bus;

public record BusResponseDTO(

    Long id,
    Integer busNumber,
    String licensePlate,
    String features,
    Boolean status,
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
