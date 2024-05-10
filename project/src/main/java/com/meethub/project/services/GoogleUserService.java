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

/**
 * Servicio que proporciona métodos para interactuar con la API de Google OAuth para gestionar detalles de usuario y tokens de acceso.
 *
 * @author Ruben
 * @version 1.0
 */
@Service
public class GoogleUserService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Obtiene los detalles del usuario de Google basados en el accessToken proporcionado.
     *
     * @param accessToken El token de acceso OAuth proporcionado por Google.
     * @return Usuario El objeto Usuario con los datos obtenidos de Google, o null si no hay respuesta.
     */
    public Usuario obtenerDetallesUsuario(String accessToken) {
        String url = "https://www.googleapis.com/oauth2/v2/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
            Usuario usuario = new Usuario();
            usuario.setEmail(jsonObject.get("email").getAsString());
            usuario.setNombre(jsonObject.get("given_name").getAsString());
            usuario.setApellidos(jsonObject.get("family_name").getAsString());
            usuario.setAvatar(jsonObject.get("picture").getAsString());

            try {
                usuario.setFechaNacimiento(jsonObject.get("birthdate").getAsString());
            } catch (Exception e) {
                usuario.setFechaNacimiento(null);
            }
            
            return usuario;
        }
        return null;
    }

    /**
     * Renueva el token de acceso utilizando el refreshToken proporcionado.
     *
     * @param refreshToken El token de actualización usado para obtener un nuevo token de acceso.
     * @return String El nuevo token de acceso, o null si la renovación falla.
     */
    public String refreshAccessToken(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", System.getenv("GOOGLE_CLIENT_ID"));
        map.add("client_secret", System.getenv("GOOGLE_CLIENT_SECRET"));
        map.add("refresh_token", refreshToken);
        map.add("grant_type", "refresh_token");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token", request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
            return jsonObject.get("access_token").getAsString();
        } else {
            return null;
        }
    }

    /**
     * Verifica si el token de acceso almacenado en la sesión ha expirado.
     *
     * @param session La sesión HTTP donde está almacenado el token de expiración.
     * @return boolean Verdadero si el token ha expirado, falso en caso contrario.
     */
    public boolean accessTokenExpired(HttpSession session) {
        LocalDateTime tokenExpiration = (LocalDateTime) session.getAttribute("tokenExpiration");
        return LocalDateTime.now().isAfter(tokenExpiration);
    }

    /**
     * Verifica si es posible obtener un nuevo token de acceso usando el refreshToken.
     *
     * @param refreshToken El token de actualización a evaluar.
     * @return boolean Verdadero si no se puede obtener un nuevo token, indicando que el refreshToken ha expirado.
     */
    public boolean refreshTokenExpired(String refreshToken) {
        return refreshAccessToken(refreshToken) == null;
    }
}
