package com.meethub.project.models;

import jakarta.persistence.*;

/**
 * Entidad que representa un usuario en la aplicación MeetHub.
 * Esta clase maneja detalles de información de los usuarios como email, contraseña, token de actualización y avatar.
 */
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario")
    private Long id;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Column(name = "Contraseña", nullable = false)
    private String contraseña; // Considere implementar la encriptación en la lógica de negocio

    @Column(name = "TokenDeActualizacion")
    private String tokenDeActualizacion;

    @Column(name = "Avatar")
    private String avatar;

    /**
     * Constructor por defecto.
     */
    public Usuario() {
    }

    /**
     * Crea un nuevo usuario con los detalles especificados.
     *
     * @param email el correo electrónico del usuario, no debe ser nulo y debe ser único.
     * @param contraseña la contraseña del usuario, no debe ser nulo.
     * @param tokenDeActualizacion el token utilizado para la actualización de la sesión, puede ser nulo.
     * @param avatar la URL del avatar del usuario, puede ser nulo.
     */
    public Usuario(String email, String contraseña, String tokenDeActualizacion, String avatar) {
        this.email = email;
        this.contraseña = contraseña;
        this.tokenDeActualizacion = tokenDeActualizacion;
        this.avatar = avatar;
    }

    // Métodos getters y setters con comentarios JavaDoc simples

    /**
     * Devuelve el ID único del usuario.
     * @return el ID del usuario.
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el correo electrónico del usuario.
     * @return el correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la contraseña del usuario.
     * @return la contraseña del usuario.
     */
    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Devuelve el token de actualización del usuario, utilizado para la autenticación y renovación de la sesión.
     * @return el token de actualización, puede ser nulo.
     */
    public String getTokenDeActualizacion() {
        return tokenDeActualizacion;
    }

    public void setTokenDeActualizacion(String tokenDeActualizacion) {
        this.tokenDeActualizacion = tokenDeActualizacion;
    }

    /**
     * Devuelve el avatar del usuario, una URL a una imagen.
     * @return la URL del avatar, puede ser nulo.
     */
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
