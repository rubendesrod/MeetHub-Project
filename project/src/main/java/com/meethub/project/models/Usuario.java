package com.meethub.project.models;

import com.meethub.project.Sexo;

import jakarta.persistence.*;

/**
 * Entidad que representa un usuario en la aplicación MeetHub.
 * Esta clase maneja detalles de información de los usuarios como email, contraseña, token de actualización y avatar.
 * 
 * @author Ruben
 * @version 1.0
 */
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario")
    private int id;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;
    
    @Column(name = "Nombre", nullable = false)
    private String Nombre;
    
    @Column(name = "Apellidos", nullable = false)
    private String apellidos;

    @Column(name = "Contrasena", nullable = true)
    private String contrasena;

    @Column(name = "FechaNacimiento", nullable = true)
    private String FechaNacimiento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Sexo", nullable = true)
    private Sexo sexo;
    
    @Column(name = "Token")
    private String Token;

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
     * @param contrasena la contraseña del usuario, no debe ser nulo.
     * @param Token el token utilizado para la actualizacion de la sesion, puede ser nulo.
     * @param avatar la URL del avatar del usuario, puede ser nulo.
     */
    public Usuario(String email, String contrasena, String Token, String avatar) {
        this.email = email;
        this.contrasena = contrasena;
        this.Token = Token;
        this.avatar = avatar;
    }

    // Métodos getters y setters con comentarios JavaDoc simples

    /**
     * Devuelve el ID único del usuario.
     * @return el ID del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Método set del ID
     * @param id Setea el id del usuario
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el correo electrónico del usuario.
     * @return el correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Metodo set para el email
     * @param email Email del usuario
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    
    /**
     * Devuelve el Nombre del usuario
     * @return Nombre del usuario
     */
    public String getNombre() {
		return Nombre;
	}

    /**
     * Setea el nombre del usuario
     * @param nombre Nombre del usuario nuevo que queremos ponerle
     */
	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	/**
	 * Devuelve los apellidos del usuario
	 * @return Apellidos del usuario
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Setea los apellidos del usurio
	 * @param apellidos Apellidos nuevos que queremos ponerle al usuario
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
     * Devuelve la contraseña del usuario.
     * @return la contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Metodo set para la contraseña
     * @param contrasena Contraseña hasheada del usuario
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Devuelve el token de actualización del usuario, utilizado para la autenticación y renovación de la sesión.
     * @return el token de actualización, puede ser nulo.
     */
    public String getToken() {
        return Token;
    }

    /**
     * Metodo set para el token
     * @param Token Token de actualizacion de google
     */
    public void setToken(String Token) {
        this.Token = Token;
    }

    /**
     * Devuelve el avatar del usuario, una URL a una imagen.
     * @return la URL del avatar, puede ser nulo.
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Metodo set para el avatar
     * @param avatar Enlace del avatar del usuario de google
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Meotodo que devuelve la fehca de nacimiento
     * @return La fecha de nacimiento
     */
	public String getFechaNacimiento() {
		return FechaNacimiento;
	}

	/**
	 * Metodo para setear una nueva fecha de nacimiento
	 * @param fechaNacimiento Fecha de nacimiento del usuario
	 */
	public void setFechaNacimiento(String fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}

	
	/**
	 * Metodo que devuelve el sexo del usuario
	 * @return Enum de sexo (Hombre, Mujer)
	 */
	public Sexo getSexo() {
		return sexo;
	}

	/**
	 * Metodo que setea el sexo del usuario
	 * @param sexo Sexo del usuario
	 */
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	
    
    
}
