# SEDE ELECTRÓNICA GF

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.6+-blue?style=flat-square&logo=apachemaven)
![Hibernate](https://img.shields.io/badge/Hibernate-6.4-green?style=flat-square&logo=hibernate)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)

Aplicación web JEE para la gestión de registros de trámites educativos utilizando el patrón MVC con Hibernate nativo y MySQL.

---

## 📋 Descripción

**SEDE ELECTRÓNICA GF** es una aplicación web desarrollada en Java EE que permite dar de alta y consultar registros de trámites para miembros de una comunidad educativa en distintas entidades administrativas relacionadas con educación.

**Centro:** Gregorio Fernández  
**Curso:** 2º DAM  
**Asignatura:** Acceso a Datos

---

## 🎯 Características

- ✅ **Patrón MVC**: Arquitectura clara con separación de responsabilidades
- ✅ **Hibernate Nativo**: ORM con anotaciones JPA para persistencia
- ✅ **MySQL**: Base de datos relacional
- ✅ **Validaciones**: Formato DNI español, campos obligatorios
- ✅ **Generación automática**: Números de registro únicos (REG_XXXXXX)
- ✅ **Manejo de excepciones**: Try-catch en DAOs y Servlets
- ✅ **Interfaz responsive**: CSS moderno y limpio

---

## 🏗️ Arquitectura

### Patrón MVC

```
┌─────────────┐
│   JSP       │  ← Vista (Registro.jsp, Buscar.jsp, Consultar.jsp, Mensaje.jsp)
└──────┬──────┘
       │
       ↓
┌─────────────┐
│  Servlet    │  ← Controlador (ServletRegistro.java)
└──────┬──────┘
       │
       ↓
┌─────────────┐
│  Lógica LN  │  ← Negocio (RegistrosLN.java)
└──────┬──────┘
       │
       ↓
┌─────────────┐
│    DAO      │  ← Acceso a Datos (EntidadDAO, RegistrosDAO)
└──────┬──────┘
       │
       ↓
┌─────────────┐
│  Hibernate  │  ← Persistencia (HibernateUtil)
└──────┬──────┘
       │
       ↓
┌─────────────┐
│   MySQL     │  ← Base de Datos
└─────────────┘
```

---

## 📁 Estructura del Proyecto

```
SedeElectronicaGF/
├── src/main/java/com/sede/
│   ├── model/
│   │   ├── Entidad.java          # POJO tabla entidad
│   │   └── Registros.java        # POJO tabla registros
│   ├── dao/
│   │   ├── HibernateUtil.java    # Gestión SessionFactory
│   │   ├── EntidadDAO.java       # DAO entidades
│   │   └── RegistrosDAO.java     # DAO registros
│   ├── ln/
│   │   └── RegistrosLN.java      # Lógica de negocio
│   └── servlet/
│       └── ServletRegistro.java  # Controlador principal
├── src/main/resources/
│   └── hibernate.cfg.xml         # Configuración Hibernate
├── src/main/webapp/
│   ├── WEB-INF/
│   │   └── web.xml              # Configuración web
│   ├── Registro.jsp             # Formulario de registro
│   ├── Mensaje.jsp              # Página de confirmación
│   ├── Buscar.jsp               # Formulario de búsqueda
│   ├── Consultar.jsp            # Resultados de búsqueda
│   ├── Error.jsp                # Página de errores
│   └── index.jsp                # Redirección a Registro
├── database/
│   └── sede.sql                 # Script de creación de BD
└── pom.xml                      # Dependencias Maven
```

---

## 🗄️ Modelo de Datos

### Tabla: entidad

| Campo          | Tipo         | Restricción        |
|----------------|--------------|-------------------|
| id_entidad     | INT          | PRIMARY KEY, AUTO_INCREMENT |
| nombre_entidad | VARCHAR(200) | NOT NULL          |

### Tabla: registros

| Campo                | Tipo          | Restricción        |
|---------------------|---------------|-------------------|
| num_registro        | VARCHAR(50)   | PRIMARY KEY        |
| dni_solicitante     | VARCHAR(10)   | NOT NULL          |
| nombre_solicitante  | VARCHAR(100)  | NOT NULL          |
| apellidos_solicitante | VARCHAR(150) | NOT NULL          |
| tramite             | VARCHAR(200)  | NOT NULL          |
| id_entidad          | INT           | FK → entidad      |
| fecha_registro      | TIMESTAMP     | DEFAULT CURRENT_TIMESTAMP |

### Datos Iniciales

```sql
INSERT INTO entidad (nombre_entidad) VALUES 
('INSPECCIÓN EDUCATIVA INFANTIL'),
('INSPECCIÓN EDUCATIVA PRIMARIA'),
('INSPECCIÓN EDUCATIVA SECUNDARIA'),
('INSPECCIÓN EDUCATIVA BACHILLERATO'),
('INSPECCIÓN EDUCATIVA FP'),
('CONSEJERIA EDUCACION'),
('MINISTERIO DE EDUCACIÓN Y CIENCIA');
```

---

## 🚀 Instalación y Ejecución

### Requisitos Previos

- **JDK 17** o superior
- **Apache Maven 3.6+**
- **Apache Tomcat 10+**
- **MySQL 8.0+**

### Pasos de Instalación

#### 1. Clonar el repositorio

```bash
git clone https://github.com/nicgrefer/ProyectoAccesoDatos.git
cd ProyectoAccesoDatos
```

#### 2. Configurar la base de datos

```bash
# Conectar a MySQL
mysql -u root -p

# Ejecutar el script de creación
source database/sede.sql
```

#### 3. Configurar Hibernate

Editar `src/main/resources/hibernate.cfg.xml` si es necesario:

```xml
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">tu_contraseña</property>
```

#### 4. Compilar el proyecto

```bash
mvn clean install
```

#### 5. Desplegar en Tomcat

```bash
# Copiar el WAR generado
cp target/SedeElectronicaGF.war $TOMCAT_HOME/webapps/

# O desplegar desde Maven
mvn tomcat7:deploy
```

#### 6. Acceder a la aplicación

```
http://localhost:8080/SedeElectronicaGF/
```

---

## 💻 Uso de la Aplicación

### 1. Registrar un Trámite

1. Acceder a la página principal (Registro.jsp)
2. Completar todos los campos:
   - **DNI**: Formato español (8 dígitos + letra). Ej: `12345678A`
   - **Nombre**: Nombre del solicitante
   - **Apellidos**: Apellidos del solicitante
   - **Trámite**: Descripción del trámite
   - **Entidad**: Seleccionar de la lista
3. Hacer clic en **"Grabar"**
4. Se mostrará el número de registro generado (ej: `REG_000001`)

### 2. Buscar un Trámite

1. Ir a la página de búsqueda (Buscar.jsp)
2. Introducir el número de registro
3. Hacer clic en **"Buscar"**
4. Se mostrarán los datos del trámite si existe

---

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17**
- **Jakarta EE 10** (Servlet API 6.0, JSP API 3.1)
- **Hibernate 6.4.1** (ORM con anotaciones JPA)
- **MySQL Connector/J 8.2.0**

### Frontend
- **JSP** (JavaServer Pages)
- **CSS3** (Estilos personalizados)
- **JavaScript** (Validaciones cliente)

### Herramientas
- **Maven** (Gestión de dependencias y build)
- **Apache Tomcat 10+** (Servidor de aplicaciones)
- **Git** (Control de versiones)

---

## 📚 Flujo de Información

### Grabar Registro

```
Registro.jsp 
    → [POST] ServletRegistro 
    → RegistrosLN.validarDatos()
    → RegistrosLN.generarNumeroRegistro()
    → RegistrosLN.procesarRegistro()
    → RegistrosDAO.guardarRegistro()
    → Hibernate → MySQL
    → Mensaje.jsp (éxito/error)
```

### Buscar Registro

```
Buscar.jsp 
    → [GET] ServletRegistro?accion=buscar
    → RegistrosDAO.buscarPorNumero()
    → Hibernate → MySQL
    → Consultar.jsp (datos del registro)
```

---

## 🔒 Validaciones

### Lado Servidor (Java)

- ✅ Todos los campos son obligatorios
- ✅ DNI: 8 dígitos + 1 letra (patrón regex)
- ✅ Longitud máxima de campos
- ✅ ID de entidad válido
- ✅ Manejo de excepciones Hibernate

### Lado Cliente (JavaScript)

- ✅ Validación de formato DNI en tiempo real
- ✅ Campos requeridos HTML5
- ✅ Patrones de validación

---

## 📝 Manejo de Excepciones

- **DAO**: Try-catch con HibernateException, transacciones con rollback
- **Servlet**: Try-catch general, redirección a Error.jsp
- **Validaciones**: Mensajes de error específicos

---

## 🧪 Testing

### Compilación

```bash
mvn clean compile
```

### Empaquetado WAR

```bash
mvn clean package
```

---

## 📄 Licencia

[MIT License](LICENSE)

---

## 👥 Equipo de Desarrollo

- **Nicolás** - Desarrollo completo SEDE ELECTRÓNICA GF

---

## 🔗 Enlaces

- [Repositorio GitHub](https://github.com/nicgrefer/ProyectoAccesoDatos)
- [Hibernate Documentation](https://hibernate.org/orm/documentation/)
- [Jakarta EE Specification](https://jakarta.ee/specifications/)

---

## 📞 Soporte

Para reportar errores o solicitar funcionalidades, por favor crear un [issue](https://github.com/nicgrefer/ProyectoAccesoDatos/issues) en GitHub.
