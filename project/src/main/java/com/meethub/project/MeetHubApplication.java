package com.meethub.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ruben
 * @version 1.0
 * Clase principal de la aplicación con Spring Boot la cual es la que se ejecuta para poder lanzar la aplicación
 */
@SpringBootApplication
public class MeetHubApplication {

	
	/**
	 * Metodo main
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MeetHubApplication.class, args);
	}

	/**
	 * Metodo para un Bean que proporciona métodos para hacer solicitudes HTTP a servicios web, como sacar datos de un usuario desde el token
	 * @return Objeto RestTemplate
	 */
	 @Bean
	  public RestTemplate restTemplate() {
	       return new RestTemplate();
	  }
	
}
