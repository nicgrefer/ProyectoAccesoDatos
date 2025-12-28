# 🌍 Sistema de Manejo de Ficheros - Datos Ambientales

Aplicación web JEE para la lectura y escritura de datos ambientales relacionados con los Objetivos de Desarrollo Sostenible (ODS) en múltiples formatos de archivo.

---

## 📋 Descripción

Este proyecto es una aplicación web desarrollada en Java EE que permite gestionar datos ambientales (emisiones de CO2, temperatura, fuentes de energía) mediante diferentes formatos de ficheros: JSON, CSV, XML, XLS (Excel) y RDF.

**Centro:** Gregorio Fernández  
**Curso:** 2º DAM  
**Asignatura:** Acceso a Datos

---

## ✨ Características

- ✅ Lectura de ficheros en 5 formatos diferentes
- ✅ Escritura/añadir registros en 5 formatos diferentes
- ✅ Visualización de datos en tabla HTML
- ✅ Manejo robusto de errores
- ✅ Interfaz web intuitiva y responsive
- ✅ Validación de datos
- ✅ Datos relacionados con ODS (Objetivos de Desarrollo Sostenible)

---

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 11**
- **Java EE (Servlet API 4.0)**
- **JSP (JavaServer Pages)**

### Librerías para Manejo de Ficheros
- **Gson 2.10.1** - Procesamiento JSON
- **OpenCSV 5.7.1** - Procesamiento CSV
- **Apache POI 5.2.3** - Procesamiento Excel (XLS/XLSX)
- **Apache Jena 4.9.0** - Procesamiento RDF
- **DOM Parser** - Procesamiento XML (incluido en Java)

### Herramientas
- **Maven** - Gestión de dependencias
- **Apache Tomcat 9+** - Servidor de aplicaciones

---

##  Estructura del Proyecto

```
manejo-ficheros/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── models/
│   │   │   │   └── DatoAmbiental.java
│   │   │   ├── servlets/
│   │   │   │   └── FileHandlerServlet.java
│   │   │   └── utils/
│   │   │       ├── JSONHandler.java
│   │   │       ├── CSVHandler.java
│   │   │       ├── XMLHandler.java
│   │   │       ├── XLSHandler.java
│   │   │       └── RDFHandler.java
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml
│   │       │   └── data/  (archivos generados)
│   │       ├── TratamientoFich.jsp
│   │       ├── AccesoDatosA.jsp
│   │       └── Error.jsp
│   └── test/
├── pom.xml
└── README.md
```

---

## 🚀 Instalación y Ejecución

### Requisitos Previos
- JDK 11 o superior
- Apache Maven 3.6+
- Apache Tomcat 9+
- IDE (Eclipse, IntelliJ IDEA, NetBeans)

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone [URL_DEL_REPOSITORIO]
cd manejo-ficheros
```

2. **Compilar el proyecto con Maven**
```bash
mvn clean install
```

3. **Desplegar en Tomcat**
   - Copiar el archivo `target/manejo-ficheros.war` a la carpeta `webapps` de Tomcat
   - O configurar el servidor en tu IDE y ejecutar directamente

4. **Acceder a la aplicación**
```
http://localhost:8080/manejo-ficheros/
```

---

## 💻 Uso de la Aplicación

### Lectura de Datos
1. Seleccionar el formato del fichero (JSON, CSV, XML, XLS, RDF)
2. Seleccionar la operación "Lectura"
3. Hacer clic en "Procesar"
4. Visualizar los datos en forma de tabla

### Escritura de Datos
1. Seleccionar el formato del fichero
2. Seleccionar la operación "Escritura"
3. Completar el formulario con los datos:
   - ID (obligatorio)
   - País (obligatorio)
   - Año (obligatorio)
   - Emisión CO2 (opcional)
   - Temperatura (opcional)
   - Fuente de Energía (opcional)
4. Hacer clic en "Procesar"
5. Ver confirmación del registro guardado

---

## 📊 Formato de Datos

### Modelo de Datos
```java
public class DatoAmbiental {
    private String id;
    private String pais;
    private String anio;
    private double emisionCO2;  // toneladas
    private double temperatura; // grados celsius
    private String fuenteEnergia;
}
```

### Ejemplos de Formatos

**JSON:**
```json
{
  "id": "001",
  "pais": "España",
  "anio": "2023",
  "emisionCO2": 1450.75,
  "temperatura": 15.8,
  "fuenteEnergia": "Solar"
}
```

**CSV:**
```csv
ID,Pais,Anio,EmisionCO2,Temperatura,FuenteEnergia
001,España,2023,1450.75,15.8,Solar
```

**XML:**
```xml
<dato>
  <id>001</id>
  <pais>España</pais>
  <anio>2023</anio>
  <emisionCO2>1450.75</emisionCO2>
  <temperatura>15.8</temperatura>
  <fuenteEnergia>Solar</fuenteEnergia>
</dato>
```

---

## 🔧 Configuración

### Ubicación de Archivos
Los archivos se almacenan en: `WEB-INF/data/`
- `datos_ambientales.json`
- `datos_ambientales.csv`
- `datos_ambientales.xml`
- `datos_ambientales.xlsx`
- `datos_ambientales.rdf`

### Configuración del Servlet
El servlet está mapeado en: `/FileHandler`

---

## 🐛 Manejo de Errores

La aplicación maneja los siguientes tipos de errores:
- Archivo no encontrado
- Formato de datos incorrecto
- Errores de lectura/escritura
- Validación de campos obligatorios
- Errores de parseo

Todos los errores se redirigen a `Error.jsp` con información detallada.

---

## 👥 Equipo de Desarrollo

- Sara - XMLHandler, XLSHandler
- Nicolás - JSONHandler
- [Nombre del Miembro 2] -CSVHandler
- [Nombre del Miembro 3] - RDFHandler, Servlet
- [Nombre del Miembro 4] - JSPs, Pruebas

---

## 📝 Evaluación

**Criterios de Evaluación:**
- Implementación lectura (2.5 puntos)
- Funcionamiento lectura (2.5 puntos)
- Implementación escritura (2.5 puntos)
- Funcionamiento escritura (2.5 puntos)

---

##  Enlaces Útiles

- Documentación Gson
- Documentación OpenCSV
- Documentación Apache POI
- Documentación Apache Jena
- ODS - Objetivos de Desarrollo Sostenible

---

## 📄 Licencia

Este proyecto es un trabajo académico para el Centro de Enseñanza "Gregorio Fernández".

## 📧 Contacto

Para consultas sobre el proyecto, contactar con los miembros del equipo o la profesora de la asignatura.

---

**Centro de Enseñanza Concertada "Gregorio Fernández"**  
2º Desarrollo de Aplicaciones Multiplataforma  
Acceso a Datos - Unidad 2
