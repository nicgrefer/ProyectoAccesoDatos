<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SEDE ELECTRÓNICA GF - Mensaje</title>
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
            max-width: 600px;
            margin: 40px auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        
        .message {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .success {
            color: #2e7d32;
            font-size: 18px;
            line-height: 1.6;
        }
        
        .error {
            color: #c62828;
            font-size: 18px;
            line-height: 1.6;
        }
        
        .registro-info {
            background-color: #f1f8e9;
            border: 2px solid #2e7d32;
            border-radius: 4px;
            padding: 20px;
            margin: 20px 0;
        }
        
        .registro-info p {
            margin: 10px 0;
            font-size: 16px;
        }
        
        .numero-registro {
            font-weight: bold;
            color: #1b5e20;
            font-size: 18px;
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
    </style>
</head>
<body>
    <div class="header">
        SEDE ELECTRÓNICA GF
    </div>
    
    <div class="container">
        <%
            Boolean exito = (Boolean) request.getAttribute("exito");
            String numRegistro = (String) request.getAttribute("numRegistro");
            Date fechaRegistro = (Date) request.getAttribute("fechaRegistro");
            String mensaje = (String) request.getAttribute("mensaje");
            
            if (exito != null && exito) {
        %>
            <div class="message success">
                <p>La grabación se ha realizado correctamente en registro. Guarde el siguiente número de registro:</p>
            </div>
            
            <div class="registro-info">
                <p><strong>Número registro:</strong> <span class="numero-registro"><%= numRegistro %></span></p>
                <p><strong>Fecha de registro:</strong> <%= fechaRegistro %></p>
            </div>
        <%
            } else {
        %>
            <div class="message error">
                <p>No se ha registrado el trámite. <%= mensaje != null ? mensaje : "Error al realizar la grabación." %></p>
            </div>
        <%
            }
        %>
        
        <div class="button-container">
            <a href="<%= request.getContextPath() %>/Registro.jsp" class="button">Nuevo Registro</a>
            <a href="<%= request.getContextPath() %>/Buscar.jsp" class="button">Consulta Registro</a>
        </div>
    </div>
</body>
</html>
