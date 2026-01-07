package com.gf.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase modelo que representa un dato ambiental.
 * Cada objeto de esta clase contiene 6 propiedades de tipo String que describen un dato ambiental específico.
 * Esta clase implementa la interfaz Serializable para permitir su serialización.
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
    /**
     * Constructor vacío por defecto. Este constructor se utiliza cuando se desea crear una instancia vacía
     * de la clase `DatoAmbiental` para luego establecer sus atributos mediante los setters.
     */
    public DatoAmbiental() {}
    
    // Constructor con parámetros
    /**
     * Constructor que permite crear una instancia de `DatoAmbiental` inicializando todos los atributos.
     * 
     * @param dato1 Primer dato ambiental (descripción o valor del dato 1)
     * @param dato2 Segundo dato ambiental (descripción o valor del dato 2)
     * @param dato3 Tercer dato ambiental (descripción o valor del dato 3)
     * @param dato4 Cuarto dato ambiental (descripción o valor del dato 4)
     * @param dato5 Quinto dato ambiental (descripción o valor del dato 5)
     * @param dato6 Sexto dato ambiental (descripción o valor del dato 6)
     */
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
    
    /**
     * Obtiene el primer dato ambiental.
     * 
     * @return El primer dato ambiental
     */
    public String getDato1() {
        return dato1;
    }
    
    /**
     * Establece el primer dato ambiental.
     * 
     * @param dato1 El primer dato ambiental a establecer
     */
    public void setDato1(String dato1) {
        this.dato1 = dato1;
    }
    
    /**
     * Obtiene el segundo dato ambiental.
     * 
     * @return El segundo dato ambiental
     */
    public String getDato2() {
        return dato2;
    }
    
    /**
     * Establece el segundo dato ambiental.
     * 
     * @param dato2 El segundo dato ambiental a establecer
     */
    public void setDato2(String dato2) {
        this.dato2 = dato2;
    }
    
    /**
     * Obtiene el tercer dato ambiental.
     * 
     * @return El tercer dato ambiental
     */
    public String getDato3() {
        return dato3;
    }
    
    /**
     * Establece el tercer dato ambiental.
     * 
     * @param dato3 El tercer dato ambiental a establecer
     */
    public void setDato3(String dato3) {
        this.dato3 = dato3;
    }
    
    /**
     * Obtiene el cuarto dato ambiental.
     * 
     * @return El cuarto dato ambiental
     */
    public String getDato4() {
        return dato4;
    }
    
    /**
     * Establece el cuarto dato ambiental.
     * 
     * @param dato4 El cuarto dato ambiental a establecer
     */
    public void setDato4(String dato4) {
        this.dato4 = dato4;
    }
    
    /**
     * Obtiene el quinto dato ambiental.
     * 
     * @return El quinto dato ambiental
     */
    public String getDato5() {
        return dato5;
    }
    
    /**
     * Establece el quinto dato ambiental.
     * 
     * @param dato5 El quinto dato ambiental a establecer
     */
    public void setDato5(String dato5) {
        this.dato5 = dato5;
    }
    
    /**
     * Obtiene el sexto dato ambiental.
     * 
     * @return El sexto dato ambiental
     */
    public String getDato6() {
        return dato6;
    }
    
    /**
     * Establece el sexto dato ambiental.
     * 
     * @param dato6 El sexto dato ambiental a establecer
     */
    public void setDato6(String dato6) {
        this.dato6 = dato6;
    }

    // Métodos sobrescritos para comparación y generación de cadenas de texto
    
    /**
     * Devuelve una representación en formato String del objeto `DatoAmbiental`.
     * La cadena de texto contiene los valores de todos los atributos de la clase.
     * 
     * @return Representación en formato String del objeto `DatoAmbiental`
     */
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

    /**
     * Compara si dos objetos `DatoAmbiental` son iguales. Dos objetos son considerados iguales
     * si tienen los mismos valores en todos sus atributos (dato1, dato2, dato3, dato4, dato5, dato6).
     * 
     * @param o Objeto con el que se va a comparar
     * @return true si los objetos son iguales, false en caso contrario
     */
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

    /**
     * Genera un valor hash para el objeto `DatoAmbiental`. Este valor es utilizado por estructuras
     * de datos como HashMap o HashSet para almacenar y comparar objetos.
     * 
     * @return Valor hash calculado para el objeto `DatoAmbiental`
     */
    @Override
    public int hashCode() {
        return Objects.hash(dato1, dato2, dato3, dato4, dato5, dato6);
    }
}
