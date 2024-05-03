package com.meethub.project.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.meethub.project.PasswordUtil;
import com.meethub.project.models.Usuario;
import com.meethub.project.services.GoogleUserService;
import com.meethub.project.services.UsuarioService;
import com.mysql.cj.Session;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UsuarioService usuService;
	
	private PasswordUtil pwdUtil;
	
	@Autowired
	private GoogleUserService googleService;
	
	@PostMapping("/iniciar")
	public String iniciarLogin(HttpSession sesion, @RequestParam("email") String email, @RequestParam("password") String password 
								,RedirectAttributes redirectAttributes) 
	{
		
		Optional<Usuario> usuOpt = usuService.findByEmail(email);
		
		// Se comprueba que tiene la instancia en el objeto de Usuario
		if (usuOpt.isPresent()) {
		    Usuario usuario = usuOpt.get();
		    if (password == null || password.isEmpty()) {
		        redirectAttributes.addFlashAttribute("error", "La contraseña no puede estar vacía.");
		        return "redirect:/";
		    }
		    if (!validarCredenciales(password, usuario)) {
		        redirectAttributes.addFlashAttribute("error", "La contraseña es incorrecta.");
		        return "redirect:/";
		    }
		    if (!gestionarTokenDeAcceso(usuario, sesion)) {
		        return "redirect:/oauth2/authorize/google";
		    }
		    sesion.setAttribute("usuario", usuario);
		    sesion.setAttribute("LOGGED", true);
		    return "redirect:/calendario";
		} else {
		    redirectAttributes.addFlashAttribute("error", "No se encontró un usuario con ese email.");
		    return "redirect:/";
		}
	}
	
	/**
	 * Método que se encarga de validar que las los contraseñas son iguales
	 * @param password Contraseña en texto plano
	 * @param usuario Instancio del objeto usuario el cual se saca la contraseña hasheada
	 * @return Boolean True, si las pass coinciden o False, si no coinciden las pass
	 */
	private boolean validarCredenciales(String password, Usuario usuario) {
	    return pwdUtil.verificarPassword(password, usuario.getContrasena());
	}

	/**
	 * Metodo que se encarga gestionar los tokens y sobre todo de ver si ha expirado el de actualizacion
	 * @param usuario Instancia del objeto usuario
	 * @param sesion session que se mantiene en la llamda HTTP
	 * @return Boolean True, si se ha podido sacar bien los tokens y no hay ninguno expirado o False, si el token de actualización ha expirado
	 */
	private boolean gestionarTokenDeAcceso(Usuario usuario, HttpSession sesion) {
	    if (googleService.refreshTokenExpired(usuario.getToken())) {
	        return false;
	    } else {
	        String accessToken = googleService.refreshAccessToken(usuario.getToken());
	        sesion.setAttribute("accesToken", accessToken);
	        return true;
	    }
	}
	
}