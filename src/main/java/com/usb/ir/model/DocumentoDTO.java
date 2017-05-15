package com.usb.ir.model;

public class DocumentoDTO {
	
	public String id;
	public String titulo;
	public String path;
	public String contenido;
	public float score;
	public String fecha;
	String relevante;
	
	public DocumentoDTO(String titulo, String path) {
		this.path = path;
		this.titulo = titulo;
	}
	
	public DocumentoDTO() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getRelevante() {
		return relevante;
	}
	public void setRelevante(String relevante) {
		this.relevante = relevante;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
		
}
