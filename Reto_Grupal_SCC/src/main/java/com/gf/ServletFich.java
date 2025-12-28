package com.gf;

import com.gf.handlers.JSONHandler;
import com.gf.models.DatoAmbiental;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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

/**
 * Servlet implementation class ServletFich
 */
@WebServlet("/ServletFich")
public class ServletFich extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Path PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "resources");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletFich() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pagina = "";
		String mensaje = "";
		
		switch (request.getParameter("boton")) {
		case "enviarTratamiento":
			
			// opciones del formulario
			String formatoFichero = request.getParameter("formato");
			String leerEscribir = request.getParameter("accion");
			
			// datos del formulario
			List<String> listaDatos = new ArrayList<String>(6);
			boolean hasError = false;
			for (int i = 1; i <= 6; i++) {
				String dato = "dato"+i;
				
				String param = request.getParameter(dato);
				
				if (param == null || param.trim().isEmpty()) {
					mensaje = "(*) Los campos no pueden estar vacíos";
					
					request.setAttribute("error", mensaje);
					hasError = true;
					
					break;
				} else listaDatos.add(param);
			}
			
			if (hasError) pagina = "TratamientoFich.jsp";
			else {
				// Usar carpeta dentro de la webapp para almacenar archivos en tiempo de ejecución
				String filesRealPath = request.getServletContext().getRealPath("/files");
				Path filesDir = Paths.get(filesRealPath == null ? "files" : filesRealPath);
				try {
					Files.createDirectories(filesDir);
				} catch (IOException e) {
					e.printStackTrace();
				}

				if ("lectura".equalsIgnoreCase(leerEscribir)) {
					if ("json".equalsIgnoreCase(formatoFichero)) {
						try {
							List<DatoAmbiental> registros = JSONHandler.leerJSON(filesDir.resolve("datos.json").toString());
							request.setAttribute("registros", registros);
							pagina = "MostrarJSON.jsp";
						} catch (IOException e) {
							e.printStackTrace();
							request.setAttribute("error", "Error leyendo JSON: "+e.getMessage());
							pagina = "AccesoDatosA.jsp";
						}
					} else if ("xml".equalsIgnoreCase(formatoFichero) || "rdf".equalsIgnoreCase(formatoFichero)) {
						// Formatos no implementados para lectura
						request.setAttribute("error", "Formato '" + formatoFichero + "' no implementado para lectura.");
						pagina = "TratamientoFich.jsp";
					} else {
						// Otros formatos (por ahora mostramos la lista enviada)
						request.setAttribute("lista", listaDatos);
						request.setAttribute("formato", formatoFichero);
						pagina = "AccesoDatosA.jsp";
					}
				} else {
					// Escritura: validar si el formato está implementado
					if ("xml".equalsIgnoreCase(formatoFichero) || "rdf".equalsIgnoreCase(formatoFichero)) {
						request.setAttribute("error", "Formato '" + formatoFichero + "' no implementado para escritura.");
						pagina = "TratamientoFich.jsp";
					} else {
						this.procesarDatos(listaDatos, formatoFichero, filesDir);
						request.setAttribute("lista", listaDatos);
						request.setAttribute("formato", formatoFichero);
						pagina = "AccesoDatosA.jsp";
					}
				}
			}
			
			break;
		
		case "volverTratamiento":
			
			pagina = "TratamientoFich.jsp";
			break;
		}
		
		request.getRequestDispatcher(pagina).forward(request, response);
		
	}

	private void procesarDatos(List<String> listaDatos, String formatoFichero, Path baseDir) {
		switch (formatoFichero) {
			case "xls": {
				try {
					Files.createDirectories(baseDir);
				} catch (IOException ignored) {}
				List<List<String>> excelData = new ArrayList<>();
				excelData.add(listaDatos);
				EasyExcel.write(baseDir.resolve("datos.xlsx").toString())
						.sheet("Datos")
						.doWrite(excelData);
				break;
			}
			case "csv": {
				try {
					Files.createDirectories(baseDir);
				} catch (IOException ignored) {}
				Path csvPath = baseDir.resolve("datos.csv");
				boolean exists = Files.exists(csvPath);
				CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
						.setHeader("DATO 1", "DATO 2", "DATO 3", "DATO 4", "DATO 5", "DATO 6")
						.setSkipHeaderRecord(exists)
						.get();

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
					Files.createDirectories(baseDir);
				} catch (IOException ignored) {}
				try {
					DatoAmbiental dato = JSONHandler.convertirListaADato(listaDatos);
					JSONHandler.agregarRegistroJSON(baseDir.resolve("datos.json").toString(), dato);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			case "xml": {
				// Implementación XML pendiente
				break;
			}
			case "rdf": {
				// Implementación RDF pendiente
				break;
			}
			default: {
				// Formato no soportado
				break;
			}
		}
	}

}
