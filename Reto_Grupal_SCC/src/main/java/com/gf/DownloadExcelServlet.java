package com.gf;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Servlet que maneja la descarga del archivo Excel generado en el servidor.
 * Este servlet busca un archivo llamado "datos.xlsx" en el directorio de archivos
 * y lo envía como una respuesta de descarga al cliente.
 * Si el archivo no existe, devuelve un error 404 (No encontrado).
 */
@WebServlet("/DownloadExcel")
public class DownloadExcelServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Método que maneja las peticiones GET. Este método permite al cliente descargar
     * el archivo Excel "datos.xlsx" almacenado en el servidor.
     *
     * @param req La solicitud HTTP.
     * @param resp La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el procesamiento del servlet.
     * @throws IOException Si ocurre un error en la entrada/salida de datos.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Ruta del directorio de archivos donde se encuentra el archivo Excel
        String filesRealPath = req.getServletContext().getRealPath("/files");
        Path filesDir = Paths.get(filesRealPath == null ? "files" : filesRealPath);
        
        // Ruta completa al archivo Excel (datos.xlsx)
        Path file = filesDir.resolve("datos.xlsx");
        
        // Verificar si el archivo existe en el sistema de archivos
        if (!Files.exists(file)) {
            // Si el archivo no existe, se devuelve un error 404
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Archivo no encontrado: datos.xlsx");
            return;
        }

        // Configurar la respuesta HTTP para indicar que se trata de un archivo Excel
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        
        // Configurar el encabezado de la respuesta para forzar la descarga del archivo
        resp.setHeader("Content-Disposition", "attachment; filename=datos.xlsx");

        // Enviar el archivo Excel como respuesta al cliente
        Files.copy(file, resp.getOutputStream());

        // Asegurar que los datos se envíen correctamente
        resp.getOutputStream().flush();
    }
}
