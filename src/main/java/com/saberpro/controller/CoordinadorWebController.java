package com.saberpro.controller;

import com.saberpro.model.Coordinador;
import com.saberpro.model.Estudiante;
import com.saberpro.service.CoordinadorService;
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
@RequestMapping("/coordinador")
public class CoordinadorWebController {
    
    @Autowired
    private CoordinadorService coordinadorService;
    
    @Autowired
    private EstudianteService estudianteService;
    
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("coordinador", new Coordinador());
        return "login-coordinador";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String cedula, @RequestParam String password, 
                        HttpSession session, RedirectAttributes redirectAttributes) {
        if (coordinadorService.authenticate(cedula, password)) {
            Optional<Coordinador> coordinadorOpt = coordinadorService.findByCedula(cedula);
            if (coordinadorOpt.isPresent()) {
                session.setAttribute("coordinador", coordinadorOpt.get());
                return "redirect:/coordinador/inicio";
            }
        }
        redirectAttributes.addFlashAttribute("error", "Credenciales inválidas");
        return "redirect:/coordinador/login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/coordinador/login";
    }
    
    @GetMapping("/inicio")
    public String inicio(Model model, HttpSession session) {
        Coordinador coordinador = (Coordinador) session.getAttribute("coordinador");
        if (coordinador == null) {
            return "redirect:/coordinador/login";
        }
        
        List<Estudiante> estudiantes = estudianteService.findAll();
        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("coordinador", coordinador);
        return "inicio-coordinador";
    }
    
    @GetMapping("/registro")
    public String registroForm(Model model, HttpSession session) {
        if (session.getAttribute("coordinador") == null) {
            return "redirect:/coordinador/login";
        }
        model.addAttribute("coordinador", new Coordinador());
        return "registro-coordinador";
    }
    
    @PostMapping("/registro")
    public String registrarCoordinador(@ModelAttribute Coordinador coordinador, 
                                      RedirectAttributes redirectAttributes) {
        if (coordinadorService.existsByCedula(coordinador.getCedula())) {
            redirectAttributes.addFlashAttribute("error", "Ya existe un coordinador con esa cédula");
            return "redirect:/coordinador/registro";
        }
        
        coordinadorService.save(coordinador);
        redirectAttributes.addFlashAttribute("success", "Coordinador registrado exitosamente");
        return "redirect:/coordinador/inicio";
    }
    
    @GetMapping("/estudiante/registro")
    public String registroEstudianteForm(Model model, HttpSession session) {
        if (session.getAttribute("coordinador") == null) {
            return "redirect:/coordinador/login";
        }
        model.addAttribute("estudiante", new Estudiante());
        return "registro-estudiante";
    }
    
    @PostMapping("/estudiante/registro")
    public String registrarEstudiante(@ModelAttribute Estudiante estudiante, 
                                     RedirectAttributes redirectAttributes) {
        if (estudianteService.existsByCedula(estudiante.getCedula())) {
            redirectAttributes.addFlashAttribute("error", "Ya existe un estudiante con esa cédula");
            return "redirect:/coordinador/estudiante/registro";
        }
        
        estudianteService.save(estudiante);
        redirectAttributes.addFlashAttribute("success", "Estudiante registrado exitosamente");
        return "redirect:/coordinador/inicio";
    }
    
    @GetMapping("/estudiante/editar/{id}")
    public String editarEstudianteForm(@PathVariable String id, Model model, HttpSession session) {
        if (session.getAttribute("coordinador") == null) {
            return "redirect:/coordinador/login";
        }
        
        Optional<Estudiante> estudiante = estudianteService.findById(id);
        if (estudiante.isPresent()) {
            model.addAttribute("estudiante", estudiante.get());
            return "editar-estudiante";
        } else {
            return "redirect:/coordinador/inicio";
        }
    }
    
    @PostMapping("/estudiante/editar/{id}")
    public String editarEstudiante(@PathVariable String id, @ModelAttribute Estudiante estudiante, 
                                  RedirectAttributes redirectAttributes) {
        estudiante.setId(id);
        estudianteService.save(estudiante);
        redirectAttributes.addFlashAttribute("success", "Estudiante actualizado exitosamente");
        return "redirect:/coordinador/inicio";
    }
    
    @GetMapping("/estudiante/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable String id, RedirectAttributes redirectAttributes, 
                                    HttpSession session) {
        if (session.getAttribute("coordinador") == null) {
            return "redirect:/coordinador/login";
        }
        
        estudianteService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Estudiante eliminado exitosamente");
        return "redirect:/coordinador/inicio";
    }
    
    @GetMapping("/estudiante/detalle/{id}")
    public String detalleEstudiante(@PathVariable String id, Model model, HttpSession session) {
        if (session.getAttribute("coordinador") == null) {
            return "redirect:/coordinador/login";
        }
        
        Optional<Estudiante> estudiante = estudianteService.findById(id);
        if (estudiante.isPresent()) {
            model.addAttribute("estudiante", estudiante.get());
            return "detalle-estudiante";
        } else {
            return "redirect:/coordinador/inicio";
        }
    }
    
    @GetMapping("/ranking")
    public String ranking(Model model, HttpSession session) {
        if (session.getAttribute("coordinador") == null) {
            return "redirect:/coordinador/login";
        }
        
        List<Estudiante> ranking = estudianteService.getRanking();
        model.addAttribute("estudiantes", ranking);
        return "ranking-estudiantes";
    }
    
    @GetMapping("/promedios")
    public String promedios(Model model, HttpSession session) {
        if (session.getAttribute("coordinador") == null) {
            return "redirect:/coordinador/login";
        }
        
        Map<String, Double> promedios = estudianteService.getPromediosNacionales();
        model.addAttribute("promedios", promedios);
        return "promedio-asignaturas";
    }
}