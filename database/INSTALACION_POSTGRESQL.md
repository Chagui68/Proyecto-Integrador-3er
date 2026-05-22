# Guía de Instalación y Configuración - PostgreSQL

## Paso 1: Descargar e Instalar PostgreSQL

### 1.1 Descargar
1. Ve a: https://www.postgresql.org/download/windows/
2. Descarga la versión más reciente (ej: PostgreSQL 15.x)
3. Ejecuta el instalador

### 1.2 Instalación
Durante la instalación:
- **Directorio de instalación**: `C:\Program Files\PostgreSQL\15`
- **Puerto**: `5432` (predeterminado)
- **Contraseña de postgres**: `postgres` (guárdala)
- **Locale**: Default (UTF-8)

## Paso 2: Instalar pgAdmin 4

pgAdmin se instala automáticamente con PostgreSQL. Para abrirlo:
```
Inicio → PostgreSQL 15 → pgAdmin 4
```

O navega a: http://localhost:5050/pgadmin4

## Paso 3: Crear la Base de Datos

### 3.1 Conectarse a PostgreSQL
1. Abre pgAdmin 4
2. Expande "Servers" → "PostgreSQL 15"
3. Ingresa la contraseña: `postgres`
4. Marca "Save password" si lo deseas

### 3.2 Crear Base de Datos
```sql
-- Opción 1: Usando pgAdmin (Interfaz gráfica)
-- Click derecho en "Databases" → "Create" → "Database"
-- Nombre: hospital_san_rafael
-- Owner: postgres

-- Opción 2: Usando SQL
CREATE DATABASE hospital_san_rafael
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Spain.1252'
    LC_CTYPE = 'Spanish_Spain.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
```

## Paso 4: Ejecutar Script de Creación de Tablas

### 4.1 Abrir Query Tool
1. En pgAdmin, click derecho en `hospital_san_rafael`
2. Selecciona "Query Tool"
3. Copia y pega el contenido de `database/script.sql`
4. Presiona F5 o click en "Execute"

### 4.2 Verificar
Ejecuta:
```sql
SELECT * FROM persona LIMIT 5;
SELECT * FROM estudiante LIMIT 5;
SELECT * FROM doctor LIMIT 5;
```

## Paso 5: Configurar Conexión en la Aplicación

### 5.1 Editar database.properties
Ubicado en: `src/main/resources/database.properties`

```properties
db.host=localhost
db.port=5432
db.name=hospital_san_rafael
db.username=postgres
db.password=TU_CONTRASEÑA
```

### 5.2 Verificar Conexión
La aplicación probará la conexión automáticamente al iniciar.

## Paso 6: Comandos Útiles

### Ver todas las tablas
```sql
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public'
ORDER BY table_name;
```

### Ver estudiantes
```sql
SELECT * FROM v_estudiantes_carrera;
```

### Ver doctores
```sql
SELECT * FROM v_doctores_especialidad;
```

### Ver horarios
```sql
SELECT * FROM v_horario_estudiantes;
```

### Contar registros
```sql
SELECT 
    (SELECT COUNT(*) FROM persona WHERE tipo_persona = 'ESTUDIANTE') AS estudiantes,
    (SELECT COUNT(*) FROM persona WHERE tipo_persona = 'DOCTOR') AS doctores,
    (SELECT COUNT(*) FROM materia) AS materias,
    (SELECT COUNT(*) FROM horario) AS horarios;
```

## Solución de Problemas

### Error: "No se pudo conectar al servidor"
- Verifica que PostgreSQL esté corriendo
- Revisa el puerto (por defecto: 5432)
- Verifica firewall

### Error: "La base de datos no existe"
- Crea la base de datos `hospital_san_rafael`
- Verifica el nombre en `database.properties`

### Error: "Contraseña incorrecta"
- Restablece la contraseña de postgres
- Actualiza `database.properties`

### PostgreSQL no inicia
```
# En Windows (PowerShell como Admin)
net start postgresql-x64-15

# Detener
net stop postgresql-x64-15
```

## Comandos Rápidos pgAdmin

### Ejecutar consulta
- F5 o botón "Play"

### Guardar consulta
- Ctrl + S

### Exportar resultados
- Click derecho en resultados → "Exportar"

### Importar datos
- Click derecho en tabla → "Import/Export Data"

## Respaldar Base de Datos

### Crear Backup
```bash
pg_dump -U postgres hospital_san_rafael > backup.sql
```

### Restaurar Backup
```bash
psql -U postgres -d hospital_san_rafael < backup.sql
```

## Enlaces Útiles
- Documentación oficial: https://www.postgresql.org/docs/
- pgAdmin docs: https://www.pgadmin.org/docs/
- Comandos SQL: https://www.postgresql.org/docs/current/sql.html

---

**Hospital San Rafael**  
*Base de Datos: PostgreSQL 15*  
*Gestor: pgAdmin 4*
