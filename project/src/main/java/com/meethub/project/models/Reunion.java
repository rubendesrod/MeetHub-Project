package com.meethub.project.models;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entidad que representa una reunión en la aplicación MeetHub.
 * Esta clase maneja los detalles de las reuniones como títulos, descripciones, fechas de inicio y fin, y la asociación con un usuario.
 */
@Entity
@Table(name = "Reunion")
public class Reunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Reunion")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_Usuario", referencedColumnName = "ID_Usuario")
    private Usuario usuario;

    @Column(name = "Titulo")
    private String titulo;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "FechaInicio", nullable = false)
    private Date fechaInicio;

    @Column(name = "FechaFin", nullable = false)
    private Date fechaFin;

    @Column(name = "ID_Calendario")
    private String idCalendario;

    /**
     * Constructor por defecto de Reunion.
     */
    public Reunion() {
    }

    /**
     * Crea una nueva reunión con todos los detalles necesarios.
     *
     * @param usuario el usuario que organiza la reunión
     * @param titulo el título de la reunión
     * @param descripcion una descripción breve de la reunión
     * @param fechaInicio la fecha y hora de inicio de la reunión
     * @param fechaFin la fecha y hora de finalización de la reunión
     * @param idCalendario el identificador del calendario asociado a la reunión
     */
    public Reunion(Usuario usuario, String titulo, String descripcion, Date fechaInicio, Date fechaFin, String idCalendario) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idCalendario = idCalendario;
    }

    // Métodos getters y setters con comentarios JavaDoc simples

    /**
     * Devuelve el ID único de la reunión.
     * @return el ID de la reunión.
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el usuario que organiza la reunión.
     * @return el usuario organizador.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Devuelve el título de la reunión.
     * @return el título.
     */
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Devuelve la descripción de la reunión.
     * @return la descripción.
     */
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve la fecha de inicio de la reunión.
     * @return la fecha de inicio.
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Devuelve la fecha de fin de la reunión.
     * @return la fecha de fin.
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Devuelve el identificador del calendario asociado a la reunión.
     * @return el ID del calendario.
     */
    public String getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(String idCalendario) {
        this.idCalendario = idCalendario;
    }
}
