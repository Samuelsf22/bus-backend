package com.management.bus.bus_backend;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.management.bus.bus_backend.entity.Brand;
import com.management.bus.bus_backend.entity.Bus;
import com.management.bus.bus_backend.repository.BrandRepository;
import com.management.bus.bus_backend.repository.BusRepository;

@SpringBootApplication
public class BusBackendApplication implements CommandLineRunner {

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private BusRepository busRepository;

	public static void main(String[] args) {
		SpringApplication.run(BusBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (busRepository.count() == 0) {
			Brand volvo = Brand.builder()
					.name("Volvo")
					.build();

			Brand scania = Brand.builder()
					.name("Scania")
					.build();

			Brand fiat = Brand.builder()
					.name("Fiat")
					.build();

			brandRepository.saveAll(Arrays.asList(volvo, scania, fiat));

			for (int i = 1; i <= 10; i++) {
				Brand brand = (i % 3 == 0) ? volvo : (i % 3 == 1) ? scania : fiat;

				Bus bus = Bus.builder()
						.busNumber(1000 + i)
						.licensePlate("ABC-" + (100 + i))
						.features("Bus features " + i)
						.status(i % 2 == 0)
						.brand(brand)
						.build();

				busRepository.save(bus);
			}
		}
	}
}
