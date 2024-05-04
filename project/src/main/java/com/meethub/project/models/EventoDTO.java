package com.meethub.project.models;

import java.util.Date;

public class EventoDTO {
	
    private String id;
    private String title;
    private Date start;
    private Date end;
    private String description;
    private String color = "#7416FF";

    public EventoDTO() {}
    /**
     * Constructo de la clase EventoDTO
     * @param id id del evento
     * @param title titulo del evento
     * @param startTime Tiempo que empieza el evento
     * @param endTime tiempo que acab el evento
     * @param description descripcion del evento
     */
    public EventoDTO(String id, String title, Date start, Date end, String description) {
    	this.id = id;
    	this.title = title;
    	this.start = start;
    	this.end = end;
    	this.description = description;
    }
    
    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
    
    

}
