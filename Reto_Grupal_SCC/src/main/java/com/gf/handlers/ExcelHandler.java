package com.gf.handlers;

import com.alibaba.excel.EasyExcel;
import com.gf.models.DatoAmbiental;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Handler para operaciones con archivos Excel (XLSX).
 * Esta clase permite leer y escribir datos ambientales desde un archivo Excel,
 * y realizar conversiones entre listas de String y objetos DatoAmbiental.
 */
public class ExcelHandler {

    /**
     * Lee datos desde un archivo Excel (XLSX).
     * Utiliza Apache POI para leer el archivo y convertirlo en una lista de objetos DatoAmbiental.
     * @param rutaArchivo Ruta completa del archivo Excel a leer
     * @return Lista de objetos DatoAmbiental leída desde el archivo
     * @throws IOException Si hay un error al leer el archivo
     */
    public static List<DatoAmbiental> leerExcel(String rutaArchivo) throws IOException {
        Path path = Paths.get(rutaArchivo);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        if (Files.size(path) == 0L) {
            return new ArrayList<>();
        }

        List<DatoAmbiental> resultado = new ArrayList<>();
        
        try (InputStream fis = Files.newInputStream(path);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                
                boolean isFirstRow = true;
                for (Row row : sheet) {
                    if (isFirstRow) {
                        isFirstRow = false;
                        continue;
                    }
                    
                    DatoAmbiental dato = new DatoAmbiental();
                    int cellIndex = 0;
                    for (Cell cell : row) {
                        String valor = getCellValue(cell);
                        
                        switch (cellIndex) {
                            case 0: dato.setDato1(valor); break;
                            case 1: dato.setDato2(valor); break;
                            case 2: dato.setDato3(valor); break;
                            case 3: dato.setDato4(valor); break;
                            case 4: dato.setDato5(valor); break;
                            case 5: dato.setDato6(valor); break;
                        }
                        cellIndex++;
                    }
                    resultado.add(dato);
                }
            }
            
        } catch (Exception e) {
            throw new IOException("Error al leer el archivo Excel: " + e.getMessage(), e);
        }
        
        return resultado;
    }
    
    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
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
            EasyExcel.write(rutaArchivo)
                .head(createHeader())
                .sheet("Datos")
                .doWrite(datos);
        } catch (Exception e) {
            throw new IOException("Error al escribir en el archivo Excel: " + e.getMessage(), e);
        }
    }
    
    private static java.util.List<List<String>> createHeader() {
        java.util.List<List<String>> header = new java.util.ArrayList<>();
        header.add(java.util.Arrays.asList("DATO 1", "DATO 2", "DATO 3", "DATO 4", "DATO 5", "DATO 6"));
        return header;
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
