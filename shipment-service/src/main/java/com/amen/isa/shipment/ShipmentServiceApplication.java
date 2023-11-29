package com.amen.isa.shipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableMongoAuditing
@SpringBootApplication
@EnableReactiveMongoRepositories
public class ShipmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipmentServiceApplication.class, args);
	}

}
