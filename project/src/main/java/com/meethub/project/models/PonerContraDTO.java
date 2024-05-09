package com.meethub.project.models;

/**
 * Clase DTO para manejar la configuración o actualización de la contraseña del usuario.
 * Contiene campos para la nueva contraseña y su confirmación para garantizar que sean idénticas.
 *
 * @author Ruben
 * @version 1.0
 */
public class PonerContraDTO {

    private String contrasena;
    private String contrasenaRepetida;
    
    /**
     * Constructor con parámetros para inicializar la instancia con una contraseña y su confirmación.
     * 
     * @param pass La contraseña nueva del usuario.
     * @param passRepeat La confirmación de la contraseña nueva del usuario.
     */
    public PonerContraDTO(String pass, String passRepeat) {
        this.contrasena = pass;
        this.contrasenaRepetida = passRepeat;
    }
    
    /**
     * Constructor sin parámetros para permitir la inicialización vacía de la clase.
     */
    public PonerContraDTO() {
    }
    
    /**
     * Devuelve la contraseña nueva del usuario.
     * 
     * @return La contraseña nueva.
     */
    public String getContrasena() {
        return contrasena;
    }
    
    /**
     * Establece la nueva contraseña del usuario.
     * 
     * @param pass La nueva contraseña a establecer.
     */
    public void setContrasena(String pass) {
        this.contrasena = pass;
    }
    
    /**
     * Devuelve la confirmación de la contraseña nueva del usuario.
     * 
     * @return La confirmación de la contraseña.
     */
    public String getContrasenaRepetida() {
        return contrasenaRepetida;
    }
    
    /**
     * Establece la confirmación de la nueva contraseña del usuario.
     * 
     * @param passRepeat La confirmación de la nueva contraseña a establecer.
     */
    public void setContrasenaRepetida(String passRepeat) {
        this.contrasenaRepetida = passRepeat;
    }
}
