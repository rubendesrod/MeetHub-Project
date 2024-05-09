package com.meethub.project.repositorys;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.meethub.project.models.Usuario;

/**
 * Interfaz/Repositorio que proporciona las consultas directas a la base de datos para la tabla Usuario.
 * Permite la realización de operaciones de búsqueda, guardado, actualización y borrado de usuarios.
 *
 * @author Ruben
 * @version 1.0
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	/**
	 * Encuentra un usuario por su correo electrónico.
	 * 
	 * @param email El correo electrónico del usuario que se desea encontrar.
	 * @return Un Optional conteniendo el usuario encontrado, o un Optional vacío si no se encuentra ningún usuario con ese correo electrónico.
	 */
	Optional<Usuario> findByEmail(String email);
	
}
