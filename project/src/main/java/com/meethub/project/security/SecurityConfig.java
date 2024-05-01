package com.meethub.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@SuppressWarnings("deprecation")
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeRequests(authorizeRequests ->
	            authorizeRequests
	                .requestMatchers("/", "/register").permitAll()  // Permitir acceso no autenticado
	                .anyRequest().authenticated()  // Requiere autenticación para todas las demás rutas
	        )
	        .formLogin(formLogin ->
	            formLogin
	                .loginPage("/")  // Página de login personalizada
	                .defaultSuccessUrl("/calendario", true)  // Redirige aquí tras login exitoso
	                .loginProcessingUrl("/auth/login-post")  // URL para procesar el formulario de login
	                .permitAll()  // Permitir acceso a todos al formulario de login
	        )
	        .logout(logout ->
	            logout
	                .logoutUrl("/auth/logout")  // URL para iniciar el proceso de logout
	                .logoutSuccessUrl("/")  // Redirige aquí tras logout exitoso
	                .permitAll()
	        );
	    return http.build();
	}




    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**");
    }
}
