package com.meethub.project.services;

import com.meethub.project.models.Usuario;
import com.meethub.project.repositorys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para manejar las operaciones relacionadas con usuarios.
 * Proporciona métodos para gestionar la creación, eliminación, actualización y consulta de usuarios.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor para inyección de dependencias del repositorio de usuarios.
     *
     * @param usuarioRepository el repositorio de usuarios que será inyectado
     */
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Guarda un usuario en la base de datos.
     *
     * @param usuario el usuario a guardar
     * @return el usuario guardado
     */
    @Transactional
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Encuentra todos los usuarios disponibles en la base de datos.
     *
     * @return una lista de usuarios
     */
    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id el ID del usuario a buscar
     * @return un objeto Optional que contiene el usuario si es encontrado
     */
    public Optional<Usuario> findUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     *
     * @param id el ID del usuario a eliminar
     * @throws RuntimeException si el usuario no se encuentra
     */
    @Transactional
    public void deleteUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuario el usuario con la información actualizada
     * @return el usuario actualizado
     * @throws RuntimeException si el usuario no existe
     */
    @Transactional
    public Usuario updateUsuario(Usuario usuario) {
        if (usuario.getId() == null || !usuarioRepository.existsById(usuario.getId())) {
            throw new RuntimeException("Intento de actualizar un usuario que no existe con ID: " + usuario.getId());
        }
        return usuarioRepository.save(usuario);
    }
}
