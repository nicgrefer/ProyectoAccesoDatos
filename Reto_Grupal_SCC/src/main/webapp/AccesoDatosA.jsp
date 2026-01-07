<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java. util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Resultados - Datos</title>
<style>
	table {
		border-collapse: collapse;
		width: 100%;
	}
	th, td {
		border: 1px solid #000;
		padding: 8px;
		text-align:  left;
	}
	th {
		background-color: #f2f2f2;
		font-weight: bold;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="header">
			<h1>DATOS</h1>
		</div>

		<div class="content">
			<%
			ArrayList<String> lista = (ArrayList<String>) request.getAttribute("lista");
			%>

			<div class="table-container">
				<table>
					<thead>
						<tr>
						<%
							for(int numeroCabecera = 1; numeroCabecera<=6; numeroCabecera++) {
							%>
							<th>DATO <%= numeroCabecera %></th>
							<%
							}
						%>
						</tr>
					</thead>
					<tbody>
						<tr>
						<%
							if (lista != null) {
								for(int i = 0; i<6 && i<lista.size(); i++) {
								%>
								<td><%=lista.get(i) %></td>
								<%
								}
							}
						%>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="actions">
				<form action="ServletFich" method="post" style="display: inline;">
					<button name="boton" value="volverTratamiento">
						Volver
					</button>
				</form>
				<%
					String formato = (String) request.getAttribute("formato");
					if (formato != null) {
						String downloadUrl = null;
						String fileName = null;
						if ("json".equalsIgnoreCase(formato)) {
							downloadUrl = "DownloadJson";
							fileName = "datos.json";
						} else if ("xml".equalsIgnoreCase(formato)) {
							downloadUrl = "DownloadXml";
							fileName = "datos.xml";
						} else if ("csv".equalsIgnoreCase(formato)) {
							downloadUrl = "DownloadCsv";
							fileName = "datos.csv";
						} else if ("xls".equalsIgnoreCase(formato) || "xlsx".equalsIgnoreCase(formato)) {
							downloadUrl = "DownloadExcel";
							fileName = "datos.xlsx";
						}
						
						if (downloadUrl != null) {
							java.nio.file.Path filePath = java.nio.file.Paths. get(
								getServletContext().getRealPath("/files"), fileName
							);
							if (java.nio.file.Files.exists(filePath)) {
				%>
				<a href="<%= downloadUrl %>" style="margin-left:12px;">Descargar <%= fileName.substring(0, fileName.lastIndexOf('.')).toUpperCase() %></a>
				<%
					} else if (formato != null && "xml".equalsIgnoreCase(formato)) {
				%>
				<!-- Botón para descargar XML (solo si el formato seleccionado fue XML) -->
				<a href="DownloadXml" style="margin-left:12px;">Descargar XML</a>
				<%
							}
						}
					}
				%>
			</div>
		</div>
	</div>
</body>
</html>