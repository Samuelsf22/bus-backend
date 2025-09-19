package com.management.bus.bus_backend.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Brand Entity Tests")
class BrandTest {

    private Brand brand;
    private List<Bus> buses;

    @BeforeEach
    void setUp() {
        buses = new ArrayList<>();
        
        brand = Brand.builder()
                .id(1L)
                .name("Mercedes-Benz")
                .buses(buses)
                .build();
    }

    @Test
    @DisplayName("Should create Brand with all fields")
    void shouldCreateBrandWithAllFields() {
        assertNotNull(brand);
        assertEquals(1L, brand.getId());
        assertEquals("Mercedes-Benz", brand.getName());
        assertEquals(buses, brand.getBuses());
    }

    @Test
    @DisplayName("Should create Brand using builder pattern")
    void shouldCreateBrandUsingBuilder() {
        Brand newBrand = Brand.builder()
                .id(2L)
                .name("Volvo")
                .buses(new ArrayList<>())
                .build();

        assertEquals(2L, newBrand.getId());
        assertEquals("Volvo", newBrand.getName());
        assertNotNull(newBrand.getBuses());
        assertTrue(newBrand.getBuses().isEmpty());
    }

    @Test
    @DisplayName("Should create Brand with no args constructor")
    void shouldCreateBrandWithNoArgsConstructor() {
        Brand emptyBrand = new Brand();
        assertNotNull(emptyBrand);
        assertNull(emptyBrand.getId());
        assertNull(emptyBrand.getName());
        assertNull(emptyBrand.getBuses());
    }

    @Test
    @DisplayName("Should create Brand with all args constructor")
    void shouldCreateBrandWithAllArgsConstructor() {
        List<Bus> testBuses = new ArrayList<>();
        Brand newBrand = new Brand(3L, "Toyota", testBuses);

        assertEquals(3L, newBrand.getId());
        assertEquals("Toyota", newBrand.getName());
        assertEquals(testBuses, newBrand.getBuses());
    }

    @Test
    @DisplayName("Should set and get all fields correctly")
    void shouldSetAndGetAllFieldsCorrectly() {
        Brand testBrand = new Brand();
        List<Bus> testBuses = new ArrayList<>();

        testBrand.setId(10L);
        testBrand.setName("Scania");
        testBrand.setBuses(testBuses);

        assertEquals(10L, testBrand.getId());
        assertEquals("Scania", testBrand.getName());
        assertEquals(testBuses, testBrand.getBuses());
    }

    @Test
    @DisplayName("Should handle buses list operations")
    void shouldHandleBusesListOperations() {
        Bus bus1 = Bus.builder()
                .id(1L)
                .busNumber(101)
                .licensePlate("BUS-001")
                .brand(brand)
                .build();

        Bus bus2 = Bus.builder()
                .id(2L)
                .busNumber(102)
                .licensePlate("BUS-002")
                .brand(brand)
                .build();

        List<Bus> busList = new ArrayList<>();
        busList.add(bus1);
        busList.add(bus2);

        brand.setBuses(busList);

        assertNotNull(brand.getBuses());
        assertEquals(2, brand.getBuses().size());
        assertTrue(brand.getBuses().contains(bus1));
        assertTrue(brand.getBuses().contains(bus2));
    }

    @Test
    @DisplayName("Should handle null buses list")
    void shouldHandleNullBusesList() {
        Brand brandWithNullBuses = Brand.builder()
                .id(5L)
                .name("Iveco")
                .buses(null)
                .build();

        assertNull(brandWithNullBuses.getBuses());
    }

    @Test
    @DisplayName("Should handle empty buses list")
    void shouldHandleEmptyBusesList() {
        Brand brandWithEmptyBuses = Brand.builder()
                .id(6L)
                .name("MAN")
                .buses(new ArrayList<>())
                .build();

        assertNotNull(brandWithEmptyBuses.getBuses());
        assertTrue(brandWithEmptyBuses.getBuses().isEmpty());
        assertEquals(0, brandWithEmptyBuses.getBuses().size());
    }

    @Test
    @DisplayName("Should handle null name")
    void shouldHandleNullName() {
        Brand brandWithNullName = Brand.builder()
                .id(7L)
                .name(null)
                .buses(new ArrayList<>())
                .build();

        assertNull(brandWithNullName.getName());
    }

    @Test
    @DisplayName("Should handle empty name")
    void shouldHandleEmptyName() {
        Brand brandWithEmptyName = Brand.builder()
                .id(8L)
                .name("")
                .buses(new ArrayList<>())
                .build();

        assertEquals("", brandWithEmptyName.getName());
        assertTrue(brandWithEmptyName.getName().isEmpty());
    }
}