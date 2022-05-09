package com.vic.microproducer.controllers;

import com.vic.microproducer.model.Band;
import com.vic.microproducer.services.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {


	@Autowired
	KafkaSender kafkaSender;

	@Value("${topic}")
	String topic;

	@PostMapping("v1/test/band")
	public ResponseEntity<?> testController() {
		Band band = new Band();
		band.setNombre("Banda de pruebas");
		band.setEstilo("Metal");
		band.setPais("ES");
		band.setId("0");

		kafkaSender.sendMessage(topic, band);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@PutMapping("v1/test/band")
	public ResponseEntity<?> testControllerPost(@RequestBody Band band) {

		kafkaSender.sendMessage(topic, band);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}
