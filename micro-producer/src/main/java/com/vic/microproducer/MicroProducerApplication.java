package com.vic.microproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.vic.microproducer.controllers", "com.vic.microproducer.services" })
public class MicroProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroProducerApplication.class, args);
	}

}
