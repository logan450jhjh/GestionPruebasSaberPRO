package com.saberpro.service;

import com.saberpro.model.Estudiante;
import com.saberpro.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EstudianteService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }
    
    public Optional<Estudiante> findById(String id) {
        return estudianteRepository.findById(id);
    }
    
    public Optional<Estudiante> findByCedula(String cedula) {
        return estudianteRepository.findByCedula(cedula);
    }
    
    public Estudiante save(Estudiante estudiante) {
    	// Si el estudiante está anulado, no calculamos niveles ni estados
        if (estudiante.getAnulado() != null && estudiante.getAnulado()) {
            return estudianteRepository.save(estudiante);
        }
        
        // Asegurarse de que los niveles se calculen correctamente
        if (estudiante.getPuntaje() != null) {
            estudiante.setPuntaje(estudiante.getPuntaje()); // Esto recalculará el nivel y el estado
        }
        // ... resto del código para calcular niveles ...
        
        return estudianteRepository.save(estudiante);
    }
    
    public void deleteById(String id) {
        estudianteRepository.deleteById(id);
    }
    
    public void deleteByCedula(String cedula) {
        Optional<Estudiante> estudiante = estudianteRepository.findByCedula(cedula);
        estudiante.ifPresent(e -> estudianteRepository.deleteById(e.getId()));
    }
    
    public boolean existsByCedula(String cedula) {
        return estudianteRepository.existsByCedula(cedula);
    }
    
    public List<Estudiante> getRanking() {
    	// Excluir estudiantes anulados del ranking
        List<Estudiante> allEstudiantes = estudianteRepository.findAllByOrderByPuntajeDesc();
        return allEstudiantes.stream()
                .filter(e -> e.getAnulado() == null || !e.getAnulado())
                .collect(Collectors.toList());
        
         
        
    }
    
    public Map<String, Double> getPromediosNacionales() {
        List<Estudiante> estudiantes = estudianteRepository.findAllPuntajes();
        Map<String, Double> promedios = new HashMap<>();
        
        // Inicializar contadores y sumas
        int countPuntaje = 0, countCE = 0, countRC = 0, countLC = 0, countCC = 0, 
            countEN = 0, countFPI = 0, countPCME = 0, countDS = 0;
        double sumPuntaje = 0, sumCE = 0, sumRC = 0, sumLC = 0, sumCC = 0, 
               sumEN = 0, sumFPI = 0, sumPCME = 0, sumDS = 0;
        
        // Calcular sumas
        for (Estudiante e : estudiantes) {
            if (e.getPuntaje() != null) {
                sumPuntaje += e.getPuntaje();
                countPuntaje++;
            }
            if (e.getCE() != null) {
                sumCE += e.getCE();
                countCE++;
            }
            if (e.getRC() != null) {
                sumRC += e.getRC();
                countRC++;
            }
            if (e.getLC() != null) {
                sumLC += e.getLC();
                countLC++;
            }
            if (e.getCC() != null) {
                sumCC += e.getCC();
                countCC++;
            }
            if (e.getEN() != null) {
                sumEN += e.getEN();
                countEN++;
            }
            if (e.getFPI() != null) {
                sumFPI += e.getFPI();
                countFPI++;
            }
            if (e.getPCME() != null) {
                sumPCME += e.getPCME();
                countPCME++;
            }
            if (e.getDS() != null) {
                sumDS += e.getDS();
                countDS++;
            }
        }
        
        // Calcular promedios
        promedios.put("puntaje", countPuntaje > 0 ? sumPuntaje / countPuntaje : 0);
        promedios.put("CE", countCE > 0 ? sumCE / countCE : 0);
        promedios.put("RC", countRC > 0 ? sumRC / countRC : 0);
        promedios.put("LC", countLC > 0 ? sumLC / countLC : 0);
        promedios.put("CC", countCC > 0 ? sumCC / countCC : 0);
        promedios.put("EN", countEN > 0 ? sumEN / countEN : 0);
        promedios.put("FPI", countFPI > 0 ? sumFPI / countFPI : 0);
        promedios.put("PCME", countPCME > 0 ? sumPCME / countPCME : 0);
        promedios.put("DS", countDS > 0 ? sumDS / countDS : 0);
        
        return promedios;
    }
    
    public boolean authenticate(String cedula, String password) {
        Optional<Estudiante> estudianteOpt = estudianteRepository.findByCedula(cedula);
        if (estudianteOpt.isPresent()) {
            Estudiante estudiante = estudianteOpt.get();
            return password.equals(estudiante.getPassword());
        }
        return false;
    }
}