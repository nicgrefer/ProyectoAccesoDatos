<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Tratamiento de ficheros</title>
</head>
<body>
	<div class="container">
		<div class="header">
			<h1>Tratamiento de Ficheros</h1>
			<p>Sistema de Gestión de Datos Ambientales</p>
		</div>

		<div class="content">
			<form action="ServletFich" method="post" enctype="multipart/form-data">
				<section class="seccionIzquierda">
					<h2>1. Selecciona el Formato</h2>
					<div class="form-group">
						<span>Formato del fichero:</span>
						<select name="formato">
							<option value="xls">XLS</option>
							<option value="csv">CSV</option>
							<option value="json">JSON</option>
							<option value="xml">XML</option>
							<option value="rdf">RDF</option>
						</select>
					</div>
					<hr>
					<h2>2. Selecciona la Operación</h2>
					<div class="form-group">
						<span>¿Qué quiere hacer con el fichero?</span>
						<br>
						<label>
							<span>Lectura:</span>
							<input type="radio" name="accion" value="lectura">
						</label>
						<label>
							<span>Escritura:</span>
							<input type="radio" name="accion" value="escritura">
						</label>
					</div>
				</section>

				<section class="seccionDerecha">
					<h2>3. Datos del Registro</h2>
					<div id="fileUpload" style="display:none; margin-bottom:12px;">
						<label>
							<span id="uploadLabel">Subir archivo:</span>
							<input id="uploadFile" type="file" name="uploadFile" accept="application/json">
						</label>
					</div>
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
						<textarea name="dato5" rows="3" cols="20"></textarea>
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

				<script>
					// Mostrar/ocultar campo de subida y actualizar label/accept según formato y acción
					function toggleUpload() {
						const lectura = document.querySelector('input[name="accion"][value="lectura"]');
						const fileDiv = document.getElementById('fileUpload');
						const formatoSel = document.querySelector('select[name="formato"]');
						const formato = formatoSel ? formatoSel.value : '';
						const uploadLabel = document.getElementById('uploadLabel');
						const uploadInput = document.getElementById('uploadFile');

						if (lectura && lectura.checked) {
							// Mostrar y adaptar según formato
							fileDiv.style.display = 'block';
							if (formato === 'json') {
								uploadLabel.textContent = 'Subir archivo JSON:';
								uploadInput.accept = 'application/json';
							} else if (formato === 'xml') {
								uploadLabel.textContent = 'Subir archivo XML:';
								uploadInput.accept = 'text/xml,application/xml';
							} else if (formato === 'rdf') {
								uploadLabel.textContent = 'Subir archivo RDF:';
								uploadInput.accept = 'application/rdf+xml,text/turtle,*/*';
							} else {
								uploadLabel.textContent = 'Subir archivo:';
								uploadInput.accept = '*/*';
							}
						} else {
							fileDiv.style.display = 'none';
							// limpiar valor del input si se oculta
							if (uploadInput) uploadInput.value = '';
						}
					}

					// Añadir listeners
					document.addEventListener('DOMContentLoaded', function(){
						const radios = document.querySelectorAll('input[name="accion"]');
						radios.forEach(r=>r.addEventListener('change', toggleUpload));
						const formatoSel = document.querySelector('select[name="formato"]');
						if (formatoSel) formatoSel.addEventListener('change', toggleUpload);
						toggleUpload();
					});
				</script>

				<br>
				<button name="boton" value="enviarTratamiento">Enviar</button>

				<div class="info-box">
					<p><strong>Información:</strong> Este sistema permite leer y escribir datos en diferentes formatos. Seleccione el formato deseado y la operación a realizar.</p>
				</div>
			</form>
		</div>
	</div>
</body>
</html>