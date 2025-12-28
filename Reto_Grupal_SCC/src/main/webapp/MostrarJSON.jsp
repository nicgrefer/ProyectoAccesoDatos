<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.gf.models.DatoAmbiental" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registros JSON</title>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Registros JSON</h1>
        </div>
        <div class="content">
            <%
                List<DatoAmbiental> registros = (List<DatoAmbiental>) request.getAttribute("registros");
                if (registros == null) registros = java.util.Collections.emptyList();
            %>
            <table border="1">
                <thead>
                    <tr>
                        <th>DATO 1</th>
                        <th>DATO 2</th>
                        <th>DATO 3</th>
                        <th>DATO 4</th>
                        <th>DATO 5</th>
                        <th>DATO 6</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (DatoAmbiental d : registros) {
                    %>
                    <tr>
                        <td><%= d.getDato1() %></td>
                        <td><%= d.getDato2() %></td>
                        <td><%= d.getDato3() %></td>
                        <td><%= d.getDato4() %></td>
                        <td><%= d.getDato5() %></td>
                        <td><%= d.getDato6() %></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>

            <div style="margin-top:12px;">
                <a href="AccesoDatosA.jsp">Volver</a>
                <a href="DownloadJson" style="margin-left:12px;">Descargar JSON</a>
            </div>
        </div>
    </div>
</body>
</html>
