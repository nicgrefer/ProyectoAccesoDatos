package com.gf;

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
import java.lang.module.ModuleDescriptor.Builder;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	
	private static final Path PATH = Paths.get("user.dir", "src", "main", "resources");
       
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
				
				if (param==null) {
					mensaje = "(*) Los campos no pueden estar vacíos";
					
					request.setAttribute("error", mensaje);
					hasError = true;
					
					break;
				} else listaDatos.add(param);
			}
			
			if (hasError) pagina = "TratamientoFich.jsp";
			else {
				this.procesarDatos(listaDatos, formatoFichero);
				request.setAttribute("lista", listaDatos);
				pagina = "AccesoDatosA.jsp";
			}
			
			break;
		
		case "volverTratamiento":
			
			pagina = "TratamientoFich.jsp";
			break;
		}
		
		request.getRequestDispatcher(pagina).forward(request, response);
		
	}

	private void procesarDatos(List<String> listaDatos, String formatoFichero) {
		switch (formatoFichero) {
			case "xls" -> {
				// Lógica para escribir
				EasyExcel.write(PATH.toString().concat("datos.xls"), List.class)
						.sheet("Datos")
						.doWrite(listaDatos);
				
			}
			case "csv" -> {
			    CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
			    		.setHeader(new String[] {"DATO 1", "DATO 2", "DATO 3", "DATO 4", "DATO 5", "DATO 6"})
			    		.get();
			    StringWriter sw = new StringWriter();
			    try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
			    	for (String dato : listaDatos) {
			    		printer.print(dato);
				    }
				} catch (IOException e) {
					// Manejar error
					e.printStackTrace();
				}
			}
			case "json" -> {
				
			}
			case "xml" -> {
				
			}
			case "rdf" -> {
				
			}
		}
	}

}
