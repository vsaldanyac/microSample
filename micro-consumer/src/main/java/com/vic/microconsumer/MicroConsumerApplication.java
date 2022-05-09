package com.vic.microconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.vic.microconsumer.controllers", "com.vic.microconsumer.services" })
public class MicroConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroConsumerApplication.class, args);
	}

}
