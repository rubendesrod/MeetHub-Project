package com.meethub.project.controllers;

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

@Controller
@RequestMapping("/ponerContrasena")
public class PonerContraController {
	
	@Autowired
    private GoogleUserService googleUserService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/crearUsu")
	public String crearUsuario(@ModelAttribute PonerContraDTO formulario, HttpSession session) {
		
		String password = formulario.getContrasena();
	    String refreshToken = (String) session.getAttribute("refreshToken");
	    String accesToken = (String) session.getAttribute("accessToken");
	    
	    if (refreshToken != null) {
	        Usuario usuario = googleUserService.obtenerDetallesUsuario(accesToken);
	        if (usuario != null) {
	            usuario.setContrasena(password);
	            // Token de refresco porque los toquen de acceso duran 1 hora
	            usuario.setToken(refreshToken); 
	            usuarioService.saveUsuario(usuario);
	        }
	    }
	    session.setAttribute("LOGGED", true);
	    return "redirect:/calendario";
	}
	
}
