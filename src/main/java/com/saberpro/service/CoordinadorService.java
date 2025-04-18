package com.saberpro.service;

import com.saberpro.model.Coordinador;
import com.saberpro.repository.CoordinadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoordinadorService {
    
    @Autowired
    private CoordinadorRepository coordinadorRepository;
    
    public List<Coordinador> findAll() {
        return coordinadorRepository.findAll();
    }
    
    public Optional<Coordinador> findById(String id) {
        return coordinadorRepository.findById(id);
    }
    
    public Optional<Coordinador> findByCedula(String cedula) {
        return coordinadorRepository.findByCedula(cedula);
    }
    
    public Coordinador save(Coordinador coordinador) {
        return coordinadorRepository.save(coordinador);
    }
    
    public void deleteById(String id) {
        coordinadorRepository.deleteById(id);
    }
    
    public boolean existsByCedula(String cedula) {
        return coordinadorRepository.existsByCedula(cedula);
    }
    
    public boolean authenticate(String cedula, String password) {
        Optional<Coordinador> coordinadorOpt = coordinadorRepository.findByCedula(cedula);
        if (coordinadorOpt.isPresent()) {
            Coordinador coordinador = coordinadorOpt.get();
            return password.equals(coordinador.getPassword());
        }
        return false;
    }
}