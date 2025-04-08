package com.management.bus.bus_backend.service;

import java.util.List;

import com.management.bus.bus_backend.dto.BusResponseDTO;

public interface BusService {

    List<BusResponseDTO> getAllBuses();

    BusResponseDTO getBusById(Long id);
}
