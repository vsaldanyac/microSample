package com.vic.microproducer.services.impl;

import com.vic.microproducer.model.Band;
import com.vic.microproducer.services.KafkaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Service
public class KafkaSenderImpl implements KafkaSender {

	Logger log = LoggerFactory.getLogger(KafkaSenderImpl.class);

	@Autowired
	private KafkaTemplate kakfaTemplate;

	@Override public void sendMessage(String topic, Band band) {

		ListenableFuture<SendResult<String, Band>> future = kakfaTemplate.send(topic, band);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Band>>() {
			@Override public void onFailure(Throwable ex) {
				log.info("Kafka Failure");
			}
			@Override public void onSuccess(SendResult<String, Band> result) {
				log.info("Kafka Success publishing " + result.getProducerRecord().value().getNombre() + " al tema " + topic);
			}
		});
	}
}
