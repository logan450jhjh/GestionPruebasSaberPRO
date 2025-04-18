package com.saberpro.controller;

import com.saberpro.model.Coordinador;
import com.saberpro.service.CoordinadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/coordinadores")
public class CoordinadorRestController {
    
    @Autowired
    private CoordinadorService coordinadorService;
    
    @GetMapping
    public ResponseEntity<List<Coordinador>> getAllCoordinadores() {
        List<Coordinador> coordinadores = coordinadorService.findAll();
        return new ResponseEntity<>(coordinadores, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Coordinador> getCoordinadorById(@PathVariable String id) {
        Optional<Coordinador> coordinador = coordinadorService.findById(id);
        return coordinador.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<Coordinador> getCoordinadorByCedula(@PathVariable String cedula) {
        Optional<Coordinador> coordinador = coordinadorService.findByCedula(cedula);
        return coordinador.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Coordinador> createCoordinador(@RequestBody Coordinador coordinador) {
        if (coordinadorService.existsByCedula(coordinador.getCedula())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Coordinador savedCoordinador = coordinadorService.save(coordinador);
        return new ResponseEntity<>(savedCoordinador, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Coordinador> updateCoordinador(@PathVariable String id, @RequestBody Coordinador coordinador) {
        Optional<Coordinador> existingCoordinador = coordinadorService.findById(id);
        if (existingCoordinador.isPresent()) {
            coordinador.setId(id);
            Coordinador updatedCoordinador = coordinadorService.save(coordinador);
            return new ResponseEntity<>(updatedCoordinador, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordinador(@PathVariable String id) {
        Optional<Coordinador> existingCoordinador = coordinadorService.findById(id);
        if (existingCoordinador.isPresent()) {
            coordinadorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Coordinador> login(@RequestBody Map<String, String> credentials) {
        String cedula = credentials.get("cedula");
        String password = credentials.get("password");
        
        if (coordinadorService.authenticate(cedula, password)) {
            Optional<Coordinador> coordinador = coordinadorService.findByCedula(cedula);
            return coordinador.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}