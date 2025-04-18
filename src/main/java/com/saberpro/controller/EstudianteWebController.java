package com.saberpro.controller;

import com.saberpro.model.Estudiante;
import com.saberpro.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/estudiante")
public class EstudianteWebController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "login-estudiante";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String cedula, @RequestParam String password, 
                        HttpSession session, RedirectAttributes redirectAttributes) {
        if (estudianteService.authenticate(cedula, password)) {
            Optional<Estudiante> estudianteOpt = estudianteService.findByCedula(cedula);
            if (estudianteOpt.isPresent()) {
                session.setAttribute("estudiante", estudianteOpt.get());
                return "redirect:/estudiante/inicio";
            }
        }
        redirectAttributes.addFlashAttribute("error", "Credenciales inválidas");
        return "redirect:/estudiante/login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/estudiante/login";
    }
    
    @GetMapping("/inicio")
    public String inicio(Model model, HttpSession session) {
        Estudiante estudiante = (Estudiante) session.getAttribute("estudiante");
        if (estudiante == null) {
            return "redirect:/estudiante/login";
        }
        
        // Refrescar datos del estudiante
        Optional<Estudiante> estudianteActualizado = estudianteService.findById(estudiante.getId());
        if (estudianteActualizado.isPresent()) {
            model.addAttribute("estudiante", estudianteActualizado.get());
        } else {
            model.addAttribute("estudiante", estudiante);
        }
        
        return "inicio-estudiante";
    }
    
    @GetMapping("/ranking")
    public String ranking(Model model, HttpSession session) {
        Estudiante estudiante = (Estudiante) session.getAttribute("estudiante");
        if (estudiante == null) {
            return "redirect:/estudiante/login";
        }
        
        List<Estudiante> ranking = estudianteService.getRanking();
        model.addAttribute("estudiantes", ranking);
        model.addAttribute("estudianteActual", estudiante);
        return "ranking-estudiantes";
    }
    
    @GetMapping("/promedios")
    public String promedios(Model model, HttpSession session) {
        if (session.getAttribute("estudiante") == null) {
            return "redirect:/estudiante/login";
        }
        
        Map<String, Double> promedios = estudianteService.getPromediosNacionales();
        model.addAttribute("promedios", promedios);
        return "promedio-asignaturas";
    }
}