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
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	
	Optional<Usuario> findByEmail(String email);
	
}
