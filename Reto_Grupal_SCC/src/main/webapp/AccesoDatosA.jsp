<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList;" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
	ArrayList<String> lista = (ArrayList<String>) request.getAttribute("lista");
	%>

	<h1>DATOS</h1>
	
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
				for(int i = 0; i<6; i++) {
				%>
				<td><%=lista.get(i) %></td>
				<%
				}
			%>
			</tr>
		</tbody>
	</table>
	
	<form action="ServletFich" method="post">
		<button name="boton" value="volverTratamiento">Volver</button>
	</form>
</body>
</html>