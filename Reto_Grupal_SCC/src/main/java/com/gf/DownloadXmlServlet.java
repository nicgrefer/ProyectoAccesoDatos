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


// para descargar el archivo XML generado en el servidor
@WebServlet("/DownloadXml")
public class DownloadXmlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filesRealPath = req.getServletContext().getRealPath("/files");
        Path filesDir = Paths.get(filesRealPath == null ? "files" : filesRealPath);
        Path file = filesDir.resolve("datos.xml");
        if (!Files.exists(file)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Archivo no encontrado: datos.xml");
            return;
        }

        resp.setContentType("application/xml;charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=datos.xml");

        Files.copy(file, resp.getOutputStream());
        resp.getOutputStream().flush();
    }
}
