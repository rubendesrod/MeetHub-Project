package com.meethub.project.components;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

/**
 * Clase componente para posteriormente usar sus metodos en las plantillas
 */
@Component
public class DateUtils {
	
	/**
	 * Metodo que parsea la fehca para sacar su año de nacimiento
	 * @param dateString La fecha en formato dd-MM-YYYY
	 * @return Numero del año de nacimiento
	 */
    public int getYear(String dateString) {
        return LocalDate.parse(dateString).getYear();
    }

    /**
     * Metodo que parsea la fecha para sacar el mes de nacimiento
     * @param dateString La fecha en formato dd-MM-YYYY
     * @return Numero del mes de nacimiento
     */
    public int getMonth(String dateString) {
        return LocalDate.parse(dateString).getMonthValue();
    }

    /**
     * Metodo para devolver el dia de nacimiento del usuario
     * @param dateString La fecha en formato dd-MM-YYYY
     * @return Numero del dia de nacimiento
     */
    public int getDay(String dateString) {
        return LocalDate.parse(dateString).getDayOfMonth();
    }
}