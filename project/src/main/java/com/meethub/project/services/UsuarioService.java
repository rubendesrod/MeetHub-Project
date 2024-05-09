package com.meethub.project.services;

import com.meethub.project.PasswordUtil;
import com.meethub.project.models.Usuario;
import com.meethub.project.repositorys.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para manejar las operaciones relacionadas con usuarios en la aplicación MeetHub.
 * Proporciona métodos para gestionar la creación, eliminación, actualización y consulta de usuarios,
 * así como autenticación de los mismos.
 *
 * @author Ruben
 * @version 1.0
 */
@Service
public class UsuarioService {

    @Autowired
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
     * Guarda un usuario en la base de datos. La contraseña se almacena en forma cifrada.
     *
     * @param usuario el usuario a guardar
     * @return el usuario guardado
     */
    @Transactional
    public Usuario saveUsuario(Usuario usuario) {
        String passPlana = usuario.getContrasena();
        usuario.setContrasena(PasswordUtil.hashPassword(passPlana));
        return usuarioRepository.save(usuario);
    }

    /**
     * Autentica un usuario comparando la contraseña proporcionada con la almacenada en la base de datos.
     *
     * @param email El correo electrónico del usuario a autenticar.
     * @param contrasenaPlana La contraseña en texto plano para verificar.
     * @return true si la autenticación es exitosa; false de lo contrario.
     */
    public boolean autenticarUsuario(String email, String contrasenaPlana) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.isPresent() && PasswordUtil.verificarPassword(contrasenaPlana, usuario.get().getContrasena());
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
    public Optional<Usuario> findUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email El correo electrónico del usuario que queremos encontrar
     * @return Una instancia de la clase Usuario si se encuentra, encapsulada en un objeto Optional
     */
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     *
     * @param id el ID del usuario a eliminar
     * @throws RuntimeException si el usuario no se encuentra
     */
    @Transactional
    public void deleteUsuario(Integer id) {
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
        if (!usuarioRepository.existsById(usuario.getId())) {
            throw new RuntimeException("Intento de actualizar un usuario que no existe con ID: " + usuario.getId());
        }
        return usuarioRepository.save(usuario);
    }
}
