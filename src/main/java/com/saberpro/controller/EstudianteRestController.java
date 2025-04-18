package com.saberpro.controller;

import com.saberpro.model.Estudiante;
import com.saberpro.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteRestController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @GetMapping
    public ResponseEntity<List<Estudiante>> getAllEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.findAll();
        return new ResponseEntity<>(estudiantes, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable String id) {
        Optional<Estudiante> estudiante = estudianteService.findById(id);
        return estudiante.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<Estudiante> getEstudianteByCedula(@PathVariable String cedula) {
        Optional<Estudiante> estudiante = estudianteService.findByCedula(cedula);
        return estudiante.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Estudiante> createEstudiante(@RequestBody Estudiante estudiante) {
        if (estudianteService.existsByCedula(estudiante.getCedula())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Estudiante savedEstudiante = estudianteService.save(estudiante);
        return new ResponseEntity<>(savedEstudiante, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> updateEstudiante(@PathVariable String id, @RequestBody Estudiante estudiante) {
        Optional<Estudiante> existingEstudiante = estudianteService.findById(id);
        if (existingEstudiante.isPresent()) {
            estudiante.setId(id);
            Estudiante updatedEstudiante = estudianteService.save(estudiante);
            return new ResponseEntity<>(updatedEstudiante, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable String id) {
        Optional<Estudiante> existingEstudiante = estudianteService.findById(id);
        if (existingEstudiante.isPresent()) {
            estudianteService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/cedula/{cedula}")
    public ResponseEntity<Void> deleteEstudianteByCedula(@PathVariable String cedula) {
        Optional<Estudiante> existingEstudiante = estudianteService.findByCedula(cedula);
        if (existingEstudiante.isPresent()) {
            estudianteService.deleteByCedula(cedula);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/ranking")
    public ResponseEntity<List<Estudiante>> getRanking() {
        List<Estudiante> ranking = estudianteService.getRanking();
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }
    
    @GetMapping("/promedios")
    public ResponseEntity<Map<String, Double>> getPromediosNacionales() {
        Map<String, Double> promedios = estudianteService.getPromediosNacionales();
        return new ResponseEntity<>(promedios, HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Estudiante> login(@RequestBody Map<String, String> credentials) {
        String cedula = credentials.get("cedula");
        String password = credentials.get("password");
        
        if (estudianteService.authenticate(cedula, password)) {
            Optional<Estudiante> estudiante = estudianteService.findByCedula(cedula);
            return estudiante.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}