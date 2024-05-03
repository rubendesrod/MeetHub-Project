package com.meethub.project.controllers;

import java.util.Enumeration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.meethub.project.models.PonerContraDTO;

import jakarta.servlet.http.HttpSession;


/**
 * Controlador para manejar las solicitudes de redirigir a otra pagina
 */
@Controller
public class AppController {

	@GetMapping("/")
	public String login(HttpSession  session) {
		// Método para destruir el atributo de session de LOGGED
		limpiarAtributosDeSession(session);
		return "login";
	}
	
	@GetMapping("/register")
	public String mostrarRegsitro(){
		return "register";
	}
	
	@GetMapping("/calendario")
	public String mostrarCalendario(HttpSession  session) {
	    if (comprobacionAutenticacion(session)) {
	        return "calendario";
	    }
	    return "redirect:/";
	}
	
	@GetMapping("/perfil")
	public String mostrarPerfil(HttpSession  session) {
		if (comprobacionAutenticacion(session)) {
			return "perfil";
	    }
		 return "redirect:/";
	}
	
	@GetMapping("/crearReunion")
	public String mostrarCrearReunion(HttpSession  session) {
		if (comprobacionAutenticacion(session)) {
			return "crearReunion";
	    }
		 return "redirect:/";
	}
	
	@GetMapping("/misReuniones")
	public String mostrarMisReuniones(HttpSession  session) {
		if (comprobacionAutenticacion(session)) {
	        return "misReuniones";
	    }
		  return "redirect:/";
	}	
	
	@GetMapping("/ponerContrasena")
	public String ponerContrasena(Model model) {
		// Meto un modelo de PonerContraDTO para que el formulario envie los datos a través de un objeto en java
		model.addAttribute("PonerContraDTO", new PonerContraDTO());
		return "ponerContrasena";
	}
	
	/**
	 * Método que recibe la session HTTP y busca si el usuarios se ha autorizado previamente
	 * @param session Session que guarda el navegador cuando hace respuestas HTTP
	 * @return Boolean TRue/False dependiendo si se ha autorizado o no este usuario
	 */
	public boolean comprobacionAutenticacion(HttpSession  session) {
		Boolean loggedIn = (Boolean) session.getAttribute("LOGGED");
	    if (loggedIn == null || !loggedIn) {
	        return false;
	    }
	    return true;
	}
	
	/**
	 * Metodo para limpiar los atributos que tenga la sesion guardados ya que el usuario acaba de entrar a la pagina principal
	 * @param session Session del usuario que recibe del navegador en la respuesta HTTP
	 */
	public void limpiarAtributosDeSession(HttpSession session) {
	    // Creo una enumeración de los nombres de todos los atributos en la sesión.
	    Enumeration<String> atributos = session.getAttributeNames();

	    // Recorro todos los nombres de los atributos y elimino cada uno de ellos.
	    while (atributos.hasMoreElements()) {
	        String nombreAtributo = atributos.nextElement();
	        session.removeAttribute(nombreAtributo);
	    }
	}

	
}
