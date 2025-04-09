package com.management.bus.bus_backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.management.bus.bus_backend.entity.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> {

    Page<Bus> findAll(Pageable pageable);
     
}
