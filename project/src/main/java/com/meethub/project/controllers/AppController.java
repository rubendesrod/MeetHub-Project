package com.meethub.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Controlador para manejar las solicitudes de redirigir a otra pagina
 */
@Controller
public class AppController {

	@GetMapping("/")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String mostrarRegsitro(){
		return "register";
	}
	
	@GetMapping("/ponerContrasena")
	public String ponerContrasena() {
		return "ponerContrasena";
	}
	
	@GetMapping("/calendario")
	public String mostrarCalendario() {
		return "calendario";
	}
	
	@GetMapping("/perfil")
	public String mostrarPerfil() {
		return "perfil";
	}
	
	@GetMapping("/crearReunion")
	public String mostrarCrearReunion() {
		return "crearReunion";
	}
	
	@GetMapping("/misReuniones")
	public String mostrarMisReuniones() {
		return "misReuniones";
	}	
	
}
