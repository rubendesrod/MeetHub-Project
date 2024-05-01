package com.meethub.project.services;

import com.meethub.project.repositorys.ReunionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para manejar las operaciones relacionadas con reuniones.
 * Contiene métodos para gestionar la creación, eliminación y consulta de reuniones.
 */
@Service
public class ReunionService {

    private final ReunionRepository reunionRepository;

    /**
     * Constructor para la inyección de dependencias del repositorio de reuniones.
     *
     * @param reunionRepository el repositorio de reuniones que será inyectado
     */
    @Autowired
    public ReunionService(ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }

    /**
     * Elimina una reunión basada en el ID proporcionado.
     * 
     * Este método es transaccional y asegura que toda la operación se maneje
     * como una transacción coherente.
     *
     * @param id el ID de la reunión a eliminar
     * @throws RuntimeException si no se encuentra la reunión con el ID dado
     */
    @Transactional
    public void deleteReunion(Long id) {
        if (!reunionRepository.existsById(id)) {
            throw new RuntimeException("Reunión no encontrada con ID: " + id);
        }
        reunionRepository.deleteById(id);
    }

}
