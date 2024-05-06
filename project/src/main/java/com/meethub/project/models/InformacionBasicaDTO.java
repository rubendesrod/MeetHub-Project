package com.meethub.project.models;

import com.meethub.project.Sexo;

/**
 * Clase de la informacion basica del DTO que se envia a la vista para recoger los datos del formulario
 */
public class InformacionBasicaDTO {

	/**
	 * Nombre del usuario
	 */
	private String nombre;
	
	/**
	 * Apellidos del usuario
	 */
	private String apellidos;
	
	/**
	 * Email del usuario
	 */
	private String email;
	
	/**
	 * ENUM de Sexo que identifica el sexo del usuario
	 */
	private Sexo sexo;
	
	/**
	 * Dia de nacimiento del usuario
	 */
	private int diaNacimiento;
	
	/**
	 * Mes de nacimiento del usuario
	 */
	private int mesNacimiento;
	
	/**
	 * Año de nacimiento del usuario
	 */
	private int anioNacimiento;
	
	/**
	 * URL del avatar del usuario
	 */
	private String avatar;
	
	/**
	 * Constructor de vacio de la clase
	 */
	public InformacionBasicaDTO() {}
	
	/**
	 * Constructor de la clase
	 * @param nombre Nombre del usuario
	 * @param apellidos Apellidos del usuario
	 * @param email Email del usuario
	 * @param sexo Sexo del usuario
	 * @param dia Dia de nacimiento del usuario
	 * @param mes Mes de nacimeinto del usuario
	 * @param anio Año de nacimiento del usuario
	 */
	public InformacionBasicaDTO(String avatar,String nombre, String apellidos, String email, Sexo sexo, int dia, int mes, int anio) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.sexo = sexo;
		this.diaNacimiento = dia;
		this.mesNacimiento = mes;
		this.anioNacimiento = anio;
		this.avatar = avatar;
	}

	/**
	 * Metodo GET del nombre
	 * @return String
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo SET del nombre
	 * @param nombre Nombre del usuario
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	/**
	 * Meotodo GET de los apellidos
	 * @return String
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Metodo SET de los apellidos
	 * @param apellidos Apellidos del usuario
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	
	/**
	 * Metodo GET del email
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Metodo set del email
	 * @param email Email de usuario
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	/**
	 * Metodo GET del sexo ENUM
	 * @return SEXO ENUM
	 */
	public Sexo getSexo() {
		return sexo;
	}

	/**
	 * Metodo SET del sexo
	 * @param sexo
	 */
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	/**
	 * Metodo GET del dia de nacimiento
	 * @return int
	 */
	public int getDiaNacimiento() {
		return diaNacimiento;
	}

	/**
	 * Metodo SET del dia de nacimiento
	 * @param diaNacimiento Dia de nacimiento del usuario
	 */
	public void setDiaNacimiento(int diaNacimiento) {
		this.diaNacimiento = diaNacimiento;
	}

	
	/**
	 * Metodo GET del mes de nacimiento
	 * @return int
	 */
	public int getMesNacimiento() {
		return mesNacimiento;
	}

	/**
	 * Metodo SET del mes de nacimiento
	 * @param mesNacimiento Mes de nacimiento del usuario
	 */
	public void setMesNacimiento(int mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}

	/**
	 * Metodo GET del año de nacimiento
	 * @return int
	 */
	public int getAnioNacimiento() {
		return anioNacimiento;
	}

	/**
	 * Metodo SET del año de nacimiento
	 * @param anioNacimiento Año de nacimiento del usuario
	 */
	public void setAnioNacimiento(int anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	/**
	 * Metodo GET que devuelve el URL del avatar del usuario
	 * @return String
	 */
	public String getAvatar() {
		return avatar;
	}	
	
	/**
	 * Metodo SET del avatar del Usuario
	 * @param avatar String del URL
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
