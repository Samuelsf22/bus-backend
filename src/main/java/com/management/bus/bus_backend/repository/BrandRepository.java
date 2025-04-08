package com.management.bus.bus_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.bus.bus_backend.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    
}
