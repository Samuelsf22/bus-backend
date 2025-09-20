package com.management.bus.bus_backend.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.management.bus.bus_backend.entity.Brand;
import com.management.bus.bus_backend.entity.Bus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BusResponseDTO Tests")
class BusResponseDTOTest {

    private Bus bus;
    private Brand brand;
    private BusResponseDTO busResponseDTO;

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
                .features("Air conditioning, WiFi, GPS")
                .status(true)
                .brand(brand)
                .build();

        busResponseDTO = new BusResponseDTO(
                1L,
                101,
                "ABC-123",
                "Air conditioning, WiFi, GPS",
                true,
                "Mercedes-Benz"
        );
    }

    @Test
    @DisplayName("Should create BusResponseDTO with all fields")
    void shouldCreateBusResponseDTOWithAllFields() {
        assertNotNull(busResponseDTO);
        assertEquals(1L, busResponseDTO.id());
        assertEquals(101, busResponseDTO.busNumber());
        assertEquals("ABC-123", busResponseDTO.licensePlate());
        assertEquals("Air conditioning, WiFi, GPS", busResponseDTO.features());
        assertTrue(busResponseDTO.status());
        assertEquals("Mercedes-Benz", busResponseDTO.brandName());
    }

    @Test
    @DisplayName("Should create BusResponseDTO from Bus entity using from method")
    void shouldCreateBusResponseDTOFromBusEntityUsingFromMethod() {
        // When
        BusResponseDTO result = BusResponseDTO.from(bus);

        // Then
        assertNotNull(result);
        assertEquals(bus.getId(), result.id());
        assertEquals(bus.getBusNumber(), result.busNumber());
        assertEquals(bus.getLicensePlate(), result.licensePlate());
        assertEquals(bus.getFeatures(), result.features());
        assertEquals(bus.getStatus(), result.status());
        assertEquals(bus.getBrand().getName(), result.brandName());
    }

    @Test
    @DisplayName("Should handle null values in BusResponseDTO")
    void shouldHandleNullValuesInBusResponseDTO() {
        BusResponseDTO nullValuesDTO = new BusResponseDTO(
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertNull(nullValuesDTO.id());
        assertNull(nullValuesDTO.busNumber());
        assertNull(nullValuesDTO.licensePlate());
        assertNull(nullValuesDTO.features());
        assertNull(nullValuesDTO.status());
        assertNull(nullValuesDTO.brandName());
    }

    @Test
    @DisplayName("Should create BusResponseDTO with false status")
    void shouldCreateBusResponseDTOWithFalseStatus() {
        BusResponseDTO falseStatusDTO = new BusResponseDTO(
                2L,
                102,
                "XYZ-456",
                "Wheelchair accessible",
                false,
                "Volvo"
        );

        assertFalse(falseStatusDTO.status());
        assertEquals(2L, falseStatusDTO.id());
    }

    @Test
    @DisplayName("Should create BusResponseDTO with empty features")
    void shouldCreateBusResponseDTOWithEmptyFeatures() {
        BusResponseDTO emptyFeaturesDTO = new BusResponseDTO(
                3L,
                103,
                "DEF-789",
                "",
                true,
                "Toyota"
        );

        assertEquals("", emptyFeaturesDTO.features());
        assertTrue(emptyFeaturesDTO.features().isEmpty());
    }

    @Test
    @DisplayName("Should create BusResponseDTO from Bus with minimal data")
    void shouldCreateBusResponseDTOFromBusWithMinimalData() {
        // Given
        Bus minimalBus = Bus.builder()
                .id(5L)
                .brand(brand)
                .build();

        // When
        BusResponseDTO result = BusResponseDTO.from(minimalBus);

        // Then
        assertNotNull(result);
        assertEquals(5L, result.id());
        assertNull(result.busNumber());
        assertNull(result.licensePlate());
        assertNull(result.features());
        assertNull(result.status());
        assertEquals("Mercedes-Benz", result.brandName());
    }

    @Test
    @DisplayName("Should handle Bus with different brand in from method")
    void shouldHandleBusWithDifferentBrandInFromMethod() {
        // Given
        Brand volvoBrand = Brand.builder()
                .id(2L)
                .name("Volvo")
                .build();

        Bus volvoBus = Bus.builder()
                .id(10L)
                .busNumber(200)
                .licensePlate("VOLVO-001")
                .features("Luxury seats, Entertainment system")
                .status(false)
                .brand(volvoBrand)
                .build();

        // When
        BusResponseDTO result = BusResponseDTO.from(volvoBus);

        // Then
        assertNotNull(result);
        assertEquals(10L, result.id());
        assertEquals(200, result.busNumber());
        assertEquals("VOLVO-001", result.licensePlate());
        assertEquals("Luxury seats, Entertainment system", result.features());
        assertFalse(result.status());
        assertEquals("Volvo", result.brandName());
    }

    @Test
    @DisplayName("Should test record equality")
    void shouldTestRecordEquality() {
        BusResponseDTO dto1 = new BusResponseDTO(
                1L,
                101,
                "ABC-123",
                "Air conditioning, WiFi, GPS",
                true,
                "Mercedes-Benz"
        );

        BusResponseDTO dto2 = new BusResponseDTO(
                1L,
                101,
                "ABC-123",
                "Air conditioning, WiFi, GPS",
                true,
                "Mercedes-Benz"
        );

        BusResponseDTO dto3 = new BusResponseDTO(
                2L,
                102,
                "XYZ-456",
                "GPS only",
                false,
                "Volvo"
        );

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    @DisplayName("Should test record toString method")
    void shouldTestRecordToStringMethod() {
        String expectedToString = busResponseDTO.toString();
        
        assertNotNull(expectedToString);
        assertTrue(expectedToString.contains("BusResponseDTO"));
        assertTrue(expectedToString.contains("id=1"));
        assertTrue(expectedToString.contains("busNumber=101"));
        assertTrue(expectedToString.contains("licensePlate=ABC-123"));
        assertTrue(expectedToString.contains("features=Air conditioning, WiFi, GPS"));
        assertTrue(expectedToString.contains("status=true"));
        assertTrue(expectedToString.contains("brandName=Mercedes-Benz"));
    }

    @Test
    @DisplayName("Should handle very long features string")
    void shouldHandleVeryLongFeaturesString() {
        String longFeatures = "Air conditioning, WiFi, GPS, Wheelchair accessible, " +
                "Entertainment system, USB charging ports, LED lighting, " +
                "Security cameras, Emergency exits, First aid kit, " +
                "Comfortable seating, Luggage compartment, Climate control";

        BusResponseDTO longFeaturesDTO = new BusResponseDTO(
                4L,
                104,
                "LONG-001",
                longFeatures,
                true,
                "Scania"
        );

        assertEquals(longFeatures, longFeaturesDTO.features());
        assertTrue(longFeaturesDTO.features().length() > 100);
    }
}