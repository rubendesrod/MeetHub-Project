package com.meethub.project.controllers;

import java.util.Enumeration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meethub.project.models.PonerContraDTO;
import com.meethub.project.models.Usuario;
import com.meethub.project.services.GoogleUserService;
import com.meethub.project.services.UsuarioService;

import jakarta.servlet.http.HttpSession;


/**
 * Controlador para manejar las solicitudes de redirigir a otra pagina
 */
@Controller
public class AppController {

	@Autowired
    private GoogleUserService googleUserService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/")
	public String login(@RequestParam(value = "usuario_existe", required = false) String usuarioExiste, HttpSession  session, Model model) {
		// Método para destruir el atributo de session de LOGGED
		limpiarAtributosDeSession(session);
		if ("true".equals(usuarioExiste)) {
	        model.addAttribute("UsuarioExiste", "Un usuario con ese correo electrónico ya existe.");
	    }
		return "login";
	}
	
	@GetMapping("/register")
	public String mostrarRegsitro(){
		return "register";
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
	public String ponerContrasena(Model model, HttpSession session) {
		String accesToken = (String) session.getAttribute("accessToken");
		
		Usuario usuario = googleUserService.obtenerDetallesUsuario(accesToken);
		
		// Compruebo antes de mandar a poner la contraseña si ya estaba logeado un usuario con ese correo
		if(!usuarioYaExiste(usuario.getEmail())) {
			// DTO que utilizo en el formulario para recoger los datos
			model.addAttribute("PonerContraDTO", new PonerContraDTO());
			return "ponerContrasena";
		}else {
			return "redirect:/?usuario_existe=true";
		}
	}
	
	/**
	 * Método que se enarcaga de comprobar si el usuario ya existe en la base de datos con ese correo
	 * @return Tue, si no existe el usuario o False, si que existe el usuarios
	 */
	public boolean usuarioYaExiste(String email) {
		// Busca el usuario por correo electrónico
	    Optional<Usuario> usuario = usuarioService.findByEmail(email);
	    // Devuelve true si el usuario existe, false de lo contrario
	    return usuario.isPresent();
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
