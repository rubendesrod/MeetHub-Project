package com.meethub.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MeetHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetHubApplication.class, args);
	}

	/**
	 * Metodo para un Bean que proporciona métodos para hacer solicitudes HTTP a servicios web, como sacar datos de un usuario desde el token
	 * @return
	 */
	 @Bean
	  public RestTemplate restTemplate() {
	       return new RestTemplate();
	  }
	
}
