package com.meethub.project.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meethub.project.PasswordUtil;
import com.meethub.project.CamposValidate;
import com.meethub.project.Sexo;
import com.meethub.project.models.InformacionBasicaDTO;
import com.meethub.project.models.Usuario;
import com.meethub.project.services.GoogleUserService;
import com.meethub.project.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PerfilController {

	
	@Autowired
	private GoogleUserService googleService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	/**
	 * Método que gestiona la vista del perfil
	 * @param session Session que guarda la comunicacion HTTP en el navegador
	 * @param model modelo de la vista para pasar atributos
	 * @return String nombre de la vista
	 */
	@GetMapping("/perfil")
	public String vistaPerfil(HttpSession session, Model model) {
		
		if (comprobacionAutenticacion(session)) {
			
	    	Usuario usuario = (Usuario) session.getAttribute("usuario");
	    	
	    	// Compruebo que el usuario sea diferente a null
	        if (usuario == null) {
	        	// Manjeo el caso donde no hay usuario en la sesión
	            return "redirect:/";
	        } 
	        
	        // Compruebo que no haya expirado ninguno de los token
	        if (!gestionarTokenDeAcceso(usuario, session)) {
	            return "redirect:/oauth2/authorize/google";
	        }
	        
	        // Añado el modelo DTO de la informacion para mandarselo al Modelo de la vista
	        InformacionBasicaDTO informacionUsu = convertirUsuarioDTO(usuario);

	        model.addAttribute("usuario", informacionUsu);
	        
	        return "perfil";
	        
		}
		return "redirect:/";
	}
	
	/**
	 * Metodo que se encarga de recibir la informacion de actualizacion que envia el usuario y actualizar en la base de datos
	 * @param usuDTO Modelo DTO del formulario enviado
	 * @param session Session que guarda la comunicacion HTTP en el navegador
	 * @param model Modelo de la vista
	 * @return String nombre de la vista a la que redirige
	 */
	@PostMapping("/perfil/actualizarInformacion")
	public String actualizarInformacion(@ModelAttribute InformacionBasicaDTO usuDTO
										, HttpSession session
										, Model model) 
	{
		
		// Obtengo el usuario de la sesion para actualizarle tambien
		Usuario usuSesion = (Usuario) session.getAttribute("usuario");
		
		// Creo una nueva instancia de usuario
		Usuario usu = new Usuario();
		
		// Seteo todas las variables y codifico la fecha
		if (CamposValidate.validarNombre(usuDTO.getNombre()) &&
				CamposValidate.validarApellidos(usuDTO.getApellidos())) 
		{
			usu.setNombre(usuDTO.getNombre());
			usu.setApellidos(usuDTO.getApellidos());
		}else {
			usuDTO.setAvatar(usuSesion.getAvatar());
			usuDTO.setEmail(usuSesion.getEmail());
			model.addAttribute("usuario", usuDTO);
			model.addAttribute("ERROR", "Los campos NOMBRE o APELLIDOS no cumplen los requisitos");
			return "perfil";
		}
		usu.setAvatar(usuSesion.getAvatar());
		usu.setContrasena(usuSesion.getContrasena());
		usu.setEmail(usuSesion.getEmail());
		
		// Compruebo que no sea NULL por si no ha seleccionado ningun campo de sexo
		if(usuDTO.getSexo() == Sexo.Null) {
			usu.setSexo(null);
		}else {
			usu.setSexo(usuDTO.getSexo());
		}
		
		String fechaNacimiento;
		// compruebo que alguna de las variables no sea valor 0
		if(usuDTO.getAnioNacimiento() == 0 ||
			usuDTO.getMesNacimiento() == 0 ||
			usuDTO.getDiaNacimiento() == 0) {
			fechaNacimiento = null;
		}else {
			// Combinar la fecha de nacimiento par apasarla
			fechaNacimiento = String.format("%d-%02d-%02d",
								usuDTO.getAnioNacimiento(),
								usuDTO.getMesNacimiento(),
								usuDTO.getDiaNacimiento());
		}
		
		
		// Seteo la fecha formateada
		usu.setFechaNacimiento(fechaNacimiento);
		usu.setToken(usuSesion.getToken());
		usu.setId(usuSesion.getId());
		
		// Hago uso del servicio para actualizar el usuario
		usuarioService.updateUsuario(usu);
		
		// Seteo los campos que le faltan al DTO para poder pasarle al modelo directamente
		usuDTO.setAvatar(usu.getAvatar());
		usuDTO.setEmail(usu.getEmail());
		session.setAttribute("usuario", usu);
		model.addAttribute("usuario", usuDTO);
		model.addAttribute("Correcto", "Se actualizado la informacion correctamente");
		
		
		// Antes de enviar esto hay que setear el nuevo usuario en el atributo de la sesión
		return "perfil";
	}
	
	
	/**
	 * Metodo que se encarga cambiar la contraseña vieja por la actual
	 * @param sesion Session que guarda la comunicacion HTTP en el navegador
	 * @param passActual Contraseña que se va a comparar con las pass de la Base de datos
	 * @param newPass Nueva contraseña que quiere poner el usuario
	 * @param newPassRep Nueva contraseña pero repetida para comprobar si son iguales
	 * @return String nombre de la vista a la que quiere redirigir
	 */
	@PostMapping("/perfil/cambiarPass")
	public String cambioDePass(HttpSession sesion, 
								@RequestParam(name="actual") String passActual,
								@RequestParam(name="nuevaPass") String newPass,
								@RequestParam(name="nuevaPassRep") String newPassRep,
								Model model) 
	{
		// Obtengo el usuario que tengo en la sesion
		Usuario usu = (Usuario) sesion.getAttribute("usuario");
		
		// Para cuando paso el usuario
		InformacionBasicaDTO informacion = convertirUsuarioDTO(usu);
		
		// Valido que todos los campos tengan contenido
		if (passActual != "" && newPass != "" && newPassRep != "") {
			// Compruebo que la contraseña en texto plano es la misma que la contraseña HASHEADA
			if(PasswordUtil.verificarPassword(passActual, usu.getContrasena())) {
			    // Validación de la nueva contraseña
			    if (CamposValidate.validarNuevaContraseña(newPass)) {
			    	// Validación de que las nuevas contraseñas son iguales las dos
			       if (newPass.equals(newPassRep)) {
						String passCodificada = PasswordUtil.hashPassword(newPass);
				        usu.setContrasena(passCodificada);
				        usuarioService.updateUsuario(usu);
				        model.addAttribute("cambioCorrecto", "Contraseña actualizada correctamente.");
					}else {
						model.addAttribute("cambioError", "Las nuevas contraseña no coinciden");
					}
			    } else {
			        model.addAttribute("cambioError", "La nueva contraseña no cumple con los requisitos.");
			    }
			} else {
			    model.addAttribute("cambioError", "La contraseña actual es incorrecta");
			}
		}else {
			model.addAttribute("cambioError", "Ningun campo puede estar vacio");
		}
		model.addAttribute("usuario", informacion);
		sesion.setAttribute("usuario", usu);

		return "perfil";
	}
	
	
	@PostMapping("/perfil/borrarCuenta")
	public String borrarCuenta(HttpSession sesion, Model model) {
		
		Usuario usu = (Usuario) sesion.getAttribute("usuario");
		
		try {
			usuarioService.deleteUsuario(usu.getId());	
			
		}catch(Exception e) {
			model.addAttribute("error", "El usuario no ha podido ser borrado, Intentalo más tarde");
			return "perfil";
		}
		
		model.addAttribute("borrado", "El usuario ha sido borrado correctamente");
		return "login";
	}
	
	/**
	 * Metodo que encarga de recibir una INstancia de usuario y Pasarle a InformacionBasicaDTO
	 * @param usuario Instancia de la clase Usuario
	 * @return InformacionBasicaDTO
	 */
	public InformacionBasicaDTO convertirUsuarioDTO(Usuario usuario) {
		InformacionBasicaDTO informacionUsu = new InformacionBasicaDTO();
        
        informacionUsu.setEmail(usuario.getEmail());
        informacionUsu.setNombre(usuario.getNombre());
        informacionUsu.setApellidos(usuario.getApellidos());
        informacionUsu.setSexo(usuario.getSexo());
        informacionUsu.setAvatar(usuario.getAvatar());
        
        // Descomponer la fecha de nacimiento
        if (usuario.getFechaNacimiento() != null && !usuario.getFechaNacimiento().isEmpty()) {
            LocalDate fecha = LocalDate.parse(usuario.getFechaNacimiento());
            informacionUsu.setDiaNacimiento(fecha.getDayOfMonth());
            informacionUsu.setMesNacimiento(fecha.getMonthValue());
            informacionUsu.setAnioNacimiento(fecha.getYear());
        }
        
        return informacionUsu;
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
	 * Método que se encarga de gestionar los tokens de acceso y de actualización para poder realizar las llamadas
	 * a la API de Google
	 * @param usuario Instancia del objeto usuario para obtener su token
	 * @param sesion Session que guarda el navegador cuando hace respuestas HTTP
	 * @return True, si el token de acceso ha sido actualizado con éxito, False, si no se ha podido actualizar
	 */
	private boolean gestionarTokenDeAcceso(Usuario usuario, HttpSession sesion) {
	    if (googleService.refreshTokenExpired(usuario.getToken())) {
	        return false;
	    } else {
	        try {
	            String refreshToken = usuario.getToken(); 
	            String accessToken = googleService.refreshAccessToken(refreshToken);
	            sesion.setAttribute("accessToken", accessToken);
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	}
	
}
