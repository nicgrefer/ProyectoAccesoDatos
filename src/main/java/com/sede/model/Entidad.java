package com.sede.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO para la tabla entidad
 * Representa las diferentes entidades administrativas educativas
 */
@Entity
@Table(name = "entidad")
public class Entidad implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entidad")
    private Integer idEntidad;
    
    @Column(name = "nombre_entidad", nullable = false, length = 200)
    private String nombreEntidad;
    
    @OneToMany(mappedBy = "entidad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registros> registros = new ArrayList<>();
    
    // Constructores
    public Entidad() {
    }
    
    public Entidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }
    
    public Entidad(Integer idEntidad, String nombreEntidad) {
        this.idEntidad = idEntidad;
        this.nombreEntidad = nombreEntidad;
    }
    
    // Getters y Setters
    public Integer getIdEntidad() {
        return idEntidad;
    }
    
    public void setIdEntidad(Integer idEntidad) {
        this.idEntidad = idEntidad;
    }
    
    public String getNombreEntidad() {
        return nombreEntidad;
    }
    
    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }
    
    public List<Registros> getRegistros() {
        return registros;
    }
    
    public void setRegistros(List<Registros> registros) {
        this.registros = registros;
    }
    
    @Override
    public String toString() {
        return "Entidad{" +
                "idEntidad=" + idEntidad +
                ", nombreEntidad='" + nombreEntidad + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Entidad entidad = (Entidad) o;
        
        return idEntidad != null ? idEntidad.equals(entidad.idEntidad) : entidad.idEntidad == null;
    }
    
    @Override
    public int hashCode() {
        return idEntidad != null ? idEntidad.hashCode() : 0;
    }
}
