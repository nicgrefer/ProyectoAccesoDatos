package com.gf;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.alibaba.excel.EasyExcel;
import com.gf.handlers.JSONHandler;
import com.gf.handlers.XMLHandler;
import com.gf.models.DatoAmbiental;
import com.gf.handlers.CSVHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet encargado de gestionar la carga y descarga de archivos en diferentes formatos.
 * Los formatos soportados son CSV, JSON, XML y XLS (Excel).
 * Permite tanto la lectura de archivos como la escritura de nuevos archivos.
 */
@WebServlet("/ServletFich")
@MultipartConfig
public class ServletFich extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor por defecto del ServletFich.
     */
    public ServletFich() {
        super();
    }

    /**
     * Método que maneja las peticiones GET. En este caso, solo devuelve un mensaje de texto indicando que se debe usar POST.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error al procesar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("ServletFich OK - use POST to enviarTratamiento");
    }

    /**
     * Método que maneja las peticiones POST. Dependiendo de la acción solicitada (lectura o escritura),
     * dirige el flujo a los métodos correspondientes.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error al procesar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pagina = "TratamientoFich.jsp";  // Página de retorno por defecto

        // Obtiene el parámetro 'boton' que determina qué acción se realizará
        String boton = request.getParameter("boton");
        if (boton == null) {
            request.setAttribute("error", "Parámetros incompletos");
            request.getRequestDispatcher(pagina).forward(request, response);
            return;
        }

        if ("volverTratamiento".equals(boton)) {
            request.getRequestDispatcher(pagina).forward(request, response);
            return;
        }

        if (!"enviarTratamiento".equals(boton)) {
            request.setAttribute("error", "Acción desconocida");
            request.getRequestDispatcher(pagina).forward(request, response);
            return;
        }

        // Obtiene el formato y la acción (lectura o escritura)
        String formatoFichero = request.getParameter("formato");
        String leerEscribir = request.getParameter("accion");

        // Crea el directorio de archivos si no existe
        String filesRealPath = request.getServletContext().getRealPath("/files");
        Path filesDir = Paths.get(filesRealPath == null ? "files" : filesRealPath);
        try {
            Files.createDirectories(filesDir);
        } catch (IOException ignored) {
        }

        // Determina si la acción es de lectura o escritura
        if ("lectura".equalsIgnoreCase(leerEscribir)) {
            pagina = handleLectura(request, filesDir, formatoFichero);
        } else {
            pagina = handleEscritura(request, filesDir, formatoFichero);
        }

        // Asegura que siempre haya una página a la que redirigir
        if (pagina == null || pagina.trim().isEmpty()) {
            pagina = "TratamientoFich.jsp";
        }
        
        request.getRequestDispatcher(pagina).forward(request, response);
    }

    /**
     * Maneja la lectura de archivos. Dependiendo del formato, procesa el archivo
     * y carga los datos en el atributo 'registros'.
     *
     * @param request La solicitud HTTP.
     * @param filesDir El directorio donde se almacenan los archivos.
     * @param formato El formato del archivo a leer (JSON, XML, RDF).
     * @return La página a la que redirigir después de procesar la lectura.
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws ServletException Si ocurre un error de servlet.
     */
    private String handleLectura(HttpServletRequest request, Path filesDir, String formato) 
            throws IOException, ServletException {
        if (formato == null) {
            request.setAttribute("error", "Seleccione un formato");
            return "TratamientoFich.jsp";
        }

        if ("json".equalsIgnoreCase(formato)) {
            Part uploadPart = request.getPart("uploadFile");
            if (uploadPart == null || uploadPart.getSize() == 0) {
                request.setAttribute("error", "No se ha seleccionado ningún archivo JSON para cargar.");
                return "TratamientoFich.jsp";
            }

            Path target = filesDir.resolve("datos.json");
            try (java.io.InputStream in = uploadPart.getInputStream()) {
                Files.copy(in, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            try {
                List<DatoAmbiental> registros = JSONHandler.leerJSON(target.toString());
                request.setAttribute("registros", registros);
                return "MostrarJSON.jsp";
            } catch (IOException e) {
                e.printStackTrace();
                request.setAttribute("error", "Error leyendo JSON: " + e.getMessage());
                return "TratamientoFich.jsp";
            }
        } else if ("xml".equalsIgnoreCase(formato)) {
            Part uploadPart = request.getPart("uploadFile");
            if (uploadPart == null || uploadPart.getSize() == 0) {
                request.setAttribute("error", "No se ha seleccionado ningún archivo XML para cargar.");
                return "TratamientoFich.jsp";
            }

            Path target = filesDir.resolve("datos.xml");
            try (java.io.InputStream in = uploadPart.getInputStream()) {
                Files.copy(in, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            try {
                List<DatoAmbiental> registros = XMLHandler.leerXML(target.toString());
                request.setAttribute("registros", registros);
                return "MostrarXML.jsp";
            } catch (IOException e) {
                e.printStackTrace();
                request.setAttribute("error", "Error leyendo XML: " + e.getMessage());
                return "TratamientoFich.jsp";
            }
        } else if ("csv".equalsIgnoreCase(formato)) {
            Part uploadPart = request.getPart("uploadFile");
            if (uploadPart == null || uploadPart.getSize() == 0) {
                request.setAttribute("error", "No se ha seleccionado ningún archivo CSV para cargar.");
                return "TratamientoFich.jsp";
            }

            Path target = filesDir.resolve("datos.csv");
            try (java.io. InputStream in = uploadPart.getInputStream()) {
                Files.copy(in, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            try {
                List<DatoAmbiental> registros = CSVHandler.leerCSV(target. toString());
                request.setAttribute("registros", registros);
                return "MostrarCSV.jsp";
            } catch (IOException e) {
                e.printStackTrace();
                request.setAttribute("error", "Error leyendo CSV: " + e.getMessage());
                return "TratamientoFich.jsp";
            }
        } else if ("rdf".equalsIgnoreCase(formato)) {
            request.setAttribute("error", "Formato 'RDF' no implementado para lectura.");
            return "TratamientoFich.jsp";
        }

        // Si el formato no es válido o no está implementado
        request.setAttribute("error", "Lectura para formato '" + formato + "' no implementada.");
        return "TratamientoFich.jsp";
    }

    /**
     * Maneja la escritura de datos en archivos. Dependiendo del formato seleccionado,
     * los datos se escriben en el archivo correspondiente (CSV, JSON, XML, XLS).
     *
     * @param request La solicitud HTTP.
     * @param filesDir El directorio donde se guardarán los archivos.
     * @param formato El formato de archivo para escribir (CSV, JSON, XML, XLS).
     * @return La página a la que redirigir después de procesar la escritura.
     */
    private String handleEscritura(HttpServletRequest request, Path filesDir, String formato) {
        if (formato == null) {
            request.setAttribute("error", "Seleccione un formato");
            return "TratamientoFich.jsp";
        }

        // Validación de los campos de entrada
        List<String> listaDatos = new ArrayList<>(6);
        for (int i = 1; i <= 6; i++) {
            String param = request.getParameter("dato" + i);
            if (param == null || param.trim().isEmpty()) {
                request.setAttribute("error", "(*) Los campos no pueden estar vacíos");
                return "TratamientoFich.jsp";
            }
            listaDatos.add(param);
        }

        if ("rdf".equalsIgnoreCase(formato)) {
            request.setAttribute("error", "Formato 'RDF' no implementado para escritura.");
            return "TratamientoFich.jsp";
        }

        // Procesar los datos y escribir en el archivo correspondiente
        procesarDatos(listaDatos, formato, filesDir);
        request.setAttribute("lista", listaDatos);
        request.setAttribute("formato", formato);
        return "AccesoDatosA.jsp";
    }

    /**
     * Procesa los datos y los escribe en el archivo correspondiente según el formato seleccionado.
     * Los formatos soportados son XLS (Excel), CSV, JSON y XML.
     *
     * @param listaDatos Los datos a escribir en el archivo.
     * @param formatoFichero El formato de archivo para guardar (XLS, CSV, JSON, XML).
     * @param baseDir El directorio donde se guardarán los archivos.
     */
    private void procesarDatos(List<String> listaDatos, String formatoFichero, Path baseDir) {
        switch (formatoFichero == null ? "" : formatoFichero.toLowerCase()) {
            case "xls": {
                // Preparar datos para Excel
                List<List<String>> excelData = new ArrayList<>();
                excelData.add(listaDatos);

                // Escribir en Excel usando EasyExcel
                try {
                    EasyExcel.write(baseDir.resolve("datos.xlsx").toString())
                            .sheet("Datos")
                            .doWrite(excelData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "csv": {
                Path csvPath = baseDir.resolve("datos.csv");
                boolean exists = Files.exists(csvPath);
                CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                        .setHeader("DATO 1", "DATO 2", "DATO 3", "DATO 4", "DATO 5", "DATO 6")
                        .setSkipHeaderRecord(exists)
                        .build();

                try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                     CSVPrinter printer = new CSVPrinter(writer, csvFormat)) {
                    printer.printRecord(listaDatos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "json": {
                try {
                    DatoAmbiental dato = JSONHandler.convertirListaADato(listaDatos);
                    JSONHandler.agregarRegistroJSON(baseDir.resolve("datos.json").toString(), dato);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "xml": {
                try {
                    DatoAmbiental dato = XMLHandler.convertirListaADato(listaDatos);
                    XMLHandler.agregarRegistroXML(baseDir.resolve("datos.xml").toString(), dato);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "rdf": {
                // Implementación pendiente para RDF
                break;
            }
            default: {
                // Formato no soportado
                break;
            }
        }
    }
}
