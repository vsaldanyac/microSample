package com.vic.microconsumer.services.impl;

import com.vic.microconsumer.model.Band;
import com.vic.microconsumer.services.KafkaListenerBands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class KafkaListenerBandsImpl implements KafkaListenerBands {

	Logger log = LoggerFactory.getLogger(KafkaListenerBandsImpl.class);

	List<Band> bandas = new ArrayList<>();

	@KafkaListener(topics="VIC", groupId = "my-group")
	public void listenTopic(Band banda) {
		log.info("INCORPORADA nueva banda: " + banda.getNombre());
		bandas.add(banda);
	}




	@Override public List<Band> getBandas() {
		return bandas;
	}
}
