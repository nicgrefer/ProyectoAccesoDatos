<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<h1 style="color: red;">TIPO DE ERROR</h1>
	
	<%
		if (request.getAttribute("error")!=null) {
			String error = (String)request.getAttribute("error");%>
					
			<span><%= error %></span>
		<%}
	%>
	
	<form action="ServletFich" method="post">
		<button name="boton" value="volverTratamiento">Volver</button>
	</form>
</body>
</html>