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
import com.gf.models.DatoAmbiental;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/ServletFich")
@MultipartConfig
public class ServletFich extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletFich() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("ServletFich OK - use POST to enviarTratamiento");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pagina = "TratamientoFich.jsp";

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

        String formatoFichero = request.getParameter("formato");
        String leerEscribir = request.getParameter("accion");

        // Crear directorio de archivos
        String filesRealPath = request.getServletContext().getRealPath("/files");
        Path filesDir = Paths.get(filesRealPath == null ? "files" : filesRealPath);
        try {
            Files.createDirectories(filesDir);
        } catch (IOException ignored) {
        }

        if ("lectura".equalsIgnoreCase(leerEscribir)) {
            pagina = handleLectura(request, filesDir, formatoFichero);
        } else {
            pagina = handleEscritura(request, filesDir, formatoFichero);
        }

        // Asegurar que siempre hay una página a la que reenviar
        if (pagina == null || pagina.trim().isEmpty()) {
            pagina = "TratamientoFich.jsp";
        }
        
        request.getRequestDispatcher(pagina).forward(request, response);
    }

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
        } else if ("xml".equalsIgnoreCase(formato) || "rdf".equalsIgnoreCase(formato)) {
            request.setAttribute("error", "Formato '" + formato + "' no implementado para lectura.");
            return "TratamientoFich.jsp";
        }

        // Formatos no implementados para lectura
        request.setAttribute("error", "Lectura para formato '" + formato + "' no implementada.");
        return "TratamientoFich.jsp";
    }

    private String handleEscritura(HttpServletRequest request, Path filesDir, String formato) {
        if (formato == null) {
            request.setAttribute("error", "Seleccione un formato");
            return "TratamientoFich.jsp";
        }

        // Validar campos
        List<String> listaDatos = new ArrayList<>(6);
        for (int i = 1; i <= 6; i++) {
            String param = request.getParameter("dato" + i);
            if (param == null || param.trim().isEmpty()) {
                request.setAttribute("error", "(*) Los campos no pueden estar vacíos");
                return "TratamientoFich.jsp";
            }
            listaDatos.add(param);
        }

        if ("xml".equalsIgnoreCase(formato) || "rdf".equalsIgnoreCase(formato)) {
            request.setAttribute("error", "Formato '" + formato + "' no implementado para escritura.");
            return "TratamientoFich.jsp";
        }

        // Procesar datos
        procesarDatos(listaDatos, formato, filesDir);
        request.setAttribute("lista", listaDatos);
        request.setAttribute("formato", formato);
        return "AccesoDatosA.jsp";
    }

    private void procesarDatos(List<String> listaDatos, String formatoFichero, Path baseDir) {
        switch (formatoFichero == null ? "" : formatoFichero.toLowerCase()) {
            case "xls": {
                List<List<String>> excelData = new ArrayList<>();
                excelData.add(listaDatos);
                EasyExcel.write(baseDir.resolve("datos.xlsx").toString())
                        .sheet("Datos")
                        .doWrite(excelData);
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
            case "xml":
            case "rdf": {
                // Pendiente de implementación
                break;
            }
            default: {
                // Formato no soportado
                break;
            }
        }
    }
}