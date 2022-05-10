package com.vic.microconsumer.services.impl;

import com.vic.microconsumer.model.Grupo;
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

	List<Grupo> grupos = new ArrayList<>();

	@KafkaListener(topics="VIC", groupId = "my-group")
	public void listenTopic(Grupo grupo) {
		log.info("INCORPORADA nuevo grupo: " + grupo.getNombre());
		grupos.add(grupo);
	}




	@Override public List<Grupo> getGrupos() {
		return grupos;
	}
}
