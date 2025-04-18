package com.saberpro.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estudiantes")
public class Estudiante {
    @Id
    private String id;
    private String tipoDocumento;
    private String cedula;
    private String primerApellido;
    private String segundoApellido;
    private String primerNombre;
    private String segundoNombre;
    private String email;
    private String telefono;
    private String registro;
    private String password;

    // Puntajes y niveles
    private Integer puntaje;
    private String nivelPuntaje;
    private String estadoAprobacion;

    // Comunicación Escrita
    private Integer CE;
    private String lvlCE;

    // Razonamiento Cuantitativo
    private Integer RC;
    private String lvlRC;

    // Lectura Crítica
    private Integer LC;
    private String lvlLC;

    // Competencias Ciudadanas
    private Integer CC;
    private String lvlCC;

    // Inglés
    private Integer EN;
    private String lvlEN;
    private String nivelIngles;

    // Formulación de Proyectos de Ingeniería
    private Integer FPI;
    private String lvlFPI;

    // Pensamiento Científico y Matemáticas
    private Integer PCME;
    private String lvlPCME;

    // Diseño de Software
    private Integer DS;
    private String lvlDS;

    // Agregar este nuevo campo
    private Boolean anulado = false;

    // Constructors
    public Estudiante() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
        this.nivelPuntaje = calcularNivel(puntaje);
        this.estadoAprobacion = calcularEstadoAprobacion(puntaje);
    }

    public String getNivelPuntaje() {
        return nivelPuntaje;
    }

    public void setNivelPuntaje(String nivelPuntaje) {
        this.nivelPuntaje = nivelPuntaje;
    }

    public String getEstadoAprobacion() {
        return estadoAprobacion;
    }

    public void setEstadoAprobacion(String estadoAprobacion) {
        this.estadoAprobacion = estadoAprobacion;
    }

    public Integer getCE() {
        return CE;
    }

    public void setCE(Integer CE) {
        this.CE = CE;
        this.lvlCE = calcularNivel(CE);
    }

    public String getLvlCE() {
        return lvlCE;
    }

    public void setLvlCE(String lvlCE) {
        this.lvlCE = lvlCE;
    }

    public Integer getRC() {
        return RC;
    }

    public void setRC(Integer RC) {
        this.RC = RC;
        this.lvlRC = calcularNivel(RC);
    }

    public String getLvlRC() {
        return lvlRC;
    }

    public void setLvlRC(String lvlRC) {
        this.lvlRC = lvlRC;
    }

    public Integer getLC() {
        return LC;
    }

    public void setLC(Integer LC) {
        this.LC = LC;
        this.lvlLC = calcularNivel(LC);
    }

    public String getLvlLC() {
        return lvlLC;
    }

    public void setLvlLC(String lvlLC) {
        this.lvlLC = lvlLC;
    }

    public Integer getCC() {
        return CC;
    }

    public void setCC(Integer CC) {
        this.CC = CC;
        this.lvlCC = calcularNivel(CC);
    }

    public String getLvlCC() {
        return lvlCC;
    }

    public void setLvlCC(String lvlCC) {
        this.lvlCC = lvlCC;
    }

    public Integer getEN() {
        return EN;
    }

    public void setEN(Integer EN) {
        this.EN = EN;
        this.lvlEN = calcularNivel(EN);
    }

    public String getLvlEN() {
        return lvlEN;
    }

    public void setLvlEN(String lvlEN) {
        this.lvlEN = lvlEN;
    }

    public String getNivelIngles() {
        return nivelIngles;
    }

    public void setNivelIngles(String nivelIngles) {
        this.nivelIngles = nivelIngles;
    }

    public Integer getFPI() {
        return FPI;
    }

    public void setFPI(Integer FPI) {
        this.FPI = FPI;
        this.lvlFPI = calcularNivel(FPI);
    }

    public String getLvlFPI() {
        return lvlFPI;
    }

    public void setLvlFPI(String lvlFPI) {
        this.lvlFPI = lvlFPI;
    }

    public Integer getPCME() {
        return PCME;
    }

    public void setPCME(Integer PCME) {
        this.PCME = PCME;
        this.lvlPCME = calcularNivel(PCME);
    }

    public String getLvlPCME() {
        return lvlPCME;
    }

    public void setLvlPCME(String lvlPCME) {
        this.lvlPCME = lvlPCME;
    }

    public Integer getDS() {
        return DS;
    }

    public void setDS(Integer DS) {
        this.DS = DS;
        this.lvlDS = calcularNivel(DS);
    }

    public String getLvlDS() {
        return lvlDS;
    }

    public void setLvlDS(String lvlDS) {
        this.lvlDS = lvlDS;
    }

    // Getter y Setter para 'anulado'
    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    // Método para calcular el nivel
    private String calcularNivel(Integer puntaje) {
        if (puntaje == null) {
            return null;
        }

        if (puntaje >= 191 && puntaje <= 300) {
            return "Nivel 4";
        } else if (puntaje >= 156 && puntaje <= 190) {
            return "Nivel 3";
        } else if (puntaje >= 126 && puntaje <= 155) {
            return "Nivel 2";
        } else if (puntaje >= 0 && puntaje <= 125) {
            return "Nivel 1";
        } else {
            return "Error";
        }
    }

    // Método para calcular el estado de aprobación
    private String calcularEstadoAprobacion(Integer puntaje) {
        if (puntaje == null) {
            return null;
        }

        if (puntaje > 125) {
            return "Aprobado";
        } else {
            return "Reprobado";
        }
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id='" + id + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", cedula='" + cedula + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", primerNombre='" + primerNombre + '\'' +
                ", segundoNombre='" + segundoNombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", registro='" + registro + '\'' +
                ", puntaje=" + puntaje +
                ", nivelPuntaje='" + nivelPuntaje + '\'' +
                ", estadoAprobacion='" + estadoAprobacion + '\'' +
                ", anulado=" + anulado +
                '}';
    }
}
