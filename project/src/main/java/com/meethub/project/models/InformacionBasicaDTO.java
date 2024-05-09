package com.meethub.project.models;

import com.meethub.project.Sexo;

/**
 * Clase que representa la información básica de un usuario.
 * Esta clase es utilizada para recoger los datos desde un formulario y contiene
 * atributos como nombre, apellidos, email, sexo, fecha de nacimiento y URL de avatar.
 *
 * @author Ruben
 * @version 1.0
 */
public class InformacionBasicaDTO {

    private String nombre;
    private String apellidos;
    private String email;
    private Sexo sexo;
    private int diaNacimiento;
    private int mesNacimiento;
    private int anioNacimiento;
    private String avatar;

    /**
     * Constructor sin parámetros para crear una instancia vacía de InformacionBasicaDTO.
     */
    public InformacionBasicaDTO() {}

    /**
     * Constructor para crear una instancia de InformacionBasicaDTO con todos los detalles del usuario.
     * 
     * @param avatar URL del avatar del usuario.
     * @param nombre Nombre del usuario.
     * @param apellidos Apellidos del usuario.
     * @param email Correo electrónico del usuario.
     * @param sexo Sexo del usuario, como definido por el ENUM {@link Sexo}.
     * @param dia Día de nacimiento del usuario.
     * @param mes Mes de nacimiento del usuario.
     * @param anio Año de nacimiento del usuario.
     */
    public InformacionBasicaDTO(String avatar, String nombre, String apellidos, String email, Sexo sexo, int dia, int mes, int anio) {
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
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * 
     * @param nombre Nombre del usuario a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario.
     * 
     * @return Apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     * 
     * @param apellidos Apellidos del usuario a establecer.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el email del usuario.
     * 
     * @return Email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     * 
     * @param email Correo electrónico del usuario a establecer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el sexo del usuario.
     * 
     * @return Sexo del usuario.
     */
    public Sexo getSexo() {
        return sexo;
    }

    /**
     * Establece el sexo del usuario.
     * 
     * @param sexo Sexo del usuario a establecer.
     */
    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    /**
     * Obtiene el día de nacimiento del usuario.
     * 
     * @return Día de nacimiento del usuario.
     */
    public int getDiaNacimiento() {
        return diaNacimiento;
    }

    /**
     * Establece el día de nacimiento del usuario.
     * 
     * @param diaNacimiento Día de nacimiento del usuario a establecer.
     */
    public void setDiaNacimiento(int diaNacimiento) {
        this.diaNacimiento = diaNacimiento;
    }

    /**
     * Obtiene el mes de nacimiento del usuario.
     * 
     * @return Mes de nacimiento del usuario.
     */
    public int getMesNacimiento() {
        return mesNacimiento;
    }

    /**
     * Establece el mes de nacimiento del usuario.
     * 
     * @param mesNacimiento Mes de nacimiento del usuario a establecer.
     */
    public void setMesNacimiento(int mesNacimiento) {
        this.mesNacimiento = mesNacimiento;
    }

    /**
     * Obtiene el año de nacimiento del usuario.
     * 
     * @return Año de nacimiento del usuario.
     */
    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    /**
     * Establece el año de nacimiento del usuario.
     * 
     * @param anioNacimiento Año de nacimiento del usuario a establecer.
     */
    public void setAnioNacimiento(int anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    /**
     * Obtiene el URL del avatar del usuario.
     * 
     * @return URL del avatar del usuario.
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Establece el URL del avatar del usuario.
     * 
     * @param avatar URL del avatar a establecer.
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
