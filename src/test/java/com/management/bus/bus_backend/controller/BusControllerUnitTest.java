package com.management.bus.bus_backend.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.management.bus.bus_backend.dto.BusResponseDTO;
import com.management.bus.bus_backend.service.BusService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BusController Tests")
class BusControllerUnitTest {

    @Mock
    private BusService busService;

    @InjectMocks
    private BusController busController;

    private BusResponseDTO busResponseDTO;
    private Page<BusResponseDTO> busPage;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        busResponseDTO = new BusResponseDTO(
                1L,
                101,
                "ABC-123",
                "Air conditioning, WiFi",
                true,
                "Mercedes-Benz"
        );

        pageable = PageRequest.of(0, 10);
        busPage = new PageImpl<>(List.of(busResponseDTO), pageable, 1);
    }

    @Test
    @DisplayName("Should return 200 OK with buses when getAllBuses is called and buses exist")
    void shouldReturn200OkWithBusesWhenGetAllBusesIsCalledAndBusesExist() {
        // Given
        when(busService.getAllBuses(any(Pageable.class))).thenReturn(busPage);

        // When
        ResponseEntity<Page<BusResponseDTO>> response = busController.getAllBuses(pageable);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(busResponseDTO, response.getBody().getContent().get(0));

        verify(busService, times(1)).getAllBuses(pageable);
    }

    @Test
    @DisplayName("Should return 204 No Content when getAllBuses is called and no buses exist")
    void shouldReturn204NoContentWhenGetAllBusesIsCalledAndNoBusesExist() {
        // Given
        Page<BusResponseDTO> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        when(busService.getAllBuses(any(Pageable.class))).thenReturn(emptyPage);

        // When
        ResponseEntity<Page<BusResponseDTO>> response = busController.getAllBuses(pageable);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(busService, times(1)).getAllBuses(pageable);
    }

    @Test
    @DisplayName("Should return 200 OK with multiple buses when getAllBuses is called")
    void shouldReturn200OkWithMultipleBusesWhenGetAllBusesIsCalled() {
        // Given
        BusResponseDTO busResponseDTO2 = new BusResponseDTO(
                2L,
                102,
                "XYZ-456",
                "GPS, Wheelchair accessible",
                false,
                "Volvo"
        );

        Page<BusResponseDTO> multipleBusesPage = new PageImpl<>(
                List.of(busResponseDTO, busResponseDTO2), 
                pageable, 
                2
        );
        
        when(busService.getAllBuses(any(Pageable.class))).thenReturn(multipleBusesPage);

        // When
        ResponseEntity<Page<BusResponseDTO>> response = busController.getAllBuses(pageable);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals(2, response.getBody().getContent().size());

        verify(busService, times(1)).getAllBuses(pageable);
    }

    @Test
    @DisplayName("Should return 200 OK with bus when getBusById is called with existing id")
    void shouldReturn200OkWithBusWhenGetBusByIdIsCalledWithExistingId() {
        // Given
        Long busId = 1L;
        when(busService.getBusById(busId)).thenReturn(busResponseDTO);

        // When
        ResponseEntity<BusResponseDTO> response = busController.getBusById(busId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(busResponseDTO, response.getBody());

        verify(busService, times(1)).getBusById(busId);
    }

    @Test
    @DisplayName("Should return 404 Not Found when getBusById is called with non-existing id")
    void shouldReturn404NotFoundWhenGetBusByIdIsCalledWithNonExistingId() {
        // Given
        Long nonExistingId = 999L;
        when(busService.getBusById(nonExistingId)).thenReturn(null);

        // When
        ResponseEntity<BusResponseDTO> response = busController.getBusById(nonExistingId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(busService, times(1)).getBusById(nonExistingId);
    }

    @Test
    @DisplayName("Should handle service exception in getAllBuses")
    void shouldHandleServiceExceptionInGetAllBuses() {
        // Given
        when(busService.getAllBuses(any(Pageable.class)))
                .thenThrow(new RuntimeException("Service error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> busController.getAllBuses(pageable));

        verify(busService, times(1)).getAllBuses(pageable);
    }

    @Test
    @DisplayName("Should handle service exception in getBusById")
    void shouldHandleServiceExceptionInGetBusById() {
        // Given
        Long busId = 1L;
        when(busService.getBusById(busId)).thenThrow(new RuntimeException("Service error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> busController.getBusById(busId));

        verify(busService, times(1)).getBusById(busId);
    }

    @Test
    @DisplayName("Should use provided pageable in getAllBuses")
    void shouldUseProvidedPageableInGetAllBuses() {
        // Given
        Pageable customPageable = PageRequest.of(2, 5);
        when(busService.getAllBuses(customPageable)).thenReturn(busPage);

        // When
        ResponseEntity<Page<BusResponseDTO>> response = busController.getAllBuses(customPageable);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(busService, times(1)).getAllBuses(customPageable);
    }

    @Test
    @DisplayName("Should handle null response from service in getBusById")
    void shouldHandleNullResponseFromServiceInGetBusById() {
        // Given
        Long busId = 1L;
        when(busService.getBusById(busId)).thenReturn(null);

        // When
        ResponseEntity<BusResponseDTO> response = busController.getBusById(busId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(busService, times(1)).getBusById(busId);
    }

    @Test
    @DisplayName("Should handle empty page correctly in getAllBuses")
    void shouldHandleEmptyPageCorrectlyInGetAllBuses() {
        // Given
        Page<BusResponseDTO> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        when(busService.getAllBuses(any(Pageable.class))).thenReturn(emptyPage);

        // When
        ResponseEntity<Page<BusResponseDTO>> response = busController.getAllBuses(pageable);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(response.hasBody());

        verify(busService, times(1)).getAllBuses(pageable);
    }
}