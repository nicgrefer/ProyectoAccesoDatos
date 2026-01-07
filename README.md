# Reto Grupal - Sistema de Gestión de Datos

Aplicación web Java EE para la lectura y escritura de datos en múltiples formatos de archivo.

---

## Descripción

Este proyecto es una aplicación web desarrollada en Java EE que permite gestionar datos genéricos mediante diferentes formatos de ficheros: JSON, CSV, XML, XLS y RDF.

**Centro:** Gregorio Fernández  
**Curso:** 2º DAM  
**Asignatura:** Acceso a Datos

---

## Estado del Proyecto

### Formatos Implementados

| Formato | Lectura | Escritura | Handler/Localización |
|---------|---------|-----------|----------------------|
| JSON    | ✅      | ✅        | JSONHandler.java |
| XML     | ✅      | ✅        | XMLHandler.java |
| CSV     | ❌      | ✅        | En ServletFich.java (línea 191) |
| XLS     | ❌      | ✅        | ExcelHandler.java |
| RDF     | ❌      | ❌        | No implementado |

---

## Tecnologías Utilizadas

### Backend
- **Java 17**
- **Jakarta EE (Servlet API 6.0)**
- **JSP (JavaServer Pages)**

### Librerías
- **Gson 2.10.1** - Procesamiento JSON
- **Apache Commons CSV 1.14.1** - Procesamiento CSV
- **EasyExcel 4.0.3** - Procesamiento Excel
- **DOM Parser** - Procesamiento XML (incluido en Java)

### Herramientas
- **Maven** - Gestión de dependencias
- **Apache Tomcat 10+** - Servidor de aplicaciones

---

## Estructura del Proyecto

```
Reto_Grupal_SCC/
├── src/main/java/com/gf/
│   ├── models/
│   │   └── DatoAmbiental.java
│   ├── handlers/
│   │   ├── JSONHandler.java
│   │   ├── XMLHandler.java
│   │   ├── ExcelHandler.java
│   │   └── DatoAmbientalListener.java
│   ├── ServletFich.java
│   ├── DownloadJsonServlet.java
│   ├── DownloadXmlServlet.java
│   └── DownloadExcelServlet.java
├── src/main/webapp/
│   ├── WEB-INF/web.xml
│   ├── files/ (archivos generados)
│   ├── TratamientoFich.jsp
│   ├── AccesoDatosA.jsp
│   ├── MostrarJSON.jsp
│   ├── MostrarXML.jsp
│   └── Error.jsp
├── pom.xml
└── README.md
```

---

## Modelo de Datos

```java
public class DatoAmbiental {
    private String dato1;
    private String dato2;
    private String dato3;
    private String dato4;
    private String dato5;
    private String dato6;
}
```

---

## Instalación y Ejecución

### Requisitos Previos
- JDK 17 o superior
- Apache Maven 3.6+
- Apache Tomcat 10+

### Pasos

1. **Compilar el proyecto**
```bash
mvn clean install
```

2. **Desplegar en Tomcat**
- Copiar `target/Reto_Grupal_SCC.war` a la carpeta `webapps` de Tomcat

3. **Acceder a la aplicación**
```
http://localhost:8080/Reto_Grupal_SCC/TratamientoFich.jsp
```

---

## Uso de la Aplicación

### Lectura de Datos
1. Seleccionar formato (JSON o XML)
2. Seleccionar operación "Lectura"
3. Subir archivo
4. Visualizar datos en tabla

### Escritura de Datos
1. Seleccionar formato (JSON, XML, CSV o XLS)
2. Seleccionar operación "Escritura"
3. Completar los 6 campos de datos
4. Enviar y descargar archivo generado

---

## API de Servlets

| Servlet | Método | Descripción |
|---------|--------|-------------|
| `/ServletFich` | POST | Procesa lectura/escritura de archivos |
| `/ServletFich` | GET | Respuesta de texto plano |
| `/DownloadJson` | GET | Descarga archivo datos.json |
| `/DownloadXml` | GET | Descarga archivo datos.xml |
| `/DownloadExcel` | GET | Descarga archivo datos.xlsx |

---

## Archivos de Datos

Los archivos se almacenan en: `webapp/files/`
- `datos.json`
- `datos.xml`
- `datos.csv`
- `datos.xlsx`

---

## Funcionalidades por Handler

### JSONHandler.java
- `leerJSON(String rutaArchivo)` - Lee lista de DatoAmbiental desde JSON
- `escribirJSON(String rutaArchivo, List<DatoAmbiental> datos)` - Escribe lista completa
- `agregarRegistroJSON(String rutaArchivo, DatoAmbiental nuevoDato)` - Añade un registro
- `convertirListaADato(List<String> listaDatos)` - Convierte lista a objeto
- `convertirDatoALista(DatoAmbiental dato)` - Convierte objeto a lista

### XMLHandler.java
- `leerXML(String rutaArchivo)` - Lee datos desde XML
- `escribirXML(String rutaArchivo, List<DatoAmbiental> datos)` - Escribe datos a XML
- `agregarRegistroXML(String rutaArchivo, DatoAmbiental nuevoDato)` - Añade registro
- `convertirListaADato(List<String> listaDatos)` - Convierte lista a objeto

### ExcelHandler.java
- `leerExcel(String rutaArchivo)` - Lee datos desde Excel (XLSX)
- `escribirExcel(String rutaArchivo, List<DatoAmbiental> datos)` - Escribe datos a Excel
- `convertirListaADato(List<String> listaDatos)` - Convierte lista a objeto
- `convertirDatoALista(DatoAmbiental dato)` - Convierte objeto a lista

### DatoAmbientalListener.java
- Listener para EasyExcel que permite leer archivos Excel

### ServletFich.java
- CSV: Implementa escritura con Apache Commons CSV (append mode)
- XLS: Implementa escritura con EasyExcel
- RDF: No implementado

---

## Errores Manejados

- Archivo no encontrado
- Formato de datos incorrecto
- Campos obligatorios vacíos
- Errores de parseo JSON/XML
- Parámetros incompletos

---

## Equipo de Desarrollo

- Paula - XMLHandler
- Nicolás - JSONHandler
- Gabreil - CSV (integrado en servlet)
- Juan - JSPs y servlets de descarga
- Sara - Excel (ExcelHandler + DownloadExcelServlet)

---

## Licencia

Proyecto académico - Centro de Enseñanza Concertada "Gregorio Fernández"
