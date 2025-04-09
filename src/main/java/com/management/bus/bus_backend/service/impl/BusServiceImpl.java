package com.management.bus.bus_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.management.bus.bus_backend.dto.BusResponseDTO;
import com.management.bus.bus_backend.repository.BusRepository;
import com.management.bus.bus_backend.service.BusService;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Override
    public Page<BusResponseDTO> getAllBuses(Pageable pageable) {
        return busRepository.findAll(pageable).map(BusResponseDTO::from);
    }

    @Override
    public BusResponseDTO getBusById(Long id) {
        return busRepository.findById(id).map(BusResponseDTO::from).orElse(null);
    }

}
