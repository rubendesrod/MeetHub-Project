package com.meethub.project.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.model.ConferenceData;
import com.google.api.services.calendar.model.CreateConferenceRequest;
import com.google.api.services.calendar.model.EntryPoint;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.meethub.project.models.ReunionDTO;
import com.meethub.project.models.Usuario;
import com.meethub.project.services.GoogleUserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CrearReunionController {

	@Autowired
	private GoogleUserService googleService;
	
	
	
	@GetMapping("/crearReunion")
	public String mostrarCrearReunion(HttpSession  session, Model model) {
		if (comprobacionAutenticacion(session)) {
			Usuario usu = (Usuario) session.getAttribute("usuario");
			if (gestionarTokenDeAcceso(usu, session)) {
				ReunionDTO reunion = new ReunionDTO();
				model.addAttribute("usuario", usu);
				model.addAttribute("reunion", reunion);
				return "crearReunion";
			}else {
				return "redirect:/oauth2/authorize/google";
			}
	    }
		 return "redirect:/";
	}
	
	
	
	@PostMapping("/crearReunion/crear")
	private String crearReunion(HttpSession sesion,
								Model model,
								@ModelAttribute ReunionDTO reu) 
	{
		Usuario usu = (Usuario) sesion.getAttribute("usuario");
		model.addAttribute("usuario", usu);
		model.addAttribute("reunion", reu);
		// Establezco unos formateadores para la FECHA y HORAS
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	    
	    try {
	    	// Parseo la fecha
	        LocalDate fechaReunion = LocalDate.parse(reu.getDateReunion(), dateFormatter);

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
	        LocalTime horaInicio = LocalTime.parse(reu.getStart(), timeFormatter);
	        LocalTime horaFin = LocalTime.parse(reu.getEnd(), timeFormatter);

	        // Compruebo si la hora de inicio es mayor que la de fin
	        if (!horaInicio.isBefore(horaFin)) {
	            model.addAttribute("errorHora", "La hora de inicio debe ser anterior a la hora de fin.");
	        }
	    } catch (DateTimeParseException e) {
	    	// En el caso de que no se puedan parsea las horas
	        model.addAttribute("errorHora", "Formato de hora inválido.");
	    }
		
	    // Compruebo que el nombre contiene letras y que no esta vacio
		if (reu.getNombre() == null || reu.getNombre().isEmpty() || !reu.getNombre().matches(".*[a-zA-Z]+.*")) {
	        model.addAttribute("errorNombre", "El nombre debe contener al menos una letra.");
	    }

		// Compruebo que las descripcion no está vacía y si lo está la establezo y a mano
	    if (reu.getDescripcion() == null || reu.getDescripcion().isEmpty()) {
	        reu.setDescripcion("["+reu.getModo()+"]"+"  Sin descripción");
	    }else {
	    	
	    }

	    // Compruebo que minimo haya un participante en la reunión
	    if (reu.getInvitados() == null || reu.getInvitados().isEmpty()) {
	        model.addAttribute("errorParticipantes", "Debe haber al menos un participante.");
	    }

	    // Asumiendo que todas las validaciones están correctas
	    if (!model.containsAttribute("errorNombre") && !model.containsAttribute("errorFecha") &&
	        !model.containsAttribute("errorHora") && !model.containsAttribute("errorParticipantes")) {

	    	String accessToken = (String) sesion.getAttribute("accessToken");
			 
		    GoogleCredentials credentials = GoogleCredentials.create(new AccessToken(accessToken, null))
		        	    .createScoped(Arrays.asList("https://www.googleapis.com/auth/calendar"));  
		    
		    HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(credentials);
		    
	        // Configurar los participantes
	        List<EventAttendee> attendees = new ArrayList<>();
	        for (String email : reu.getInvitados().split(",")) {
	            attendees.add(new EventAttendee().setEmail(email.trim()));
	        }

	        // Configurar datos de la conferencia para Google Meet
	        CreateConferenceRequest createRequest = new CreateConferenceRequest().setRequestId(UUID.randomUUID().toString());
	        ConferenceData conferenceData = new ConferenceData().setCreateRequest(createRequest);
	        
	        // Crear y configurar el evento
	        Event event = new Event()
	            .setSummary(reu.getNombre())
	            .setDescription(reu.getDescripcion())
	            .setAttendees(attendees)
	            .setConferenceData(conferenceData);

	        String dateTimeStart = reu.getDateReunion() + "T" + reu.getStart() + ":00";
	        String dateTimeEnd = reu.getDateReunion() + "T" + reu.getEnd() + ":00";
	        event.setStart(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(dateTimeStart)));
	        event.setEnd(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(dateTimeEnd)));

	        // Llamar a la API de Google Calendar para insertar el evento
	        try {
	            com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(
	                    new NetHttpTransport(), new GsonFactory(), credentialsAdapter)
	                .setApplicationName("MeetHub")
	                .build();

	            event = service.events().insert((String)sesion.getAttribute("IDCalendario"), event)  // Usar "primary" o el ID del calendario específico
	                    .setConferenceDataVersion(0)
	                    .execute();
	            
	            if (event.getConferenceData() != null && event.getConferenceData().getEntryPoints() != null) {
	                // Ahora busco entre los datos de conferencia el primer dato que tenga como nombre "Video" es será el enlace
	            	Optional<EntryPoint> meetLink = event.getConferenceData().getEntryPoints().stream()
	                    .filter(ep -> "video".equals(ep.getEntryPointType()))
	                    .findFirst();
	            	// Comprubeo que se ha devuelto una instancia en Entrypoint
	                if (meetLink.isPresent()) {
	                    model.addAttribute("ReunionCreada", "La Reunión ha sido creada con éxito. Enlace de Meet: " + meetLink.get().getUri());
	                } else {
	                    model.addAttribute("Error", "No se pudo generar el enlace de Meet.");
	                }
	            } else {
	                model.addAttribute("Error", "No se encontraron datos de conferencia.");
	            }
	            
	            model.addAttribute("Correcto", "La Reunión ha sido creada con éxito" + event.getHangoutLink());
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al crear el evento en Google Calendar: " + e.getMessage());
	            return "crearReunion";
	        }
		
	    }
		return "crearReunion";
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
