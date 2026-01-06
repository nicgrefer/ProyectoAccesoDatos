package com.gf.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase modelo para almacenar datos ambientales
 */
public class DatoAmbiental implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String dato1;
    private String dato2;
    private String dato3;
    private String dato4;
    private String dato5;
    private String dato6;
    
    // Constructor vacío
    public DatoAmbiental() {}
    
    // Constructor con parámetros
    public DatoAmbiental(String dato1, String dato2, String dato3, 
                        String dato4, String dato5, String dato6) {
        this.dato1 = dato1;
        this.dato2 = dato2;
        this.dato3 = dato3;
        this.dato4 = dato4;
        this.dato5 = dato5;
        this.dato6 = dato6;
    }
    
    // Getters y Setters
    public String getDato1() {
        return dato1;
    }
    
    public void setDato1(String dato1) {
        this.dato1 = dato1;
    }
    
    public String getDato2() {
        return dato2;
    }
    
    public void setDato2(String dato2) {
        this.dato2 = dato2;
    }
    
    public String getDato3() {
        return dato3;
    }
    
    public void setDato3(String dato3) {
        this.dato3 = dato3;
    }
    
    public String getDato4() {
        return dato4;
    }
    
    public void setDato4(String dato4) {
        this.dato4 = dato4;
    }
    
    public String getDato5() {
        return dato5;
    }
    
    public void setDato5(String dato5) {
        this.dato5 = dato5;
    }
    
    public String getDato6() {
        return dato6;
    }
    
    public void setDato6(String dato6) {
        this.dato6 = dato6;
    }
    
    @Override
    public String toString() {
        return "DatoAmbiental{" +
                "dato1='" + dato1 + '\'' +
                ", dato2='" + dato2 + '\'' +
                ", dato3='" + dato3 + '\'' +
                ", dato4='" + dato4 + '\'' +
                ", dato5='" + dato5 + '\'' +
                ", dato6='" + dato6 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatoAmbiental that = (DatoAmbiental) o;
        return Objects.equals(dato1, that.dato1) &&
                Objects.equals(dato2, that.dato2) &&
                Objects.equals(dato3, that.dato3) &&
                Objects.equals(dato4, that.dato4) &&
                Objects.equals(dato5, that.dato5) &&
                Objects.equals(dato6, that.dato6);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dato1, dato2, dato3, dato4, dato5, dato6);
    }
}