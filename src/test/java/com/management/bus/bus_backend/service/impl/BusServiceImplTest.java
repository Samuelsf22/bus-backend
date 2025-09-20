package com.management.bus.bus_backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.management.bus.bus_backend.dto.BusResponseDTO;
import com.management.bus.bus_backend.entity.Brand;
import com.management.bus.bus_backend.entity.Bus;
import com.management.bus.bus_backend.repository.BusRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BusServiceImpl Tests")
class BusServiceImplTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusServiceImpl busService;

    private Bus bus;
    private Brand brand;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        brand = Brand.builder()
                .id(1L)
                .name("Mercedes-Benz")
                .build();

        bus = Bus.builder()
                .id(1L)
                .busNumber(101)
                .licensePlate("ABC-123")
                .createdAt(LocalDateTime.now())
                .features("Air conditioning, WiFi")
                .status(true)
                .brand(brand)
                .build();

        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("Should return page of buses when getAllBuses is called")
    void shouldReturnPageOfBusesWhenGetAllBusesIsCalled() {
        // Given
        List<Bus> busList = List.of(bus);
        Page<Bus> busPage = new PageImpl<>(busList, pageable, 1);
        
        when(busRepository.findAll(pageable)).thenReturn(busPage);

        // When
        Page<BusResponseDTO> result = busService.getAllBuses(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        
        BusResponseDTO busDto = result.getContent().get(0);
        assertEquals(bus.getId(), busDto.id());
        assertEquals(bus.getBusNumber(), busDto.busNumber());
        assertEquals(bus.getLicensePlate(), busDto.licensePlate());
        assertEquals(bus.getFeatures(), busDto.features());
        assertEquals(bus.getStatus(), busDto.status());
        assertEquals(brand.getName(), busDto.brandName());

        verify(busRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should return empty page when no buses exist")
    void shouldReturnEmptyPageWhenNoBusesExist() {
        // Given
        Page<Bus> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        when(busRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        Page<BusResponseDTO> result = busService.getAllBuses(pageable);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());

        verify(busRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should return multiple buses when getAllBuses is called")
    void shouldReturnMultipleBusesWhenGetAllBusesIsCalled() {
        // Given
        Brand brand2 = Brand.builder().id(2L).name("Volvo").build();
        Bus bus2 = Bus.builder()
                .id(2L)
                .busNumber(102)
                .licensePlate("XYZ-456")
                .features("GPS, Wheelchair accessible")
                .status(false)
                .brand(brand2)
                .build();

        List<Bus> busList = List.of(bus, bus2);
        Page<Bus> busPage = new PageImpl<>(busList, pageable, 2);
        
        when(busRepository.findAll(pageable)).thenReturn(busPage);

        // When
        Page<BusResponseDTO> result = busService.getAllBuses(pageable);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());

        verify(busRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should return bus when getBusById is called with existing id")
    void shouldReturnBusWhenGetBusByIdIsCalledWithExistingId() {
        // Given
        Long busId = 1L;
        when(busRepository.findById(busId)).thenReturn(Optional.of(bus));

        // When
        BusResponseDTO result = busService.getBusById(busId);

        // Then
        assertNotNull(result);
        assertEquals(bus.getId(), result.id());
        assertEquals(bus.getBusNumber(), result.busNumber());
        assertEquals(bus.getLicensePlate(), result.licensePlate());
        assertEquals(bus.getFeatures(), result.features());
        assertEquals(bus.getStatus(), result.status());
        assertEquals(brand.getName(), result.brandName());

        verify(busRepository, times(1)).findById(busId);
    }

    @Test
    @DisplayName("Should return null when getBusById is called with non-existing id")
    void shouldReturnNullWhenGetBusByIdIsCalledWithNonExistingId() {
        // Given
        Long nonExistingId = 999L;
        when(busRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When
        BusResponseDTO result = busService.getBusById(nonExistingId);

        // Then
        assertNull(result);

        verify(busRepository, times(1)).findById(nonExistingId);
    }

    @Test
    @DisplayName("Should handle null id in getBusById")
    void shouldHandleNullIdInGetBusById() {
        // Given
        when(busRepository.findById(null)).thenReturn(Optional.empty());

        // When
        BusResponseDTO result = busService.getBusById(null);

        // Then
        assertNull(result);

        verify(busRepository, times(1)).findById(null);
    }

    @Test
    @DisplayName("Should handle repository exception in getAllBuses")
    void shouldHandleRepositoryExceptionInGetAllBuses() {
        // Given
        when(busRepository.findAll(pageable)).thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> busService.getAllBuses(pageable));

        verify(busRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should handle repository exception in getBusById")
    void shouldHandleRepositoryExceptionInGetBusById() {
        // Given
        Long busId = 1L;
        when(busRepository.findById(busId)).thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> busService.getBusById(busId));

        verify(busRepository, times(1)).findById(busId);
    }
}