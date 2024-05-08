package com.meethub.project.models;

import java.util.ArrayList;

import com.meethub.project.ModoReunion;

/**
 * Clase DTO para pasarlo a la vista de crearReunion y que el formulario envie un Objeto DTO
 */
public class ReunionDTO {

	private String id;
	
	/**
	 * Nombre de la reunion
	 */
	private String nombre;
	
	/**
	 * Enum del modo de la reunion
	 */
	private ModoReunion modo;
	
	/**
	 * Descripcion de la rreunion
	 */
	private String descripcion;
	
	/**
	 * Fecha/dia de la reunion
	 */
	private String dateReunion;
	
	/**
	 * Hora de empiece de la reunion
	 */
	private String start;
	
	/**
	 * Hora de acabe de la reunion
	 */
	private String end;
	
	/**
	 * String que contendrá a todos los invitados
	 */
	private String invitados;

	/**
	 * String de la url de la reunion al meet
	 */
	private String urlMeet;
	
	/**
	 * Constructo de la clase vacio
	 */
	public ReunionDTO() {}
	
	/**
	 * Constructor de la clase con todos los parametros
	 * @param id Id de la reunion
	 * @param nombre Nombre de la reunion
	 * @param modo Enum de Presencial / Online para la reunion
	 * @param descripcion Descripcion de la reunion
	 * @param dateReunion Fecha de la reunion
	 * @param start Hora de empiece de la reunion
	 * @param end Hora de finalizacion de la reunion
	 * @param invitados Correos de los usuarios que queremos invitar a la reunion, separados por ','
	 * @param urlMeet Enlace a la reunión del meet de google
	 */
	public ReunionDTO(String id,String nombre, ModoReunion modo, String descripcion, String dateReunion, String start, String end,
			String invitados, String urlMeet) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.modo = modo;
		this.descripcion = descripcion;
		this.dateReunion = dateReunion;
		this.start = start;
		this.end = end;
		this.invitados = invitados;
		this.urlMeet = urlMeet;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre el nombre que queremos setear
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el modo
	 */
	public ModoReunion getModo() {
		return modo;
	}

	/**
	 * @param modo the modo to set
	 */
	public void setModo(ModoReunion modo) {
		this.modo = modo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the dateReunion
	 */
	public String getDateReunion() {
		return dateReunion;
	}

	/**
	 * @param dateReunion the dateReunion to set
	 */
	public void setDateReunion(String dateReunion) {
		this.dateReunion = dateReunion;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * @return the invitados
	 */
	public String getInvitados() {
		return invitados;
	}

	/**
	 * @param invitados the invitados to set
	 */
	public void setInvitados(String invitados) {
		this.invitados = invitados;
	}

	/**
	 * @return El urlMeet
	 */
	public String getUrlMeet() {
		return urlMeet;
	}

	/**
	 * @param urlMeet El urlMeet que queremos setear
	 */
	public void setUrlMeet(String urlMeet) {
		this.urlMeet = urlMeet;
	}

	/**
	 * @return El id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id El id nuevo que queremos setear
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
