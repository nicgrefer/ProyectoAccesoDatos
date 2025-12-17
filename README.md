# 📁 Aplicación Web JEE – Manejo de Ficheros  
**Reto Grupal – Unidad de Trabajo 2**  
**Acceso a Datos – 2º DAM**  
**Centro de Enseñanza Concertada “Gregorio Fernández”**

![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/nicgrefer/ProyectoAccesoDatos)

---

## 📌 Descripción del Proyecto

Este proyecto consiste en el desarrollo de una **aplicación web JEE** que permite la **lectura y escritura de ficheros en distintos formatos de datos** abiertos relacionados con los **Objetivos de Desarrollo Sostenible (ODS)**, concretamente con información vinculada a la **huella de carbono**.

La aplicación permite al usuario seleccionar:
- El **formato del fichero**.
- La **operación a realizar** (lectura o escritura).
- Visualizar los datos leídos en formato **tabla**.
- Confirmar la escritura de nuevos registros.
- Mostrar una **página de error personalizada** en caso de fallo.

---

## 🛠️ Tecnologías Utilizadas

- **Java JEE**
- **Servlets**
- **JSP**
- **Apache Tomcat**
- **HTML / CSS**
- **Maven** (si aplica)
- Librerías Java para tratamiento de ficheros:
  - CSV
  - XLS (Apache POI)
  - JSON
  - XML (DOM / SAX)
  - RDF

---

## 📂 Formatos de Ficheros Soportados

| Formato | Lectura | Escritura |
|-------|--------|----------|
| CSV | ✅ | ✅ |
| XLS | ✅ | ✅ |
| JSON | ✅ | ✅ |
| XML (DOM/SAX) | ✅ | ✅ |
| RDF | ✅ | ✅ |

---

## 🔄 Flujo de la Aplicación

1. **AccesoDatosA.jsp**
   - Página principal
   - Selección del formato de fichero
   - Selección de lectura o escritura

2. **TratamientoFich.jsp**
   - Muestra el resultado de:
     - Lectura → datos en tabla
     - Escritura → confirmación y apertura del fichero

3. **Servlet de Control**
   - Gestiona la lógica del negocio
   - Llama a los métodos de lectura/escritura según formato

4. **Error.jsp**
   - Muestra errores de:
     - Lectura
     - Escritura
     - Formato incorrecto
     - Excepciones del sistema

---

## 🌍 Datos Abiertos Utilizados

Los datos empleados provienen de **fuentes de datos abiertos** relacionadas con los **Objetivos de Desarrollo Sostenible**, por ejemplo:

- Medición de la huella de carbono
- Emisiones de CO₂
- Indicadores medioambientales

Los ficheros se encuentran disponibles en varios formatos (CSV, JSON, XML, XLS, RDF).

---

## 📊 Ejemplo de Resultados

### ✔️ Lectura
- Visualización de los datos en una **tabla HTML**.
- Adaptada dinámicamente según el formato del fichero.

### ✔️ Escritura
- Inserción de un nuevo registro.
- Confirmación visual.
- Apertura del fichero actualizado (especialmente en XLS).

---

## 🧪 Gestión de Errores

La aplicación controla:
- Ficheros inexistentes
- Formatos no válidos
- Errores de parseo
- Excepciones de lectura/escritura

➡️ Todos los errores redirigen a **Error.jsp** indicando el tipo de error ocurrido.

---
````
/src
├── controller (Servlets)
├── model (Clases de acceso a datos)
├── utils (Lectores y escritores)
└── resources (Ficheros de datos)
/webapp
├── AccesoDatosA.jsp
├── TratamientoFich.jsp
└── Error.jsp
````

---

## 🚀 Despliegue

1. Importar el proyecto en **Eclipse / IntelliJ**
2. Configurar **Apache Tomcat**
3. Ejecutar el proyecto
4. Acceder desde el navegador



http://localhost:8080/NombreProyecto


---

## 👥 Autores



---

## 📎 Enlaces

- 📌 Repositorio GitHub: *(añadir enlace aquí)*
- 📌 Plataforma Moodle: *(enlace entregado a la profesora)*

---

## ✅ Estado del Proyecto

✔️ Proyecto finalizado  
✔️ Cumple todos los requisitos del enunciado  
✔️ Preparado para evaluación

---

