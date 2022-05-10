package com.vic.microproducer.controllers;

import com.vic.microproducer.model.Band;
import com.vic.microproducer.services.KafkaSender;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
public class TestController {


	Logger log = LoggerFactory.getLogger(TestController.class);

	@Autowired
	KafkaSender kafkaSender;

	@Value("${topic}")
	String topic;

	@PostMapping("v1/test/band")
	public ResponseEntity<?> testController() {
		List<Band> bandas = new ArrayList<>();


		bandas.add(fillBand("Metallica", "Metal", "US", "1"));
		bandas.add(fillBand("Megadeth", "Metal", "US", "2"));
		bandas.add(fillBand("Perfect Smile", "Metal", "ES", "3"));
		bandas.add(fillBand("Zenobia", "Metal", "ES", "4"));
		bandas.add(fillBand("Daeria", "Metal", "ES", "6"));
		bandas.add(fillBand("Mägo de Oz", "Metal", "Es", "7"));

		StringBuilder sb = new StringBuilder();

		/*
			Stream con PREDICADO A MANO FILTER
		 */
		Predicate<Band> predicado = band -> {
			log.info(band.getNombre());
			return band.getPais().equals("ES");
		};
		Optional<Band> optional = bandas.stream().filter(predicado).findFirst();

		sb.append("PREDICADO A MANO de FILTER: La primera del país ES: ");
		sb.append(optional.isPresent() ? optional.get().getNombre() : "NO HAY");

		/*
			Stream clásico FILTER
		 */
		sb.append("\n");
		optional = bandas.stream().filter(band -> band.getPais().equals("ES")).findFirst();

		sb.append("Stream clásico FILTER: La primera del país ES: ");
		sb.append(optional.isPresent() ? optional.get().getNombre() : "NO HAY");

		/*
			Más maneras de hacer stream (from ARRAY) FILTER (OrElse)
		 */
		sb.append("\n");
		Band[] bandasArray = new Band[bandas.size()];
		bandasArray = bandas.toArray(bandasArray);
		Band b = Stream.of(bandasArray).filter(f -> f.getPais().equals("US")).findFirst().orElse(new Band());

		sb.append("Stream from Array FILTER: La primera del país US: ");
		sb.append(b.getNombre());

		/*
			Más maneras de hacer stream (from ARRAY) FILTER
		 */
		sb.append("\n");
		bandasArray = bandas.stream().toArray(Band[]::new);
		optional = Stream.of(bandasArray).filter(f -> f.getPais().equals("US")).findFirst();

		sb.append("Stream from Array FILTER: La primera del país US: ");
		sb.append(optional.isPresent() ? optional.get().getNombre() : "NO HAY");

		/*
			forEach
		 */
		sb.append("\n");
		bandas.stream().forEach(band ->	band.setNombre(band.getNombre() + "."));
		sb.append("Foreach: (punto después del nombre) ");
		sb.append(bandas.get(0).getNombre());

		/*
			Map
		 */
		sb.append("\n");

		List<String> lista = bandas.stream().map(Band::getPais).collect(Collectors.toList());

		sb.append("Map: ");
		sb.append(lista.size() + " - " + lista.get(0));


		/*
			flat Map
			Pendiente de ejemplo
		 */
		/*
			Peek
		 */
		sb.append("\n");

		List<Band> bnds = bandas.stream().peek(band -> band.setId("id. " + band.getId())).peek(band -> band.setPais("P." + band.getPais())).collect(Collectors.toList());

		sb.append("Peek: ");
		sb.append(bnds.size() + " - " + bnds.get(0).getPais());


		/*
			Ordenar con SORTED
		 */
		sb.append("\n");

		bnds = bandas.stream().sorted((o1, o2) -> o1.getNombre().compareTo(o2.getNombre())).collect(Collectors.toList());
		// nueva nomenclatura
		bnds = bandas.stream().sorted(Comparator.comparing(Band::getNombre)).collect(Collectors.toList());

		sb.append("SORTED: ");
		sb.append(bnds.get(0).getNombre());

		/*
			MIN MAX
		 */
		sb.append("\n");

		Band bnd = bandas.stream().min((o1, o2) -> o1.getPais().compareTo(o2.getNombre())).orElseThrow(() -> new NoSuchElementException());
		// nueva nomenclatura
		bnd = bandas.stream().min((o1, o2) -> o1.getNombre().compareTo(o2.getNombre())).orElseThrow(NoSuchElementException::new);


		sb.append("MIN (Max): ");
		sb.append(bnd.getNombre());
		/*
			DISTINCT
		 */

		sb.append("\n");
		bandas.add(fillBand("Zenobia.", "Metal", "P.ES", "id. 4"));

		bnds = bandas.stream().distinct().collect(Collectors.toList());

		sb.append("DISTINCT: ");
		bnds.stream().forEach(band -> sb.append(" : " + band.getNombre()));
		sb.append("IMPORTANTE!!!!: Para que funcione hay que sobreescribir el método equals!!!!");


		return new ResponseEntity<>(sb, HttpStatus.OK);
	}

	@PutMapping("v1/test/band")
	public ResponseEntity<?> testControllerPost(@RequestBody Band band) {

		kafkaSender.sendMessage(topic, band);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	private Band fillBand(String _nombre, String _estilo, String _pais, String _id) {
		Band band = new Band();
		band.setNombre(_nombre);
		band.setEstilo(_estilo);
		band.setPais(_pais);
		band.setId(_id);
		return band;
	}
}
