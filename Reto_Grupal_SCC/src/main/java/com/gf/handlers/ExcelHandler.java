package com.gf.handlers;

import com.alibaba.excel.EasyExcel;
import com.gf.models.DatoAmbiental;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler para operaciones con archivos Excel (XLSX).
 * Esta clase permite leer y escribir datos ambientales desde un archivo Excel,
 * y realizar conversiones entre listas de String y objetos DatoAmbiental.
 */
public class ExcelHandler {

    /**
     * Lee datos desde un archivo Excel (XLSX).
     * Utiliza EasyExcel para leer el archivo y convertirlo en una lista de objetos DatoAmbiental.
     * @param rutaArchivo Ruta completa del archivo Excel a leer
     * @return Lista de objetos DatoAmbiental leída desde el archivo
     * @throws IOException Si hay un error al leer el archivo
     */
    public static List<DatoAmbiental> leerExcel(String rutaArchivo) throws IOException {
        Path path = Paths.get(rutaArchivo);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try {
            if (Files.size(path) == 0L) return new ArrayList<>();
        } catch (IOException e) {
        }

        try {
            DatoAmbientalListener listener = new DatoAmbientalListener();
            EasyExcel.read(rutaArchivo, DatoAmbiental.class, listener).sheet().doReadSync();
            return listener.getDatos();
        } catch (Exception e) {
            throw new IOException("Error al leer el archivo Excel: " + e.getMessage(), e);
        }
    }

    /**
     * Escribe una lista de objetos DatoAmbiental en un archivo Excel (XLSX).
     * Utiliza EasyExcel para escribir los datos en el archivo especificado.
     * @param rutaArchivo Ruta completa del archivo Excel donde se escribirá la información
     * @param datos Lista de objetos DatoAmbiental a escribir en el archivo
     * @throws IOException Si hay un error al escribir en el archivo
     */
    public static void escribirExcel(String rutaArchivo, List<DatoAmbiental> datos) throws IOException {
        try {
            // Usamos EasyExcel para escribir los datos en el archivo Excel
            EasyExcel.write(rutaArchivo, DatoAmbiental.class).sheet("Datos Ambientales").doWrite(datos);
        } catch (Exception e) {
            throw new IOException("Error al escribir en el archivo Excel: " + e.getMessage(), e);
        }
    }

    /**
     * Convierte una lista de String a un objeto DatoAmbiental.
     * La lista debe contener exactamente 6 elementos, cada uno correspondiente a un dato ambiental.
     * @param listaDatos Lista de Strings (con 6 elementos) que representan un DatoAmbiental
     * @return Objeto DatoAmbiental creado a partir de la lista de Strings
     * @throws IllegalArgumentException Si la lista no contiene exactamente 6 elementos
     */
    public static DatoAmbiental convertirListaADato(List<String> listaDatos) {
        if (listaDatos == null || listaDatos.size() != 6) {
            throw new IllegalArgumentException("La lista debe contener exactamente 6 elementos");
        }

        // Creamos un objeto DatoAmbiental usando los 6 datos de la lista
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
     * Cada propiedad del DatoAmbiental se convierte en un elemento de la lista.
     * @param dato Objeto DatoAmbiental a convertir
     * @return Lista de Strings representando los valores del DatoAmbiental
     */
    public static List<String> convertirDatoALista(DatoAmbiental dato) {
        return List.of(
                dato.getDato1(),  // dato1
                dato.getDato2(),  // dato2
                dato.getDato3(),  // dato3
                dato.getDato4(),  // dato4
                dato.getDato5(),  // dato5
                dato.getDato6()   // dato6
        );
    }
}
