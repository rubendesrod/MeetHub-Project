package com.meethub.project.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meethub.project.models.Usuario;

/**
 * Interfaz/Repositorio que proporciona las consultas directas a la base de datos de la tabla Usuario
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	/**
	 * Método de la interfaz que se encarga de buscar un usuario por su email
	 * @param Email del usuario que queremos encontrar
	 * @return Estancia de la clase Usuario
	 */
	Optional<Usuario> findByEmail(String email);
	
	/**
	 * Metodo de la interfaz que se encarga de buscar un usuario por su id
	 * @param Id del usuario que queremos buscar
	 * @return Estancia de la clase Usuario
	 */
	Optional<Usuario> findById(Long id);
	
}
