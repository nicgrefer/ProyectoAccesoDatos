package com.gf.handlers;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
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
            System.out.println("ExcelHandler: El archivo no existe: " + rutaArchivo);
            return new ArrayList<>();
        }

        try {
            long size = Files.size(path);
            System.out.println("ExcelHandler: Tamaño del archivo: " + size);
            if (size == 0L) return new ArrayList<>();
        } catch (IOException e) {
        }

        try {
            List<DatoAmbiental> datos = EasyExcel.read(rutaArchivo)
                .head(DatoAmbiental.class)
                .sheet()
                .doReadSync();
            
            System.out.println("ExcelHandler: Datos leidos con doReadSync: " + datos.size());
            
            if (datos.isEmpty()) {
                System.out.println("ExcelHandler: Intentando leer como List<String>...");
                List<List<String>> datosString = EasyExcel.read(rutaArchivo)
                    .sheet()
                    .doReadSync();
                
                System.out.println("ExcelHandler: Datos String leidos: " + datosString.size());
                for (List<String> row : datosString) {
                    System.out.println("ExcelHandler: Fila: " + row);
                }
                
                datos = new ArrayList<>();
                for (List<String> row : datosString) {
                    DatoAmbiental dato = new DatoAmbiental();
                    dato.setDato1(row.size() > 0 ? row.get(0) : "");
                    dato.setDato2(row.size() > 1 ? row.get(1) : "");
                    dato.setDato3(row.size() > 2 ? row.get(2) : "");
                    dato.setDato4(row.size() > 3 ? row.get(3) : "");
                    dato.setDato5(row.size() > 4 ? row.get(4) : "");
                    dato.setDato6(row.size() > 5 ? row.get(5) : "");
                    datos.add(dato);
                }
            }
            
            for (DatoAmbiental d : datos) {
                System.out.println("ExcelHandler: " + d);
            }
            
            return datos;
        } catch (Exception e) {
            System.out.println("ExcelHandler: Error: " + e.getMessage());
            e.printStackTrace();
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
            EasyExcel.write(rutaArchivo, DatoAmbiental.class)
                .sheet("Datos")
                .doWrite(datos);
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
