-- Script de creación de la base de datos SEDE
-- Base de datos para la aplicación SEDE ELECTRÓNICA GF

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS sede CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sede;

-- Tabla: entidad
-- Almacena las diferentes entidades administrativas educativas
CREATE TABLE IF NOT EXISTS entidad (
    id_entidad INT PRIMARY KEY AUTO_INCREMENT,
    nombre_entidad VARCHAR(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: registros
-- Almacena los trámites registrados por los solicitantes
CREATE TABLE IF NOT EXISTS registros (
    num_registro VARCHAR(50) PRIMARY KEY,
    dni_solicitante VARCHAR(10) NOT NULL,
    nombre_solicitante VARCHAR(100) NOT NULL,
    apellidos_solicitante VARCHAR(150) NOT NULL,
    tramite VARCHAR(200) NOT NULL,
    id_entidad INT NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_entidad) REFERENCES entidad(id_entidad)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insertar datos iniciales en la tabla entidad
INSERT INTO entidad (nombre_entidad) VALUES 
('INSPECCIÓN EDUCATIVA INFANTIL'),
('INSPECCIÓN EDUCATIVA PRIMARIA'),
('INSPECCIÓN EDUCATIVA SECUNDARIA'),
('INSPECCIÓN EDUCATIVA BACHILLERATO'),
('INSPECCIÓN EDUCATIVA FP'),
('CONSEJERIA EDUCACION'),
('MINISTERIO DE EDUCACIÓN Y CIENCIA');

-- Verificar las inserciones
SELECT * FROM entidad;
