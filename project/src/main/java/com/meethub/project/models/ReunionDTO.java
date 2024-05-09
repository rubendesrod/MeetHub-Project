package com.meethub.project.models;

import java.util.ArrayList;
import com.meethub.project.ModoReunion;

/**
 * Clase DTO para pasar a la vista de crearReunion y que el formulario envíe un objeto DTO.
 * Contiene todos los detalles necesarios para gestionar la creación o modificación de reuniones.
 * 
 * @author Ruben
 * @version 1.0
 */
public class ReunionDTO {

    private String id;
    private String nombre;
    private ModoReunion modo;
    private String descripcion;
    private String dateReunion;
    private String start;
    private String end;
    private String invitados;
    private String urlMeet;

    /**
     * Constructor vacío para instanciar ReunionDTO sin inicializar sus campos.
     */
    public ReunionDTO() {}

    /**
     * Constructor con parámetros para inicializar todos los campos de ReunionDTO.
     *
     * @param id El identificador único de la reunión.
     * @param nombre El nombre descriptivo de la reunión.
     * @param modo El modo de la reunión, que puede ser Presencial o Online.
     * @param descripcion Una descripción detallada de la reunión.
     * @param dateReunion La fecha programada para la reunión.
     * @param start La hora de inicio de la reunión.
     * @param end La hora de finalización de la reunión.
     * @param invitados Una cadena que contiene los correos electrónicos de los invitados, separados por comas.
     * @param urlMeet El enlace al meet de Google si la reunión es online.
     */
    public ReunionDTO(String id, String nombre, ModoReunion modo, String descripcion, String dateReunion, String start, String end,
                      String invitados, String urlMeet) {
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
     * Obtiene el identificador único de la reunión.
     * @return El identificador de la reunión.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único de la reunión.
     * @param id El nuevo identificador para la reunión.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la reunión.
     * @return El nombre de la reunión.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la reunión.
     * @param nombre El nuevo nombre para la reunión.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el modo de la reunión, indicando si es presencial o en línea.
     * @return El modo de la reunión.
     */
    public ModoReunion getModo() {
        return modo;
    }

    /**
     * Establece el modo de la reunión.
     * @param modo El nuevo modo para la reunión, presencial o en línea.
     */
    public void setModo(ModoReunion modo) {
        this.modo = modo;
    }

    /**
     * Obtiene la descripción de la reunión.
     * @return La descripción de la reunión.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la reunión.
     * @param descripcion La nueva descripción para la reunión.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la fecha de la reunión.
     * @return La fecha de la reunión.
     */
    public String getDateReunion() {
        return dateReunion;
    }

    /**
     * Establece la fecha de la reunión.
     * @param dateReunion La nueva fecha para la reunión.
     */
    public void setDateReunion(String dateReunion) {
        this.dateReunion = dateReunion;
    }

    /**
     * Obtiene la hora de inicio de la reunión.
     * @return La hora de inicio de la reunión.
     */
    public String getStart() {
        return start;
    }

    /**
     * Establece la hora de inicio de la reunión.
     * @param start La nueva hora de inicio para la reunión.
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Obtiene la hora de finalización de la reunión.
     * @return La hora de finalización de la reunión.
     */
    public String getEnd() {
        return end;
    }

    /**
     * Establece la hora de finalización de la reunión.
     * @param end La nueva hora de finalización para la reunión.
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Obtiene la lista de invitados a la reunión.
     * @return Los correos electrónicos de los invitados, separados por comas.
     */
    public String getInvitados() {
        return invitados;
    }

    /**
     * Establece la lista de invitados a la reunión.
     * @param invitados Los nuevos correos electrónicos de los invitados, separados por comas.
     */
    public void setInvitados(String invitados) {
        this.invitados = invitados;
    }

    /**
     * Obtiene el enlace al meet de Google para reuniones en línea.
     * @return El URL del meet.
     */
    public String getUrlMeet() {
        return urlMeet;
    }

    /**
     * Establece el enlace al meet de Google para reuniones en línea.
     * @param urlMeet El nuevo URL del meet.
     */
    public void setUrlMeet(String urlMeet) {
        this.urlMeet = urlMeet;
    }
}
