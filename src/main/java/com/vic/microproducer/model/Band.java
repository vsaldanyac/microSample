package com.vic.microproducer.model;

public class Band {

	private String id;
	private String nombre;
	private String estilo;
	private String pais;

	public Band(String _id, String _nombre, String _estilo, String _pais) {
		this.pais = _pais;
		this.id = _id;
		this.estilo = _estilo;
		this.nombre = _nombre;
	}

	public Band() {

	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEstilo() {
		return estilo;
	}
	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
}
