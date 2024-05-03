package com.meethub.project.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meethub.project.repositorys.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

@Controller
public class OAuth2Controller {

    // Método que redirige al usuario a Google para autorizar nuestra aplicación
    @GetMapping("/oauth2/authorize/google")
    public String redirectToGoogleOAuth() {
        String url = buildGoogleAuthorizationUrl();
        return "redirect:" + url;
    }

    /**
     * Método para manejar la redirección de vuelta desde Google después de que el usuario autorice la aplicación
     * @param code Código que devuelve Google después del Oauth de autorización del usuario
     * @param request Permite accerder a la solicitud HTTP actual para poder guardar el token
     * @return
     */
    @GetMapping("/oauth2/callback/google")
    public String handleGoogleOAuth2Callback(@RequestParam("code") String code, HttpSession session) {
	 	try {
	 		String accessToken = fetchAccessToken(code, session);    
	 		// Guarda el token de acceso temporalmente en la sesión para utilizar en poner contrasena y ya guardarle
    	 	session.setAttribute("accessToken", accessToken);
    	 	// Creo un nuevo atributo en la session para comprobar que no ha pasado una hora del token de acceso y haya expirado
    	 	LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);
    	 	session.setAttribute("tokenExpiration", expirationTime);
    	 	// Obtengo un apoyo de una variable de sesion para saber si el usuario se ha tenido que reutenticar o no
    	 	String noRegistra = (String) session.getAttribute("noRegistra");
    	 	if(noRegistra != null) {
    	 		// El usuario ya estaba registrado
    	 		return "redirect:/calendario";
    	 	}else {
    	 		// El usuario se está registrando
    	 		return "redirect:/ponerContrasena";
    	 	}
    	    
	 	}catch(Exception e) {
	 		return "redirect:/register";
	 	}    
    }

    /**
     * Método que recibe el codigo de la autorización de google para poder acceder al token de acceso
     * @param code codigo de autorización
     * @return
     */
    private String fetchAccessToken(String code, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "System.getenv("GOOGLE_CLIENT_ID")"); // Tu ID de cliente de Google
        map.add("client_secret", "System.getenv("GOOGLE_CLIENT_SECRET")"); // Tu secreto de cliente de Google
        map.add("code", code); // El código de autorización obtenido de Google
        map.add("redirect_uri", "http://localhost:9000/oauth2/callback/google"); // La URI de redirección configurada en Google
        map.add("grant_type", "authorization_code"); // Indica que estamos utilizando el código de autorización para obtener el token

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token", request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
            String accessToken = jsonObject.get("access_token").getAsString();
            String refreshToken = jsonObject.get("refresh_token").getAsString();
            session.setAttribute("refreshToken", refreshToken);
            return accessToken; // Extrae el token de acceso de la respuesta JSON
        }
        return null;
    }

    // Método para construir la URL de autorización de Google
    private String buildGoogleAuthorizationUrl() {
        String clientId = "System.getenv("GOOGLE_CLIENT_ID")"; // Tu ID de cliente de Google
        String redirectUri = "http://localhost:9000/oauth2/callback/google"; // La URI de redirección configurada en Google
        return "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + clientId +
               "&redirect_uri=" + redirectUri +
               "&response_type=code" +
               "&scope=https://www.googleapis.com/auth/calendar email profile" +
               "&access_type=offline"; // Definición de los permisos solicitados
    }
}
