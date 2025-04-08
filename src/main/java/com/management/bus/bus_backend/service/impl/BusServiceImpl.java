package com.management.bus.bus_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.bus.bus_backend.dto.BusResponseDTO;
import com.management.bus.bus_backend.repository.BusRepository;
import com.management.bus.bus_backend.service.BusService;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Override
    public List<BusResponseDTO> getAllBuses() {
        return busRepository.findAll().stream().map(BusResponseDTO::from).toList();
    }

    @Override
    public BusResponseDTO getBusById(Long id) {
        return busRepository.findById(id).map(BusResponseDTO::from).orElse(null);
    }

}
