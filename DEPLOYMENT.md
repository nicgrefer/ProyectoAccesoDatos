# SEDE ELECTRÓNICA GF - Deployment Instructions

## Prerequisites

Before deploying the application, ensure you have the following installed:

1. **JDK 17 or higher**
   ```bash
   java -version
   ```

2. **Apache Maven 3.6+**
   ```bash
   mvn -version
   ```

3. **MySQL 8.0+**
   ```bash
   mysql --version
   ```

4. **Apache Tomcat 10+**
   - Download from: https://tomcat.apache.org/download-10.cgi

---

## Step 1: Setup MySQL Database

1. **Start MySQL Server**
   ```bash
   sudo systemctl start mysql
   # or on Windows:
   # net start MySQL80
   ```

2. **Login to MySQL**
   ```bash
   mysql -u root -p
   ```

3. **Create Database and Tables**
   ```sql
   source /path/to/ProyectoAccesoDatos/database/sede.sql
   ```
   
   Or manually execute:
   ```sql
   CREATE DATABASE IF NOT EXISTS sede CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   USE sede;
   
   CREATE TABLE IF NOT EXISTS entidad (
       id_entidad INT PRIMARY KEY AUTO_INCREMENT,
       nombre_entidad VARCHAR(200) NOT NULL
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
   
   CREATE TABLE IF NOT EXISTS registros (
       num_registro VARCHAR(50) PRIMARY KEY,
       dni_solicitante VARCHAR(10) NOT NULL,
       nombre_solicitante VARCHAR(100) NOT NULL,
       apellidos_solicitante VARCHAR(150) NOT NULL,
       tramite VARCHAR(200) NOT NULL,
       id_entidad INT NOT NULL,
       fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (id_entidad) REFERENCES entidad(id_entidad)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
   
   INSERT INTO entidad (nombre_entidad) VALUES 
   ('INSPECCIÓN EDUCATIVA INFANTIL'),
   ('INSPECCIÓN EDUCATIVA PRIMARIA'),
   ('INSPECCIÓN EDUCATIVA SECUNDARIA'),
   ('INSPECCIÓN EDUCATIVA BACHILLERATO'),
   ('INSPECCIÓN EDUCATIVA FP'),
   ('CONSEJERIA EDUCACION'),
   ('MINISTERIO DE EDUCACIÓN Y CIENCIA');
   ```

4. **Verify Data**
   ```sql
   SELECT * FROM entidad;
   ```
   You should see 7 educational entities.

---

## Step 2: Configure Database Connection

1. **Edit Hibernate Configuration** (if needed)
   
   File: `src/main/resources/hibernate.cfg.xml`
   
   Update these properties if your MySQL has different credentials:
   ```xml
   <property name="hibernate.connection.username">your_username</property>
   <property name="hibernate.connection.password">your_password</property>
   ```

---

## Step 3: Build the Application

1. **Navigate to project directory**
   ```bash
   cd /path/to/ProyectoAccesoDatos
   ```

2. **Clean and Build**
   ```bash
   mvn clean package
   ```
   
   This will:
   - Compile all Java source files
   - Run tests (if any)
   - Package the application as `SedeElectronicaGF.war`
   
   Expected output:
   ```
   [INFO] BUILD SUCCESS
   [INFO] Total time: ~8 seconds
   ```

3. **Verify WAR file**
   ```bash
   ls -lh target/SedeElectronicaGF.war
   ```
   Should show a file around 23MB.

---

## Step 4: Deploy to Tomcat

### Method 1: Manual Deployment

1. **Copy WAR file to Tomcat**
   ```bash
   cp target/SedeElectronicaGF.war $TOMCAT_HOME/webapps/
   ```
   
   Where `$TOMCAT_HOME` is your Tomcat installation directory, e.g.:
   - Linux: `/opt/tomcat/` or `/usr/local/tomcat/`
   - Windows: `C:\Program Files\Apache Software Foundation\Tomcat 10.0\`

2. **Start Tomcat**
   ```bash
   $TOMCAT_HOME/bin/startup.sh
   # or on Windows:
   # %TOMCAT_HOME%\bin\startup.bat
   ```

3. **Check logs**
   ```bash
   tail -f $TOMCAT_HOME/logs/catalina.out
   ```
   
   Look for:
   ```
   SessionFactory creada exitosamente
   ServletRegistro inicializado
   ```

### Method 2: Tomcat Manager (GUI)

1. Start Tomcat
2. Navigate to: `http://localhost:8080/manager/html`
3. Login with Tomcat credentials
4. In "WAR file to deploy" section, browse and select `target/SedeElectronicaGF.war`
5. Click "Deploy"

---

## Step 5: Access the Application

1. **Open browser and navigate to:**
   ```
   http://localhost:8080/SedeElectronicaGF/
   ```
   
   Or directly:
   ```
   http://localhost:8080/SedeElectronicaGF/Registro.jsp
   ```

2. **You should see the registration form**
   - Green header with "SEDE ELECTRÓNICA GF"
   - Form fields for DNI, Nombre, Apellidos, Tramite, and Entidad

---

## Step 6: Test the Application

### Test 1: Create a Registration

1. Fill in the form:
   - **DNI**: 12345678A
   - **Nombre**: Juan
   - **Apellidos**: García López
   - **Tramite**: Solicitud de certificado académico
   - **Entidad**: Select any from the dropdown

2. Click "Grabar"

3. **Expected Result:**
   - Success message: "La grabación se ha realizado correctamente"
   - Registration number displayed: e.g., "REG_000001"
   - Date and time shown

### Test 2: Search for a Registration

1. Click "Consulta Registro" button (from success page)
   
   Or navigate to: `http://localhost:8080/SedeElectronicaGF/Buscar.jsp`

2. Enter the registration number: `REG_000001`

3. Click "Buscar"

4. **Expected Result:**
   - Table showing all registration details
   - DNI, Name, Apellidos, Tramite, Entidad, and Date

### Test 3: Search Non-existent Registration

1. Go to Buscar.jsp
2. Enter: `REG_999999`
3. Click "Buscar"
4. **Expected Result:**
   - Message: "El trámite de registro no existe"

---

## Troubleshooting

### Problem: "SessionFactory creation failed"

**Solution:**
- Check MySQL is running: `sudo systemctl status mysql`
- Verify database exists: `SHOW DATABASES LIKE 'sede';`
- Check username/password in `hibernate.cfg.xml`
- Ensure MySQL connector JAR is in the WAR file

### Problem: "404 Not Found"

**Solution:**
- Verify WAR file is deployed: Check `$TOMCAT_HOME/webapps/SedeElectronicaGF/` exists
- Check Tomcat logs: `tail -f $TOMCAT_HOME/logs/catalina.out`
- Restart Tomcat

### Problem: "DNI validation error"

**Solution:**
- DNI must be exactly 8 digits followed by 1 letter
- Example: `12345678A` (not `1234567A` or `123456789`)
- Letter is case-insensitive (auto-converted to uppercase)

### Problem: "Entidad dropdown is empty"

**Solution:**
- Check database has initial data:
  ```sql
  USE sede;
  SELECT * FROM entidad;
  ```
- If empty, run the INSERT statements from `database/sede.sql`

---

## Application URLs

| Page | URL | Description |
|------|-----|-------------|
| Home | http://localhost:8080/SedeElectronicaGF/ | Redirects to Registro.jsp |
| Registration Form | http://localhost:8080/SedeElectronicaGF/Registro.jsp | Create new registration |
| Search Form | http://localhost:8080/SedeElectronicaGF/Buscar.jsp | Search by number |

---

## Database Verification

To check registrations in the database:

```sql
USE sede;

-- View all registrations
SELECT * FROM registros;

-- View specific registration
SELECT r.*, e.nombre_entidad 
FROM registros r 
JOIN entidad e ON r.id_entidad = e.id_entidad 
WHERE num_registro = 'REG_000001';

-- Count registrations
SELECT COUNT(*) as total FROM registros;
```

---

## Production Deployment Checklist

Before deploying to production:

- [ ] Change `hibernate.hbm2ddl.auto` from `update` to `validate` in hibernate.cfg.xml
- [ ] Set `hibernate.show_sql` to `false`
- [ ] Configure proper MySQL user (not root)
- [ ] Enable SSL for database connection
- [ ] Configure connection pooling (e.g., HikariCP)
- [ ] Set up proper logging (Log4j or SLF4J)
- [ ] Enable HTTPS on Tomcat
- [ ] Configure session timeout appropriately
- [ ] Set up database backups
- [ ] Review and harden security settings

---

## Additional Notes

- **Session Management**: Sessions expire after 30 minutes of inactivity
- **Error Handling**: All errors redirect to Error.jsp with detailed messages
- **Registration Numbers**: Auto-generated in format REG_XXXXXX (sequential)
- **Date Format**: Stored as TIMESTAMP in database, displayed in default locale format

---

## Support

For issues or questions:
- Check the application logs: `$TOMCAT_HOME/logs/catalina.out`
- Review Hibernate logs for database issues
- Consult `SEDE_README.md` for architecture details

---

**Deployment Date:** January 21, 2026  
**Version:** 1.0-SNAPSHOT  
**Java Version:** 17  
**Hibernate Version:** 6.4.1  
**MySQL Version:** 8.0+
