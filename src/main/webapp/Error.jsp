<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SEDE ELECTRÓNICA GF - Error</title>
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
        
        .error-icon {
            text-align: center;
            font-size: 48px;
            color: #c62828;
            margin-bottom: 20px;
        }
        
        .error-message {
            text-align: center;
            color: #c62828;
            font-size: 18px;
            margin-bottom: 20px;
        }
        
        .error-details {
            background-color: #ffebee;
            border: 1px solid #ef5350;
            border-radius: 4px;
            padding: 15px;
            margin: 20px 0;
            font-size: 14px;
            color: #333;
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
        <div class="error-icon">⚠️</div>
        
        <div class="error-message">
            <h2>Ha ocurrido un error</h2>
        </div>
        
        <%
            String errorMsg = (String) request.getAttribute("error");
            Exception exception = (Exception) request.getAttribute("exception");
            
            if (errorMsg != null) {
        %>
            <div class="error-details">
                <strong>Detalles del error:</strong><br>
                <%= errorMsg %>
            </div>
        <%
            } else if (exception != null) {
        %>
            <div class="error-details">
                <strong>Detalles del error:</strong><br>
                <%= exception.getMessage() %>
            </div>
        <%
            } else {
        %>
            <div class="error-details">
                <strong>Error desconocido</strong><br>
                Por favor, inténtelo de nuevo más tarde.
            </div>
        <%
            }
        %>
        
        <div class="button-container">
            <a href="<%= request.getContextPath() %>/Registro.jsp" class="button">Volver al Inicio</a>
        </div>
    </div>
</body>
</html>
