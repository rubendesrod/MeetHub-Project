package com.meethub.project.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.meethub.project.models.EventoDTO;
import com.meethub.project.models.Usuario;
import com.meethub.project.services.GoogleUserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CalendarioController {

	@Autowired
	private GoogleUserService googleService;
	
	
	
	/**
	 * Método que se encarga de recoger la llamada a la vista del calendario y le pasa como modelo el usuario,
	 * que ha sido guardado en la sesión
	 * @param session Session que mantiene el navegador en las respuestas HTTP
	 * @param model Objeto que se le pasa a la vista con atributos
	 * @return String de la vista a la que queremos acceder
	 */
	@GetMapping("/calendario")
	public String getEventos(HttpSession session, Model model) {
		
		if (comprobacionAutenticacion(session)) {
	    	Usuario usuario = (Usuario) session.getAttribute("usuario");
	    	// Compruebo que el usuario sea diferente a null
	        if (usuario != null) {
	            model.addAttribute("usuario", usuario);
	        } else {
	            // Manjeo el caso donde no hay usuario en la sesión
	            return "redirect:/";
	        }

	        if (!gestionarTokenDeAcceso(usuario, session)) {
	            return "redirect:/oauth2/authorize/google";
	        }

	        String accessToken = (String) session.getAttribute("accessToken");
	        GoogleCredentials credentials = GoogleCredentials.create(new AccessToken(accessToken, null))
	        	    .createScoped(Arrays.asList("https://www.googleapis.com/auth/calendar"));

	        List<CalendarListEntry> calendariosDecodificados;
        	try {
        		calendariosDecodificados = getCalendarios(credentials);
        	    if (!calendariosDecodificados.isEmpty()) {
        	    	
        	    	// Ahora codifico los ID de los calendarios
        	    	List<CalendarListEntry> calendariosCodificados = codificarIdsDeCalendarios(calendariosDecodificados);
        	    	
        	        String PrimerID = calendariosDecodificados.get(0).getId();
        	        
        	        // Hago una comprobaciónd de que el usuario haya entrado 
        	        // ya a la pagina para que salga su última calendario seleccionado
        	        String idcalendario = (String)session.getAttribute("IDCalendario");
        	        if(idcalendario == null) {
        	        	session.setAttribute("IDCalendario", PrimerID);
        	        }
        	        
        	        model.addAttribute("calendarios", calendariosCodificados);

        	        return "calendario";
        	    } else {
        	        model.addAttribute("message", "No calendars available");
        	        return "redirect:/calendario";
        	    }
        	} catch (Exception e) {
        	    e.printStackTrace();
        	    return null;
        	}
	        
	    }
	    return "redirect:/";
	}
	
	
	
	/**
	 * Método que se encarga de responder la llamada del event de full calendar y devolver los Evento en JSON
	 * con el ResponseBody, para cargarles
	 * @param sesion
	 * @return
	 */
	@GetMapping("/calendario/eventos")
    @ResponseBody
    public List<EventoDTO> getEvents(@RequestParam(required = false) String calendarId, HttpSession sesion) {
		
		String IDCalendario;
		if (calendarId == null || calendarId.isEmpty()) {
			IDCalendario = (String) sesion.getAttribute("IDCalendario");
	    }else {
	    	IDCalendario = decodificarIdDeCalendario(calendarId);
	    	sesion.setAttribute("IDCalendario", IDCalendario);
	    }
		
		
		
		String accessToken = (String) sesion.getAttribute("accessToken");
		 
	    GoogleCredentials credentials = GoogleCredentials.create(new AccessToken(accessToken, null))
	        	    .createScoped(Arrays.asList("https://www.googleapis.com/auth/calendar"));
	        
	    HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(credentials);
	    
	   // Calcula para sacar los eventos de dos meses antes del actual

	    // Crear una instancia de Calendar usando el nombre completamente calificado
	    java.util.Calendar cal = java.util.Calendar.getInstance();
	    // Restar un año
	    cal.add(java.util.Calendar.YEAR, -1);
	    // Establecer el día del mes a 1 para empezar desde el principio del mes
	    cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
	    // Resetear hora, minutos y segundos a cero
	    cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
	    cal.set(java.util.Calendar.MINUTE, 0);
	    cal.set(java.util.Calendar.SECOND, 0);
	    cal.set(java.util.Calendar.MILLISECOND, 0);

	    DateTime startTime = new DateTime(cal.getTimeInMillis());
        // Recojo todos los eventos de ese calendario
        Events events = null;
		try {
			
			com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(new NetHttpTransport(), new GsonFactory(), credentialsAdapter)
		            .setApplicationName("MeetHub")
		            .build();
			
			events = service.events().list(IDCalendario)
							.setTimeMin(startTime)
			        		.execute();
			
			// Covierto a DTOs todos los Eventos
	        List<EventoDTO> eventosDTO = convertirAEventosDTO(events);
	        
	        return eventosDTO;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return null;   
        
    }
	
	
	
	/**
	 * Método que recoge el envío del formulario que se realiza en un modal a la hora enviar un evento
	 * @param nombre nombre del evento
	 * @param descripcion descripcion del evento
	 * @param fecha dia en el que se quiere crear el evento
	 * @param sesion session que se guarda en la cabecera HTTP
	 * @return Un redireccionamiento al calendario para que cargue ese evento recien creado
	 */
	@PostMapping("/calendario/crear/evento")
	public String crearEvento(@RequestParam("eventName") String nombre,
            				@RequestParam("eventDescrip") String descripcion,
            				@RequestParam("eventDate") String fecha,
            				HttpSession sesion) 
	{
		
		String accessToken = (String) sesion.getAttribute("accessToken");
		 
	    GoogleCredentials credentials = GoogleCredentials.create(new AccessToken(accessToken, null))
	        	    .createScoped(Arrays.asList("https://www.googleapis.com/auth/calendar"));  
	    
	    HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(credentials);

        // Añade el evento al calendario 'primary'
        String calendarId = (String)sesion.getAttribute("IDCalendario");  // Puede ser cualquier ID de calendario que tengas acceso
        try {
        	 // Instancia del Calendario de google
    	    com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(new NetHttpTransport(), new GsonFactory(), credentialsAdapter)
    	            .setApplicationName("MeetHub")
    	            .build();
    	    
    	    String des;
    	    // Compruebo que los datos no estén vacíos
    	    if(descripcion == null || descripcion.isEmpty()) {
    	    	des = "Sin descripcion";
    	    }else {
    	    	des = descripcion;
    	    }
    	    
    	    // creo el evento
    	    Event event = new Event()
                    .setSummary(nombre)
                    .setDescription(des);
    	    
    	    // Sumo un dia a la fecha actual porque un evento dura todo el dia
    	    // Convertir la fecha de inicio y sumar un día para la fecha de fin
    	    java.util.Calendar calendar = new java.util.GregorianCalendar();
    	    
    	    // Ahora seteo la fecha del usuario que ha seleccionado en el calendario
            EventDateTime start = new EventDateTime()
                    .setDate(new com.google.api.client.util.DateTime(fecha));
            event.setStart(start);

            EventDateTime end = new EventDateTime()
                    .setDate(new com.google.api.client.util.DateTime(fecha));
            event.setEnd(end);

    	    
    	    // Ejecuto la inserccion del evento en la id del calendario que esta seleccionado
			event = service.events().insert(calendarId, event).execute();
			
			
			return "redirect:/calendario";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	/**
	 * Método que se encarga de codificar los ID's de los calendarios para que luego no haya problemas al pasarles por la URL
	 * @param calendarios Lista de las ids de los calendarios
	 * @return List de las ID's codificadas
	 */
	public List<CalendarListEntry> codificarIdsDeCalendarios(List<CalendarListEntry> calendarios) {
	    for (CalendarListEntry cal : calendarios) {
	        try {
	            String codificado = URLEncoder.encode(cal.getId(), "UTF-8");
	            cal.setId(codificado);
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("Error al codificar ID de calendario", e);
	        }
	    }
	    return calendarios;
	}
	
	
	/**
	 * Método que se necarga de decodificar la ID del calendario seleccionado por el usuario y google la lea perfectamente
	 * @param codificado ID codificada 
	 * @return ID del calendario decodificada
	 */
	public String decodificarIdDeCalendario(String IdCodificada) {
	    try {
	        return URLDecoder.decode(IdCodificada, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	        // Manejar la excepción adecuadamente
	        throw new RuntimeException("Error al decodificar ID de calendario", e);
	    }
	}

	
	
	/**
	 * Método para convertir lo eventos de la API en EventosDTO y que full calendar lo entienda
	 * @param events evento que hemos obtenido de un comentario
	 * @return
	 */
	public List<EventoDTO> convertirAEventosDTO(Events events) {
		List<EventoDTO> eventosDTO = new ArrayList<>();
	    if (events.getItems() != null) {
	        for (Event evento : events.getItems()) {
	            EventoDTO dto = new EventoDTO();
	            dto.setId(evento.getId());
	            dto.setTitle(evento.getSummary());
	            if (evento.getStart() != null && evento.getStart().getDateTime() != null) {
	                dto.setStart(new Date(evento.getStart().getDateTime().getValue()));
	            } else if (evento.getStart() != null && evento.getStart().getDate() != null) {
	                dto.setStart(new Date(evento.getStart().getDate().getValue()));
	            }
	            if (evento.getEnd() != null && evento.getEnd().getDateTime() != null) {
	                dto.setEnd(new Date(evento.getEnd().getDateTime().getValue()));
	            } else if (evento.getEnd() != null && evento.getEnd().getDate() != null) {
	                dto.setEnd(new Date(evento.getEnd().getDate().getValue()));
	            }
	            dto.setDescription(evento.getDescription());
	            // Compruebo que hay enlace a google meet
	            if (evento.getHangoutLink() != null) {
	                dto.setUrl(evento.getHangoutLink());
	            }else {
	                dto.setUrl(null);
	            }
	            eventosDTO.add(dto);
	        }
	    }
	    return eventosDTO;
	}
	
	
	
	/**
	 * Método para coger todos los Calendarios y sacar sus ID's por lo menos del primer calendario
	 * @param GoogleCredentials credenciales de google la cual se cogen con accesToken para poder realizar la llamada HTTP
	 * @returnn Lista de calendarios
	 */
	public List<CalendarListEntry> getCalendarios(GoogleCredentials credentials) throws IOException {
	    HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(credentials);

	    com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(new NetHttpTransport(), new GsonFactory(), credentialsAdapter)
	        .setApplicationName("MeetHub")
	        .build();

	    CalendarList calendarList = service.calendarList().list().execute();
	    return calendarList.getItems();
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
