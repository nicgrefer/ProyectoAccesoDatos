<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SEDE ELECTRÓNICA GF - Buscar Registro</title>
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
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        
        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }
        
        input[type="text"]:focus {
            outline: none;
            border-color: #2e7d32;
            box-shadow: 0 0 5px rgba(46, 125, 50, 0.3);
        }
        
        .required {
            color: red;
        }
        
        .button-container {
            text-align: center;
            margin-top: 30px;
        }
        
        button {
            background-color: #2e7d32;
            color: white;
            padding: 12px 40px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        
        button:hover {
            background-color: #1b5e20;
        }
        
        .link-container {
            text-align: center;
            margin-top: 20px;
        }
        
        .link {
            color: #2e7d32;
            text-decoration: none;
            font-size: 14px;
        }
        
        .link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="header">
        SEDE ELECTRÓNICA GF
    </div>
    
    <div class="container">
        <form action="ServletRegistro" method="get">
            <input type="hidden" name="accion" value="buscar">
            
            <div class="form-group">
                <label for="numRegistro">Número Registro <span class="required">*</span></label>
                <input type="text" id="numRegistro" name="numRegistro" required 
                       placeholder="REG_000001">
            </div>
            
            <div class="button-container">
                <button type="submit">Buscar</button>
            </div>
        </form>
        
        <div class="link-container">
            <a href="<%= request.getContextPath() %>/Registro.jsp" class="link">« Volver al formulario de registro</a>
        </div>
    </div>
</body>
</html>
