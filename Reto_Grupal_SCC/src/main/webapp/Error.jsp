<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Error - Sistema</title>
</head>
<body>
	<div class="container">
		<div class="header">
			<h1 style="color: white;">TIPO DE ERROR</h1>
		</div>
		
		<div class="content">
			<div class="error-details">
				<%
					if (request.getAttribute("error")!=null) {
						String error = (String)request.getAttribute("error");%>
								
						<span style="color: red;"><%= error %></span>
					<%}
				%>
			</div>
			
			<div class="actions">
				<form action="ServletFich" method="post">
					<button name="boton" value="volverTratamiento">Volver</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>