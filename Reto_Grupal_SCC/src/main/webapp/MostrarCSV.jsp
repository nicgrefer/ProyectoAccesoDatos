<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.gf.models.DatoAmbiental"%>
<! DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lectura CSV - Datos Ambientales</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color:  #4CAF50;
            color:  white;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #e0e0e0;
        }
        .actions {
            margin-top: 20px;
            text-align: center;
        }
        button, a {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin: 5px;
        }
        button:hover, a:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
            text-align: center;
            font-weight: bold;
        }
        .info {
            text-align: center;
            color: #666;
            margin:  10px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Lectura de Archivo CSV</h1>
        <p class="info">Datos Ambientales - Formato CSV</p>

        <%
        List<DatoAmbiental> registros = (List<DatoAmbiental>) request.getAttribute("registros");
        
        if (registros == null || registros.isEmpty()) {
        %>
            <p class="error">No se encontraron datos en el archivo CSV o el archivo está vacío.</p>
        <%
        } else {
        %>
            <p class="info">Total de registros: <strong><%= registros.size() %></strong></p>
            
            <table>
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
                    for (DatoAmbiental dato : registros) {
                    %>
                        <tr>
                            <td><%= dato. getDato1() %></td>
                            <td><%= dato.getDato2() %></td>
                            <td><%= dato.getDato3() %></td>
                            <td><%= dato.getDato4() %></td>
                            <td><%= dato.getDato5() %></td>
                            <td><%= dato.getDato6() %></td>
                        </tr>
                    <%
                    }
                    %>
                </tbody>
            </table>
        <%
        }
        %>

        <div class="actions">
            <form action="ServletFich" method="post" style="display: inline;">
                <button type="submit" name="boton" value="volverTratamiento">Volver al Formulario</button>
            </form>
            
            <a href="DownloadCsv">Descargar CSV</a>
        </div>
    </div>
</body>
</html>