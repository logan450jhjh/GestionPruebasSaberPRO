package com.saberpro.repository;

import com.saberpro.model.Coordinador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoordinadorRepository extends MongoRepository<Coordinador, String> {
    Optional<Coordinador> findByCedula(String cedula);
    boolean existsByCedula(String cedula);
}