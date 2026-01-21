package com.sede.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * POJO para la tabla registros
 * Representa los trámites registrados por los solicitantes
 */
@Entity
@Table(name = "registros")
public class Registros implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "num_registro", length = 50)
    private String numRegistro;
    
    @Column(name = "dni_solicitante", nullable = false, length = 10)
    private String dniSolicitante;
    
    @Column(name = "nombre_solicitante", nullable = false, length = 100)
    private String nombreSolicitante;
    
    @Column(name = "apellidos_solicitante", nullable = false, length = 150)
    private String apellidosSolicitante;
    
    @Column(name = "tramite", nullable = false, length = 200)
    private String tramite;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_entidad", nullable = false)
    private Entidad entidad;
    
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    
    // Constructores
    public Registros() {
        this.fechaRegistro = new Date();
    }
    
    public Registros(String numRegistro, String dniSolicitante, String nombreSolicitante, 
                     String apellidosSolicitante, String tramite, Entidad entidad) {
        this.numRegistro = numRegistro;
        this.dniSolicitante = dniSolicitante;
        this.nombreSolicitante = nombreSolicitante;
        this.apellidosSolicitante = apellidosSolicitante;
        this.tramite = tramite;
        this.entidad = entidad;
        this.fechaRegistro = new Date();
    }
    
    // Getters y Setters
    public String getNumRegistro() {
        return numRegistro;
    }
    
    public void setNumRegistro(String numRegistro) {
        this.numRegistro = numRegistro;
    }
    
    public String getDniSolicitante() {
        return dniSolicitante;
    }
    
    public void setDniSolicitante(String dniSolicitante) {
        this.dniSolicitante = dniSolicitante;
    }
    
    public String getNombreSolicitante() {
        return nombreSolicitante;
    }
    
    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }
    
    public String getApellidosSolicitante() {
        return apellidosSolicitante;
    }
    
    public void setApellidosSolicitante(String apellidosSolicitante) {
        this.apellidosSolicitante = apellidosSolicitante;
    }
    
    public String getTramite() {
        return tramite;
    }
    
    public void setTramite(String tramite) {
        this.tramite = tramite;
    }
    
    public Entidad getEntidad() {
        return entidad;
    }
    
    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    @Override
    public String toString() {
        return "Registros{" +
                "numRegistro='" + numRegistro + '\'' +
                ", dniSolicitante='" + dniSolicitante + '\'' +
                ", nombreSolicitante='" + nombreSolicitante + '\'' +
                ", apellidosSolicitante='" + apellidosSolicitante + '\'' +
                ", tramite='" + tramite + '\'' +
                ", entidad=" + (entidad != null ? entidad.getNombreEntidad() : "null") +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Registros registros = (Registros) o;
        
        return numRegistro != null ? numRegistro.equals(registros.numRegistro) : registros.numRegistro == null;
    }
    
    @Override
    public int hashCode() {
        return numRegistro != null ? numRegistro.hashCode() : 0;
    }
}
