package com.meethub.project.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.meethub.project.models.ReunionDTO;
import com.meethub.project.models.Usuario;
import com.meethub.project.services.GoogleUserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MisReunionesController {

	 @Autowired
	private GoogleUserService googleService;
	 
	 
	 
	 @GetMapping("/misReuniones")
		public String mostrarMisReuniones(HttpSession  sesion, Model model) {
			if (!comprobacionAutenticacion(sesion)) {
				return "redirect:/";
		    }
			Usuario usu = (Usuario) sesion.getAttribute("usuario");
			if(!gestionarTokenDeAcceso(usu, sesion)) {
				return "redirect:/oauth2/authorize/google";
			}
			
			// Extraccion de las credenciales de google
			String accessToken = (String) sesion.getAttribute("accessToken");
			com.google.api.services.calendar.Calendar service = getCalendarService(accessToken);
		    
		    // Llamada a la API de google par las reuniones creadas por mi
		    Events reuniones = getReuniones(service, model, sesion, usu);
		    List<ReunionDTO> reunionionesDTO = convertirAEventosDTO(reuniones, usu.getEmail());
			
		    // Falta añadir el array de las reuniones
		    model.addAttribute("reuniones", reunionionesDTO);
			model.addAttribute("usuario", usu);
			return "misReuniones";
		}	
	 
	 
	 
	 @PostMapping("/misReuniones/actualizar")
	 private String actualizarReunion(HttpSession sesion, 
			 				@ModelAttribute ReunionDTO reunion,
			 				Model model) 
	 {
		 
		if (!comprobacionAutenticacion(sesion)) {
			return "redirect:/";
	    }
		Usuario usu = (Usuario) sesion.getAttribute("usuario");
		if(!gestionarTokenDeAcceso(usu, sesion)) {
			return "redirect:/oauth2/authorize/google";
		}
		
		
	    
	    // Extraccion de las credenciales de google
		String accessToken = (String) sesion.getAttribute("accessToken");
		com.google.api.services.calendar.Calendar service = getCalendarService(accessToken);
		
		// Extraigo el ID del calendario
		String idCalendario = (String) sesion.getAttribute("IDCalendario");
	    
		// Llamo a la funcion de comprobar validaciones para que modifique los atributos necesarios
		comprobarValidaciones(reunion, model);
		
	    // Compruebo que no haya fallado ninguna validacion
		if (!model.containsAttribute("errorNombre") && !model.containsAttribute("errorFecha") &&
		        !model.containsAttribute("errorHora") && !model.containsAttribute("errorParticipantes")) {
			
			try {
				// Recuperar el evento existente
				com.google.api.services.calendar.model.Event event = service.events().get(idCalendario, reunion.getId()).execute();
				
				// Actualizar los detalles del evento
				event.setSummary(reunion.getNombre());
				event.setDescription(reunion.getDescripcion());
				
				String timeZoneOffset = "+02:00";  // Ajusta este valor basado en si España está en CET (UTC+1) o CEST (UTC+2)
				String dateTimeStart = reunion.getDateReunion() + "T" + reunion.getStart() + ":00" + timeZoneOffset;
				String dateTimeEnd = reunion.getDateReunion() + "T" + reunion.getEnd() + ":00" + timeZoneOffset;

				// Crear los objetos DateTime de Google
				com.google.api.client.util.DateTime startDateTime = new com.google.api.client.util.DateTime(dateTimeStart);
				com.google.api.client.util.DateTime endDateTime = new com.google.api.client.util.DateTime(dateTimeEnd);

				// Configurar en el evento
				event.setStart(new EventDateTime().setDateTime(startDateTime).setTimeZone("Europe/Madrid"));
				event.setEnd(new EventDateTime().setDateTime(endDateTime).setTimeZone("Europe/Madrid"));


				
//				// Configurar los participantes
//		        List<EventAttendee> attendees = new ArrayList<>();
//		        for (String email : reunion.getInvitados().split(",")) {
//		            attendees.add(new EventAttendee().setEmail(email.trim()));
//		        }
				
				// Enviar la solicitud de actualización
				event = service.events().update(idCalendario, reunion.getId(), event).execute();
				
				
				
				model.addAttribute("mensaje", "Reunión actualizada correctamente");
				} catch (Exception e) {
					model.addAttribute("error", "Error al actualizar la reunión, intentelo mas tade");
					return "misReuniones";
				}
			}
		
			// Vuelvo a llamar a la api para que me saque todas las reuniones y saque la reunión actualizada y se la muestre al usuario
			Events reuniones = getReuniones(service, model, sesion, usu);
			List<ReunionDTO> reunionionesDTO = convertirAEventosDTO(reuniones, usu.getEmail());
			
			// Falta añadir el array de las reuniones
			model.addAttribute("reuniones", reunionionesDTO);
			model.addAttribute("usuario", usu);
		 return "misReuniones";
	 }
	
	 /**
	  * Método que se encargar de devolver el servicio del calendario para poder gestionar las llamadas a la API
	  * @param accessToken token de acceso para la autorizacion
	  * @return El Calendar service para las llamadas
	  */
	 private com.google.api.services.calendar.Calendar getCalendarService(String accessToken){
		GoogleCredentials credentials = GoogleCredentials.create(new AccessToken(accessToken, null))
	        	    .createScoped(Arrays.asList("https://www.googleapis.com/auth/calendar"));  
	    
	    HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(credentials);
		
	    com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(new NetHttpTransport(), new GsonFactory(), credentialsAdapter)
		        .setApplicationName("MeetHub")
		        .build();
	    
	    return service;
	 }
	 
	 
	 /**
	  * Metodo que se encarga de obtener las reuniones en la que el usuario es el creador de ellas
	  * @param service servicio obtenido con las crendenciales de google calendar
	  * @param model modelo de la vista
	  * @param sesion sesion que guarda en navegador en las respuestas HTTP
	  * @param usu Instancia del objeto Usuario
	  * @return Lista con los eventos que el usuario es creador
	  */
	 private Events getReuniones(Calendar service, Model model, HttpSession sesion, Usuario usu){
		 	try {

		 		String calendarId = (String) sesion.getAttribute("IDCalendario");
		    	
				Events events = service.events().list(calendarId)
				        .setSingleEvents(true)
				        .execute();
			
				return events;
			} catch (Exception e) {
				model.addAttribute("error", "No se han podido mostrar tus reuniones");
				e.printStackTrace();
				return null;
			}
			
	 }
	 
	 
	 private void comprobarValidaciones(ReunionDTO reunion, Model model) {
		 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			
			// Compruebo todas las validaciones
			try {
		    	// Parseo la fecha
		        LocalDate fechaReunion = LocalDate.parse(reunion.getDateReunion(), dateFormatter);

		        // Compruebo que la fecha es mayor que la actual
		        if (fechaReunion.isBefore(LocalDate.now())) {
		            model.addAttribute("errorFecha", "La fecha debe ser hoy o en el futuro.");
		        }
		    } catch (DateTimeParseException e) {
		    	// En el caso de no poder parsear la fecha
		        model.addAttribute("errorFecha", "Formato de fecha inválido.");
		    }

		    try {
		    	// Parseo las horas de inicio y de fin
		        LocalTime horaInicio = LocalTime.parse(reunion.getStart(), timeFormatter);
		        LocalTime horaFin = LocalTime.parse(reunion.getEnd(), timeFormatter);

		        // Compruebo si la hora de inicio es mayor que la de fin
		        if (!horaInicio.isBefore(horaFin)) {
		            model.addAttribute("errorHora", "La hora de inicio debe ser anterior a la hora de fin.");
		        }
		    } catch (DateTimeParseException e) {
		    	// En el caso de que no se puedan parsea las horas
		        model.addAttribute("errorHora", "Formato de hora inválido.");
		    }
			
		    // Compruebo que el nombre contiene letras y que no esta vacio
			if (reunion.getNombre() == null || reunion.getNombre().isEmpty() || !reunion.getNombre().matches(".*[a-zA-Z]+.*")) {
		        model.addAttribute("errorNombre", "El nombre debe contener al menos una letra.");
		    }

			// Compruebo que las descripcion no está vacía y si lo está la establezo y a mano
		    if (reunion.getDescripcion() == null || reunion.getDescripcion().isEmpty()) {
		        reunion.setDescripcion("["+reunion.getModo()+"]"+"  Sin descripción");
		    }

		    // Compruebo que minimo haya un participante en la reunión
		    if (reunion.getInvitados() == null || reunion.getInvitados().isEmpty()) {
		        model.addAttribute("errorParticipantes", "Debe haber al menos un participante.");
		    }
	 }
	 
	 /**
	 * Método para convertir lo eventos de la API en EventosDTO y que full calendar lo entienda
	 * @param events evento que hemos obtenido de un comentario
	 * @param email Email del usuario para comprobar que solo el es el creador de la reunion
	 * @return
	 */
	public List<ReunionDTO> convertirAEventosDTO(Events events, String email) {
		 List<ReunionDTO> reunionesDTO = new ArrayList<>();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		    Date now = new Date(); 
		    
		    if (events.getItems() != null) {
		        for (Event evento : events.getItems()) {
		        	if (evento.getCreator() != null && evento.getCreator().getEmail() != null && evento.getCreator().getEmail().equalsIgnoreCase(email)) {
		        		
		        		Date startDate = null;
		                if (evento.getStart().getDateTime() != null) {
		                    startDate = new Date(evento.getStart().getDateTime().getValue());
		                } else if (evento.getStart().getDate() != null) {
		                    startDate = new Date(evento.getStart().getDate().getValue());
		                }
		        		// Compruebo que sean a partir de la fecha acutal
		        		if (startDate != null && !startDate.before(now)) {
							ReunionDTO dto = new ReunionDTO();
							dto.setId(evento.getId());
							dto.setNombre(evento.getSummary());
							dto.setDescripcion(evento.getDescription());
							dto.setUrlMeet(evento.getHangoutLink()); // Directamente asigna el enlace de Meet si está disponible

							// Formatear y establecer la fecha de la reunión
							if (evento.getStart() != null) {
								if (evento.getStart().getDateTime() != null) {
									// Configurar fecha y hora de inicio usando DateTime
									dto.setDateReunion(
											dateFormat.format(new Date(evento.getStart().getDateTime().getValue())));
									dto.setStart(
											timeFormat.format(new Date(evento.getStart().getDateTime().getValue())));
								} else if (evento.getStart().getDate() != null) {
									// Configurar solo la fecha usando Date (para eventos de todo el día)
									dto.setDateReunion(
											dateFormat.format(new Date(evento.getStart().getDate().getValue())));
									dto.setStart(""); // No hay hora específica de inicio
								}
							}

							// Formatear y establecer la hora de finalización de la reunión
							if (evento.getEnd() != null) {
								if (evento.getEnd().getDateTime() != null) {
									dto.setEnd(timeFormat.format(new Date(evento.getEnd().getDateTime().getValue())));
								} else if (evento.getEnd().getDate() != null) {
									dto.setEnd(""); // No hay hora específica de finalización
								}
							}
							
							// Extraer y formatear los correos electrónicos de los participantes
							if (evento.getAttendees() != null) {
							    String participantes = evento.getAttendees().stream()
							                                .filter(a -> a.getEmail() != null) // Filtrar para asegurar que el email no sea nulo
							                                .map(a -> a.getEmail())            // Obtener el email
							                                .collect(Collectors.joining(", ")); // Unir con comas
							    dto.setInvitados(participantes);
							}

							reunionesDTO.add(dto);
						}
		        	}
		        }
		    }
		return reunionesDTO;
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
