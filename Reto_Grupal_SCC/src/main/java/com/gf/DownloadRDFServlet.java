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

@WebServlet("/DownloadRDF")
public class DownloadRDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filesRealPath = req.getServletContext().getRealPath("/files");
        Path file = Paths.get(filesRealPath == null ? "files" : filesRealPath).resolve("datos.ttl");

        if (!Files.exists(file)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Archivo no encontrado: datos.ttl");
            return;
        }

        // Tipo MIME para Turtle
        resp.setContentType("text/turtle");
        resp.setHeader("Content-Disposition", "attachment; filename=datos.ttl");
        
        Files.copy(file, resp.getOutputStream());
        resp.getOutputStream().flush();
    }
}