package com.vic.microconsumer.controllers;

import com.vic.microconsumer.services.KafkaListenerBands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class BandController {

	@Autowired
	KafkaListenerBands kafkaListener;

	@GetMapping("v1/bands")
	public ResponseEntity<?> getBands() {
		return new ResponseEntity<>(kafkaListener.getBandas(), HttpStatus.OK);
	}
}