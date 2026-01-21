<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sede.dao.EntidadDAO"%>
<%@ page import="com.sede.model.Entidad"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SEDE ELECTRÓNICA GF - Registro de Trámite</title>
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
        
        input[type="text"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }
        
        input[type="text"]:focus,
        select:focus {
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
        
        .error-message {
            color: red;
            font-size: 12px;
            margin-top: 5px;
        }
    </style>
</head>
<body>
    <div class="header">
        SEDE ELECTRÓNICA GF
    </div>
    
    <div class="container">
        <form action="ServletRegistro" method="post">
            <input type="hidden" name="accion" value="grabar">
            
            <div class="form-group">
                <label for="dni">DNI solicitante <span class="required">*</span></label>
                <input type="text" id="dni" name="dni" required 
                       pattern="[0-9]{8}[A-Za-z]" 
                       title="Formato: 8 dígitos seguidos de una letra (ej: 12345678A)"
                       placeholder="12345678A">
                <div class="error-message" id="dniError"></div>
            </div>
            
            <div class="form-group">
                <label for="nombre">NOMBRE solicitante <span class="required">*</span></label>
                <input type="text" id="nombre" name="nombre" required 
                       maxlength="100"
                       placeholder="Nombre del solicitante">
            </div>
            
            <div class="form-group">
                <label for="apellidos">APELLIDOS solicitante <span class="required">*</span></label>
                <input type="text" id="apellidos" name="apellidos" required 
                       maxlength="150"
                       placeholder="Apellidos del solicitante">
            </div>
            
            <div class="form-group">
                <label for="tramite">TRAMITE <span class="required">*</span></label>
                <input type="text" id="tramite" name="tramite" required 
                       maxlength="200"
                       placeholder="Descripción del trámite">
            </div>
            
            <div class="form-group">
                <label for="idEntidad">ENTIDAD <span class="required">*</span></label>
                <select id="idEntidad" name="idEntidad" required>
                    <option value="">-- Seleccione una entidad --</option>
                    <%
                        try {
                            EntidadDAO entidadDAO = new EntidadDAO();
                            List<Entidad> entidades = entidadDAO.listarEntidades();
                            
                            for (Entidad entidad : entidades) {
                    %>
                                <option value="<%= entidad.getIdEntidad() %>">
                                    <%= entidad.getNombreEntidad() %>
                                </option>
                    <%
                            }
                        } catch (Exception e) {
                            out.println("<option value=''>Error al cargar entidades</option>");
                            e.printStackTrace();
                        }
                    %>
                </select>
            </div>
            
            <div class="button-container">
                <button type="submit">Grabar</button>
            </div>
        </form>
    </div>
    
    <script>
        // Validación adicional en el cliente
        document.getElementById('dni').addEventListener('blur', function() {
            var dni = this.value;
            var errorDiv = document.getElementById('dniError');
            var pattern = /^[0-9]{8}[A-Za-z]$/;
            
            if (dni && !pattern.test(dni)) {
                errorDiv.textContent = 'Formato inválido. Debe ser 8 dígitos seguidos de una letra.';
            } else {
                errorDiv.textContent = '';
            }
        });
    </script>
</body>
</html>
