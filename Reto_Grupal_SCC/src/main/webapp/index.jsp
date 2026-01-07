<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Proyecto Acceso Datos</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f8f9fa;
            padding: 20px;
        }
        .container {
            background: white;
            padding: 100px 120px;
            border:  1px solid #e0e0e0;
            max-width: 900px;
            width: 100%;
            text-align: center;
        }
        h1 {
            font-size: 48px;
            font-weight: 300;
            color: #2c3e50;
            margin-bottom: 16px;
            letter-spacing: -1px;
        }
        .subtitle {
            color: #7f8c8d;
            font-size: 20px;
            margin-bottom: 60px;
            font-weight: 300;
        }
        . team {
            margin-bottom:  60px;
            padding:  30px 0;
            border-top: 1px solid #e0e0e0;
            border-bottom: 1px solid #e0e0e0;
        }
        . team-title {
            font-size: 13px;
            text-transform: uppercase;
            letter-spacing: 2px;
            color: #95a5a6;
            margin-bottom: 20px;
            font-weight: 500;
        }
        .team-members {
            font-size: 18px;
            color: #34495e;
            line-height: 2;
            font-weight: 300;
        }
        a {
            display:  inline-block;
            color:  white;
            background-color: #2c3e50;
            padding: 20px 50px;
            text-decoration: none;
            font-size: 16px;
            transition: all 0.2s;
            letter-spacing:  1px;
        }
        a:hover {
            background-color: #34495e;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Proyecto Acceso a Datos</h1>
        <p class="subtitle">Sistema de Gestión de Ficheros en Múltiples Formatos</p>
        
        <div class="team">
            <div class="team-title">Desarrollado por</div>
            <div class="team-members">
                Nicolás · Sara · Paula · Juan · Gabriel
            </div>
        </div>
        
        <a href="TratamientoFich.jsp">Ir a Tratamiento de Ficheros →</a>
    </div>
</body>
</html>