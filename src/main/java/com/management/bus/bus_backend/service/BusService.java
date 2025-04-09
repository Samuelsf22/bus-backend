package com.management.bus.bus_backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.management.bus.bus_backend.dto.BusResponseDTO;

public interface BusService {

    Page<BusResponseDTO> getAllBuses(Pageable pageable);

    BusResponseDTO getBusById(Long id);
}
