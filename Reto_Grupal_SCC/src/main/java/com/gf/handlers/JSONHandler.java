package com.gf.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gf.models.DatoAmbiental;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Handler para operaciones con archivos JSON
 */
public class JSONHandler {
    
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    
    /**
     * Lee datos desde un archivo JSON
     * @param rutaArchivo Ruta completa del archivo
     * @return Lista de DatoAmbiental
     * @throws IOException Si hay error al leer
     */
    public static List<DatoAmbiental> leerJSON(String rutaArchivo) throws IOException {
        Path path = Paths.get(rutaArchivo);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        // Si el archivo existe pero está vacío, devolver lista vacía
        try {
            if (Files.size(path) == 0L) return new ArrayList<>();
        } catch (IOException e) {
            // Si no podemos comprobar el tamaño, intentamos leer de todas formas
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            Type listType = new TypeToken<List<DatoAmbiental>>(){}.getType();
            try {
                List<DatoAmbiental> datos = gson.fromJson(reader, listType);
                return datos != null ? datos : new ArrayList<>();
            } catch (com.google.gson.JsonSyntaxException jse) {
                throw new IOException("Error al parsear JSON: formato inválido", jse);
            }
        }
    }
    
    /**
     * Escribe datos en un archivo JSON
     * @param rutaArchivo Ruta completa del archivo
     * @param datos Lista de datos a escribir
     * @throws IOException Si hay error al escribir
     */
    public static void escribirJSON(String rutaArchivo, List<DatoAmbiental> datos) throws IOException {
        Path path = Paths.get(rutaArchivo);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            gson.toJson(datos, writer);
        }
    }
    
    /**
     * Añade un nuevo registro al archivo JSON
     * @param rutaArchivo Ruta completa del archivo
     * @param nuevoDato Dato a añadir
     * @throws IOException Si hay error en la operación
     */
    public static void agregarRegistroJSON(String rutaArchivo, DatoAmbiental nuevoDato) throws IOException {
        // Leer datos existentes
        List<DatoAmbiental> datos = leerJSON(rutaArchivo);
        
        // Agregar nuevo dato
        datos.add(nuevoDato);
        
        // Escribir todos los datos
        escribirJSON(rutaArchivo, datos);
    }
    
    /**
     * Convierte una lista de String a un DatoAmbiental
     * @param listaDatos Lista con 6 elementos
     * @return DatoAmbiental creado
     */
    public static DatoAmbiental convertirListaADato(List<String> listaDatos) {
        if (listaDatos == null || listaDatos.size() != 6) {
            throw new IllegalArgumentException("La lista debe contener exactamente 6 elementos");
        }
        
        return new DatoAmbiental(
                listaDatos.get(0),
                listaDatos.get(1),
                listaDatos.get(2),
                listaDatos.get(3),
                listaDatos.get(4),
                listaDatos.get(5)
        );
    }
    
    /**
     * Convierte un DatoAmbiental a lista de String
     * @param dato DatoAmbiental
     * @return Lista con 6 elementos
     */
    public static List<String> convertirDatoALista(DatoAmbiental dato) {
        return new ArrayList<>(Arrays.asList(
                dato.getDato1(),
                dato.getDato2(),
                dato.getDato3(),
                dato.getDato4(),
                dato.getDato5(),
                dato.getDato6()
        ));
    }
}