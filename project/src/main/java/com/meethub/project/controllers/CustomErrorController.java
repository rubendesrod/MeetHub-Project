package com.meethub.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Ruben
 * @version 1.0
 * Clase que se encarga de controlar los tipos de errores que da el navegador
 */
@Controller
public class CustomErrorController {

	/**
	 * Método que se encarga de de responder a la llamada de la ruta /error y devolver a una vista de error
	 * @param request Respuesta del navgeador para comprobar que tipo de error esta dando
	 * @return Nombre de la vista a la que quiere redirigir
	 */
    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error404";
            }
        }
        return "error";
    }
}






