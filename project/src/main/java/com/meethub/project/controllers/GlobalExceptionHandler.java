package com.meethub.project.controllers;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Ruben
 * @version 1.0
 * Clase Controlador que se encarga de recoger las expceptiones que lanza el codigo
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Metodo que envía al usuario a una pagina de error, porque la ruta a la que se quiere acceder no está disponible
	 * @param e Nombre de la vista/o ruta que se intenta acceder y no existe
	 * @return Manda un modelo que contiene el mensaje de error y el nombre de la vista a la que se quiere acceder
	 */
    @ExceptionHandler(NoSuchElementException.class)
    public ModelAndView handleNoSuchElementException(NoSuchElementException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error404"); // Nombre de la vista de error personalizada
        modelAndView.addObject("error", e.getMessage());
        return modelAndView;
    }

   /**
    * Metodo que responde cuando a cualquier tipo de excepcion
    * @param e Exception que ha dado el codigo
    * @return Manda un modelo que contiene el mensaje de error y el nombre de la vista a la que se quiere acceder
    */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error404"); // Nombre de una vista de error general
        modelAndView.addObject("error", e.getMessage());
        return modelAndView;
    }
}

