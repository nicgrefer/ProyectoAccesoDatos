package com.gf.handlers;

import com.gf.models.DatoAmbiental;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file. Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler para operaciones con archivos CSV. 
 * Esta clase permite leer y escribir datos ambientales desde archivos CSV,
 * siguiendo el mismo patrón que JSONHandler y XMLHandler.
 */
public class CSVHandler {

    /**
     * Lee datos desde un archivo CSV. 
     * @param rutaArchivo Ruta completa del archivo CSV a leer
     * @return Lista de objetos DatoAmbiental leída desde el archivo
     * @throws IOException Si hay un error al leer el archivo
     */
    public static List<DatoAmbiental> leerCSV(String rutaArchivo) throws IOException {
        Path path = Paths.get(rutaArchivo);
        if (!Files.exists(path)) {
            return null;  // Si el archivo no existe, devolvemos null
        }

        // Si el archivo está vacío, devolvemos null
        try {
            if (Files.size(path) == 0L) return null;
        } catch (IOException e) {
            // Si no podemos comprobar el tamaño, intentamos leer el archivo igualmente
        }

        List<DatoAmbiental> datos = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .builder()
                     .setHeader("DATO 1", "DATO 2", "DATO 3", "DATO 4", "DATO 5", "DATO 6")
                     .setSkipHeaderRecord(true)
                     . build())) {

            for (CSVRecord record : csvParser) {
                DatoAmbiental dato = new DatoAmbiental(
                    record.get("DATO 1"),
                    record.get("DATO 2"),
                    record.get("DATO 3"),
                    record.get("DATO 4"),
                    record.get("DATO 5"),
                    record.get("DATO 6")
                );
                datos.add(dato);
            }
        } catch (Exception e) {
            throw new IOException("Error al leer el archivo CSV:  " + e.getMessage(), e);
        }

        return datos;
    }

    /**
     * Escribe una lista de objetos DatoAmbiental en un archivo CSV.
     * @param rutaArchivo Ruta completa del archivo CSV donde se escribirá la información
     * @param datos Lista de objetos DatoAmbiental a escribir en el archivo
     * @throws IOException Si hay un error al escribir en el archivo
     */
    public static void escribirCSV(String rutaArchivo, List<DatoAmbiental> datos) throws IOException {
        Path csvPath = Paths.get(rutaArchivo);
        
        try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .builder()
                     .setHeader("DATO 1", "DATO 2", "DATO 3", "DATO 4", "DATO 5", "DATO 6")
                     .build())) {

            for (DatoAmbiental dato : datos) {
                printer.printRecord(
                    dato.getDato1(),
                    dato.getDato2(),
                    dato.getDato3(),
                    dato.getDato4(),
                    dato. getDato5(),
                    dato.getDato6()
                );
            }
        } catch (Exception e) {
            throw new IOException("Error al escribir en el archivo CSV: " + e.getMessage(), e);
        }
    }

    /**
     * Agrega un único registro al archivo CSV (modo APPEND).
     * @param rutaArchivo Ruta del archivo CSV
     * @param dato Objeto DatoAmbiental a agregar
     * @throws IOException Si hay un error
     */
    public static void agregarRegistroCSV(String rutaArchivo, DatoAmbiental dato) throws IOException {
        Path csvPath = Paths.get(rutaArchivo);
        boolean exists = Files.exists(csvPath);

        CSVFormat csvFormat = CSVFormat.DEFAULT. builder()
                .setHeader("DATO 1", "DATO 2", "DATO 3", "DATO 4", "DATO 5", "DATO 6")
                .setSkipHeaderRecord(exists)  // Si existe, no escribir cabecera
                .build();

        try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8,
                StandardOpenOption. CREATE, StandardOpenOption.APPEND);
             CSVPrinter printer = new CSVPrinter(writer, csvFormat)) {

            printer.printRecord(
                dato.getDato1(),
                dato.getDato2(),
                dato.getDato3(),
                dato.getDato4(),
                dato.getDato5(),
                dato.getDato6()
            );
        } catch (IOException e) {
            throw new IOException("Error al agregar registro CSV: " + e.getMessage(), e);
        }
    }

    /**
     * Convierte una lista de String a un objeto DatoAmbiental. 
     * @param listaDatos Lista de Strings (con 6 elementos) que representan un DatoAmbiental
     * @return Objeto DatoAmbiental creado a partir de la lista de Strings
     * @throws IllegalArgumentException Si la lista no contiene exactamente 6 elementos
     */
    public static DatoAmbiental convertirListaADato(List<String> listaDatos) {
        if (listaDatos == null || listaDatos.size() != 6) {
            throw new IllegalArgumentException("La lista debe contener exactamente 6 elementos");
        }

        return new DatoAmbiental(
            listaDatos.get(0),  // dato1
            listaDatos.get(1),  // dato2
            listaDatos.get(2),  // dato3
            listaDatos.get(3),  // dato4
            listaDatos.get(4),  // dato5
            listaDatos.get(5)   // dato6
        );
    }

    /**
     * Convierte un objeto DatoAmbiental a una lista de Strings.
     * @param dato Objeto DatoAmbiental a convertir
     * @return Lista de Strings representando los valores del DatoAmbiental
     */
    public static List<String> convertirDatoALista(DatoAmbiental dato) {
        return List.of(
            dato. getDato1(),
            dato.getDato2(),
            dato.getDato3(),
            dato.getDato4(),
            dato.getDato5(),
            dato.getDato6()
        );
    }
}