package com.vic.microproducer.services;

import com.vic.microproducer.model.Band;


public interface KafkaSender {
	void sendMessage(String topic, Band band);
}
