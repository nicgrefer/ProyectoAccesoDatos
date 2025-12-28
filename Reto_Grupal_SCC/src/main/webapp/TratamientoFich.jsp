<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tratamiento de ficheros</title>
</head>
<body>
	<form action="ServletFich" method="post">
		<section class="seccionIzquierda">
			<span>Formato del fichero:</span>
			<select  name="option">
				<option value="xls">XLS</option>
				<option value="csv">CSV</option>
				<option value="json">JSON</option>
				<option value="xml">XML</option>
				<option value="rdf">RDF</option>
			</select>
			<hr>
			<span>¿Qué quiere hacer con el fichero?</span>
			<br>
			<label>
				<span>Lectura:</span>
				<input type="radio" name="radio" value="lectura">
			</label>
			<label>
				<span>Escritura:</span>
				<input type="radio" name="radio" value="escritura">
			</label>
		</section>
		<section class="seccionDerecha">
			<%
				for(int numero = 1; numero<=4; numero++) {
				%>
					<label>
						<span>DATO <%= numero %>:</span>
						<input type="text" name="dato<%= numero %>" placeholder="Escriba aquí...">
					</label>
				<%
				}
			%>
			<label>
				<span>DATO 5:</span>
				<textarea rows="3" cols="20" name="dato5"></textarea>
			</label>
			<label>
				<span>DATO 6:</span>
				<input type="text" name="dato6" placeholder="Escriba aquí...">
			</label>

			<%
				if (request.getAttribute("error")!=null) {
					String error = (String)request.getAttribute("error");%>
					
					<span style="color: red;"><%= error %></span>
				<%}
			%>

		</section>
		<br>
		<button name="boton" value="enviarTratamiento">Enviar</button>
	</form>
</body>
</html>