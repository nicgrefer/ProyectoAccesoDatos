package com.sede.ln;

import com.sede.dao.RegistrosDAO;
import com.sede.model.Entidad;
import com.sede.model.Registros;

import java.util.regex.Pattern;

/**
 * Lógica de Negocio para Registros
 * Contiene validaciones y procesamiento de registros de trámites
 */
public class RegistrosLN {
    
    private RegistrosDAO registrosDAO;
    
    // Patrón para validar DNI español: 8 dígitos + 1 letra
    private static final Pattern DNI_PATTERN = Pattern.compile("^[0-9]{8}[A-Za-z]$");
    
    public RegistrosLN() {
        this.registrosDAO = new RegistrosDAO();
    }
    
    /**
     * Valida los datos del registro
     * @param dni DNI del solicitante
     * @param nombre Nombre del solicitante
     * @param apellidos Apellidos del solicitante
     * @param tramite Descripción del trámite
     * @param idEntidad ID de la entidad
     * @return true si los datos son válidos, false en caso contrario
     */
    public boolean validarDatos(String dni, String nombre, String apellidos, String tramite, int idEntidad) {
        // Validar que no sean nulos ni vacíos
        if (dni == null || dni.trim().isEmpty()) {
            System.out.println("Validación fallida: DNI vacío");
            return false;
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Validación fallida: Nombre vacío");
            return false;
        }
        
        if (apellidos == null || apellidos.trim().isEmpty()) {
            System.out.println("Validación fallida: Apellidos vacíos");
            return false;
        }
        
        if (tramite == null || tramite.trim().isEmpty()) {
            System.out.println("Validación fallida: Trámite vacío");
            return false;
        }
        
        if (idEntidad <= 0) {
            System.out.println("Validación fallida: ID de entidad inválido");
            return false;
        }
        
        // Validar formato de DNI español
        if (!DNI_PATTERN.matcher(dni.trim()).matches()) {
            System.out.println("Validación fallida: Formato de DNI incorrecto");
            return false;
        }
        
        // Validar longitudes máximas
        if (nombre.length() > 100) {
            System.out.println("Validación fallida: Nombre demasiado largo");
            return false;
        }
        
        if (apellidos.length() > 150) {
            System.out.println("Validación fallida: Apellidos demasiado largos");
            return false;
        }
        
        if (tramite.length() > 200) {
            System.out.println("Validación fallida: Trámite demasiado largo");
            return false;
        }
        
        System.out.println("Validación exitosa");
        return true;
    }
    
    /**
     * Genera un número de registro único en formato REG_XXXXXX
     * @return Número de registro generado
     */
    public String generarNumeroRegistro() {
        long contador = registrosDAO.obtenerContador();
        contador++; // Incrementar para el nuevo registro
        
        // Generar número de registro con formato REG_000001
        String numRegistro = String.format("REG_%06d", contador);
        
        System.out.println("Número de registro generado: " + numRegistro);
        return numRegistro;
    }
    
    /**
     * Procesa y guarda un nuevo registro
     * @param registro Objeto Registros a procesar
     * @return Número de registro generado
     */
    public String procesarRegistro(Registros registro) {
        // Generar número de registro
        String numRegistro = generarNumeroRegistro();
        registro.setNumRegistro(numRegistro);
        
        // Guardar en la base de datos
        String resultado = registrosDAO.guardarRegistro(registro);
        
        System.out.println("Registro procesado: " + resultado);
        return resultado;
    }
    
    /**
     * Valida el formato del DNI español
     * @param dni DNI a validar
     * @return true si el formato es válido
     */
    public boolean validarFormatoDNI(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return false;
        }
        return DNI_PATTERN.matcher(dni.trim()).matches();
    }
}
