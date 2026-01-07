package com.gf;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet. http.HttpServletResponse;

import java.io.IOException;
import java.io. OutputStream;
import java.nio. file.Files;
import java. nio.file.Path;
import java.nio.file.Paths;

/**
 * Servlet para descargar el archivo CSV generado. 
 */
@WebServlet("/DownloadCsv")
public class DownloadCsvServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener ruta del archivo CSV
        String filesRealPath = request.getServletContext().getRealPath("/files");
        Path csvPath = Paths.get(filesRealPath, "datos.csv");

        // Verificar que el archivo existe
        if (!Files. exists(csvPath)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Archivo CSV no encontrado");
            return;
        }

        // Configurar la respuesta para descarga
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"datos.csv\"");
        response.setContentLengthLong(Files.size(csvPath));

        // Enviar el archivo
        try (OutputStream out = response.getOutputStream()) {
            Files.copy(csvPath, out);
            out.flush();
        }
    }
}