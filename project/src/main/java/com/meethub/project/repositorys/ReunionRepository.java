package com.meethub.project.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meethub.project.models.Reunion;

/**
 * Interfaz/repositorio que proporciona las consultas directas a la base de datos de la tabla de las Reuniones
 */
@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Long> {
   
	/**
	 * Meotod de la interfaz que se encarga de buscar al usuario creador de la reunión
	 * @param usuarioId id del usuario que queremos encontrar
	 * @return List_Reunion lista de Reuniones por que puede tener mas de una reunion creada por el
	 */
	List<Reunion> findByUsuarioId(Long usuarioId);
	
	/**
	 * Método de la interfaz que se encarga de buscar una reunión por ID
	 * @param reunionId id de la reunión que queremos encontrar
	 */
	Optional<Reunion> findById(Long reunionId);
	
}

