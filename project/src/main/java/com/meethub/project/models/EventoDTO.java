package com.meethub.project.models;

import java.util.Date;

/**
 * Clase que representa un Evento con su información correspondiente.
 * Esta clase contiene detalles como el ID, título, fechas de inicio y fin,
 * descripción, color y URL del evento.
 *
 * @author Ruben
 * @version 1.0
 */
public class EventoDTO {
    
    private String id;
    private String title;
    private Date start;
    private Date end;
    private String description;
    private String color = "#a671f5";
    private String url;

    /**
     * Constructor completo para crear una instancia de EventoDTO.
     * 
     * @param id Identificador único del evento.
     * @param title Título del evento.
     * @param start Fecha y hora de inicio del evento.
     * @param end Fecha y hora de finalización del evento.
     * @param description Descripción breve del evento.
     */
    public EventoDTO(String id, String title, Date start, Date end, String description) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    /**
     * Constructor sin parámetros. Utilizado para crear una instancia vacía de EventoDTO.
     */
    public EventoDTO() {}

    /**
     * Obtiene el identificador único del evento.
     * 
     * @return El identificador del evento.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único del evento.
     * 
     * @param id El nuevo identificador del evento.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el título del evento.
     * 
     * @return El título del evento.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Establece el título del evento.
     * 
     * @param title El nuevo título del evento.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtiene la fecha y hora de inicio del evento.
     * 
     * @return La fecha de inicio del evento.
     */
    public Date getStart() {
        return start;
    }

    /**
     * Establece la fecha y hora de inicio del evento.
     * 
     * @param start La nueva fecha de inicio del evento.
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Obtiene la fecha y hora de finalización del evento.
     * 
     * @return La fecha de finalización del evento.
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Establece la fecha y hora de finalización del evento.
     * 
     * @param end La nueva fecha de finalización del evento.
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * Obtiene la descripción del evento.
     * 
     * @return La descripción del evento.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Establece la descripción del evento.
     * 
     * @param description La nueva descripción del evento.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtiene el color representativo del evento.
     * 
     * @return El color del evento.
     */
    public String getColor() {
        return color;
    }

    /**
     * Establece un nuevo color representativo del evento.
     * 
     * @param color El nuevo color del evento.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Obtiene la URL del evento, si está disponible.
     * 
     * @return La URL del evento.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL del evento.
     * 
     * @param url La nueva URL del evento.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
