package com.meethub.project.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meethub.project.models.PonerContraDTO;
import com.meethub.project.models.Usuario;
import com.meethub.project.services.GoogleUserService;
import com.meethub.project.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

/**
 * @author Ruben
 * @version 1.0
 * Clase que se encarga de recoger la llamada a la vista de Poner Contrasena
 */
@Controller
@RequestMapping("/ponerContrasena")
public class PonerContraController {
	
	/**
	 * Instancia del Objeto GoogleUserService para poder realizar la comprobación del token de acceso
	 */
	@Autowired
    private GoogleUserService googleUserService;
	
	/**
	 * Instancia del Objeto UsuarioService para poder realizar consultas a la base de datos
	 */
	@Autowired
	private UsuarioService usuarioService;
	
	
	
	/**
	 * Metodo que reacciona a un POST enviado de un formulario, y realiza la creación del usuario en la Base de datos
	 * @param formulario Objeto PonerContraDTO que contiene los datos enviados del usuario
	 * @param session Sesion que guarda el navegador WEB en la respuestas HTTP
	 * @return Nombre de la vista a la que redirige
	 */
	@PostMapping("/crearUsu")
	public String crearUsuario(@ModelAttribute PonerContraDTO formulario, HttpSession session) {
		
		String password = formulario.getContrasena();
	    String refreshToken = (String) session.getAttribute("refreshToken");
	    String accesToken = (String) session.getAttribute("accessToken");
	    
	    if (refreshToken != null) {
	        Usuario usuario = googleUserService.obtenerDetallesUsuario(accesToken);
	        if (usuario != null) {
	        	// Comprobacion de que el usuario no exista ya en la DB
        		usuario.setContrasena(password);
	            // Token de refresco porque los toquen de acceso duran 1 hora
	            usuario.setToken(refreshToken); 
	            usuarioService.saveUsuario(usuario);
	            session.setAttribute("usuario", usuario);
	            session.setAttribute("LOGGED", true);
	    	    return "redirect:/calendario";
	        }
	     }   
	    return "redirect:/register";
	}
	
	
	
}
