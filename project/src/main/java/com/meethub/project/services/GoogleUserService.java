package com.meethub.project.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meethub.project.models.Usuario;

import jakarta.servlet.http.HttpSession;

@Service
public class GoogleUserService {

    @Autowired
    private RestTemplate restTemplate;

    public Usuario obtenerDetallesUsuario(String accessToken) {
        String url = "https://www.googleapis.com/oauth2/v2/userinfo"; // Endpoint para la información del usuario
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Usar el token de acceso en el header Authorization
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
            Usuario usuario = new Usuario();
            usuario.setEmail(jsonObject.get("email").getAsString());
            usuario.setNombre(jsonObject.get("given_name").getAsString());
            try {
            	 usuario.setApellidos(jsonObject.get("family_name").getAsString());
            }catch (Exception e) {
            	usuario.setApellidos("");
            }
           
            try {
            	usuario.setAvatar(jsonObject.get("picture").getAsString());            	
            }catch(Exception e) {
            	usuario.setAvatar("");
            }
            return usuario;
        }
        return null;
    }
    
    /**
     * Método que se encarga de renovar el token de acceso
     * @param refreshToken token de aztualización para revonvar el token de acceso
     */
    public String refreshAccessToken(String refreshToken) {
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "System.getenv("GOOGLE_CLIENT_ID")");
        map.add("client_secret", "System.getenv("GOOGLE_CLIENT_SECRET")");
        map.add("refresh_token", refreshToken);
        map.add("grant_type", "refresh_token");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token", request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
            return jsonObject.get("access_token").getAsString();
        } else {
        	// El http no ha conseguido respuesta
            return null;
        }
    }

    /**
     * Metodo que se encarga de verificar si el token de acceso ha expirado
     * @return boolean True/false si se ha expirado el token
     */
    public boolean accessTokenExpired(HttpSession session) {
         LocalDateTime tokenExpiration = (LocalDateTime) session.getAttribute("tokenExpiration");
         return LocalDateTime.now().isAfter(tokenExpiration);
    }

    /**
     * Metodo que se encarga de comprobar si el token de actualización ha expirado o no
     * @param refreshToken Token de actualización para sacar mas tokens de acceso
     * @return
     */
    public boolean refreshTokenExpired(String refreshToken) {
    	String newAccessToken = refreshAccessToken(refreshToken);
        return newAccessToken == null;
    }
    
}
