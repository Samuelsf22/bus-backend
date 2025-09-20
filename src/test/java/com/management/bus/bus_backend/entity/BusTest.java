package com.management.bus.bus_backend.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bus Entity Tests")
class BusTest {

    private Bus bus;
    private Brand brand;

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
                .features("Air conditioning, WiFi, GPS")
                .status(true)
                .brand(brand)
                .build();
    }

    @Test
    @DisplayName("Should create Bus with all fields")
    void shouldCreateBusWithAllFields() {
        assertNotNull(bus);
        assertEquals(1L, bus.getId());
        assertEquals(101, bus.getBusNumber());
        assertEquals("ABC-123", bus.getLicensePlate());
        assertEquals("Air conditioning, WiFi, GPS", bus.getFeatures());
        assertTrue(bus.getStatus());
        assertEquals(brand, bus.getBrand());
    }

    @Test
    @DisplayName("Should create Bus using builder pattern")
    void shouldCreateBusUsingBuilder() {
        Bus newBus = Bus.builder()
                .busNumber(202)
                .licensePlate("XYZ-456")
                .features("Wheelchair accessible")
                .status(false)
                .brand(brand)
                .build();

        assertEquals(202, newBus.getBusNumber());
        assertEquals("XYZ-456", newBus.getLicensePlate());
        assertEquals("Wheelchair accessible", newBus.getFeatures());
        assertFalse(newBus.getStatus());
        assertEquals(brand, newBus.getBrand());
    }

    @Test
    @DisplayName("Should create Bus with no args constructor")
    void shouldCreateBusWithNoArgsConstructor() {
        Bus emptyBus = new Bus();
        assertNotNull(emptyBus);
        assertNull(emptyBus.getId());
        assertNull(emptyBus.getBusNumber());
    }

    @Test
    @DisplayName("Should create Bus with all args constructor")
    void shouldCreateBusWithAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Bus newBus = new Bus(2L, 303, "DEF-789", now, "Luxury seats", true, brand);

        assertEquals(2L, newBus.getId());
        assertEquals(303, newBus.getBusNumber());
        assertEquals("DEF-789", newBus.getLicensePlate());
        assertEquals(now, newBus.getCreatedAt());
        assertEquals("Luxury seats", newBus.getFeatures());
        assertTrue(newBus.getStatus());
        assertEquals(brand, newBus.getBrand());
    }

    @Test
    @DisplayName("Should set and get all fields correctly")
    void shouldSetAndGetAllFieldsCorrectly() {
        Bus testBus = new Bus();
        LocalDateTime testTime = LocalDateTime.now();

        testBus.setId(5L);
        testBus.setBusNumber(505);
        testBus.setLicensePlate("TEST-123");
        testBus.setCreatedAt(testTime);
        testBus.setFeatures("Test features");
        testBus.setStatus(false);
        testBus.setBrand(brand);

        assertEquals(5L, testBus.getId());
        assertEquals(505, testBus.getBusNumber());
        assertEquals("TEST-123", testBus.getLicensePlate());
        assertEquals(testTime, testBus.getCreatedAt());
        assertEquals("Test features", testBus.getFeatures());
        assertFalse(testBus.getStatus());
        assertEquals(brand, testBus.getBrand());
    }

    @Test
    @DisplayName("Should handle null brand")
    void shouldHandleNullBrand() {
        Bus busWithNullBrand = Bus.builder()
                .busNumber(404)
                .licensePlate("NULL-000")
                .status(true)
                .brand(null)
                .build();

        assertNull(busWithNullBrand.getBrand());
    }

    @Test
    @DisplayName("Should handle null status")
    void shouldHandleNullStatus() {
        Bus busWithNullStatus = Bus.builder()
                .busNumber(505)
                .licensePlate("STATUS-NULL")
                .brand(brand)
                .status(null)
                .build();

        assertNull(busWithNullStatus.getStatus());
    }
}