package com.saberpro.repository;

import com.saberpro.model.Estudiante;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends MongoRepository<Estudiante, String> {
    Optional<Estudiante> findByCedula(String cedula);
    boolean existsByCedula(String cedula);
    
    // Consulta para obtener estudiantes ordenados por puntaje (ranking)
    List<Estudiante> findAllByOrderByPuntajeDesc();
    
    // Consulta para calcular promedios
    @Query(value = "{}", fields = "{ 'puntaje': 1, 'CE': 1, 'RC': 1, 'LC': 1, 'CC': 1, 'EN': 1, 'FPI': 1, 'PCME': 1, 'DS': 1 }")
    List<Estudiante> findAllPuntajes();
}