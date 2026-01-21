<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sede.model.Registros"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SEDE ELECTRÓNICA GF - Consulta de Registro</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
        }
        
        .header {
            background-color: #2e7d32;
            color: white;
            padding: 20px;
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        .container {
            max-width: 700px;
            margin: 40px auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        
        .message {
            text-align: center;
            color: #c62828;
            font-size: 18px;
            margin-bottom: 30px;
        }
        
        .registro-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }
        
        .registro-table th {
            background-color: #2e7d32;
            color: white;
            padding: 12px;
            text-align: left;
            font-weight: bold;
        }
        
        .registro-table td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }
        
        .registro-table tr:hover {
            background-color: #f5f5f5;
        }
        
        .button-container {
            text-align: center;
            margin-top: 30px;
        }
        
        .button {
            display: inline-block;
            background-color: #2e7d32;
            color: white;
            padding: 12px 30px;
            margin: 0 10px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            text-decoration: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        
        .button:hover {
            background-color: #1b5e20;
        }
        
        .data-label {
            font-weight: bold;
            color: #555;
        }
    </style>
</head>
<body>
    <div class="header">
        SEDE ELECTRÓNICA GF
    </div>
    
    <div class="container">
        <%
            Boolean encontrado = (Boolean) request.getAttribute("encontrado");
            Registros registro = (Registros) request.getAttribute("registro");
            String mensaje = (String) request.getAttribute("mensaje");
            
            if (encontrado != null && encontrado && registro != null) {
        %>
            <h2 style="text-align: center; color: #2e7d32; margin-bottom: 20px;">Datos del Registro</h2>
            
            <table class="registro-table">
                <tr>
                    <th colspan="2">Información del Trámite</th>
                </tr>
                <tr>
                    <td class="data-label">Número de Registro:</td>
                    <td><%= registro.getNumRegistro() %></td>
                </tr>
                <tr>
                    <td class="data-label">DNI solicitante:</td>
                    <td><%= registro.getDniSolicitante() %></td>
                </tr>
                <tr>
                    <td class="data-label">NOMBRE solicitante:</td>
                    <td><%= registro.getNombreSolicitante() %></td>
                </tr>
                <tr>
                    <td class="data-label">APELLIDOS solicitante:</td>
                    <td><%= registro.getApellidosSolicitante() %></td>
                </tr>
                <tr>
                    <td class="data-label">TRAMITE:</td>
                    <td><%= registro.getTramite() %></td>
                </tr>
                <tr>
                    <td class="data-label">ENTIDAD:</td>
                    <td><%= registro.getEntidad().getNombreEntidad() %></td>
                </tr>
                <tr>
                    <td class="data-label">Fecha de registro:</td>
                    <td><%= registro.getFechaRegistro() %></td>
                </tr>
            </table>
            
            <div class="button-container">
                <a href="<%= request.getContextPath() %>/Registro.jsp" class="button">Nuevo Registro</a>
                <a href="<%= request.getContextPath() %>/Buscar.jsp" class="button">Nueva Búsqueda</a>
            </div>
        <%
            } else {
        %>
            <div class="message">
                <p><%= mensaje != null ? mensaje : "El trámite de registro no existe." %></p>
            </div>
            
            <div class="button-container">
                <a href="<%= request.getContextPath() %>/Registro.jsp" class="button">Nuevo Registro</a>
                <a href="<%= request.getContextPath() %>/Buscar.jsp" class="button">Nueva Búsqueda</a>
            </div>
        <%
            }
        %>
    </div>
</body>
</html>
