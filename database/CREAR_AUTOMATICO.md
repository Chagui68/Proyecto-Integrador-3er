# 🚀 Creación Automática de Base de Datos

## ¡Opción 1: Desde Windows (Recomendado)

### Ejecutar el script de creación:
```bash
crear_base_de_datos.bat
```

O ejecuta directamente:
```bash
mvn exec:java -Dexec.mainClass="com.hospital.sanrafael.database.DatabaseCreator"
```

## ¡Opción 2: Desde pgAdmin (Manual)

### Paso 1: Abrir pgAdmin
1. Busca "pgAdmin 4" en el menú Inicio
2. Ábrelo

### Paso 2: Ejecutar SQL
1. Click derecho en **"Databases"**
2. **"Query Tool"**
3. Copia y pega este código:

```sql
-- Crear la base de datos
CREATE DATABASE hospital_san_rafael 
WITH OWNER = postgres 
ENCODING = 'UTF8' 
LC_COLLATE = 'Spanish_Spain.1252' 
LC_CTYPE = 'Spanish_Spain.1252' 
CONNECTION LIMIT = -1;
```

4. Presiona **F5** o click en **"Execute"**

### Paso 3: Ejecutar el script
1. Click derecho en **"hospital_san_rafael"**
2. **"Query Tool"**
3. Abre el archivo `database/script.sql`
4. Copia todo el contenido
5. Pégalo en el Query Tool
6. Presiona **F5**

## ¡Opción 3: Automático desde la App

La aplicación ahora intenta crear la base de datos automáticamente al iniciar.

### Solo ejecuta:
```bash
mvn clean compile javafx:run
```

La aplicación:
1. Intentará crear la base de datos si no existe
2. Ejecutará el script de tablas
3. Iniciará la aplicación

## Verificar que se creó

### En pgAdmin:
1. Expande **"Servers"** → **"PostgreSQL 15"**
2. Expande **"Databases"**
3. Deberías ver: **🗄️ hospital_san_rafael**

### Con SQL:
```sql
SELECT datname FROM pg_database WHERE datname = 'hospital_san_rafael';
```

Si devuelve una fila, ¡la base de datos existe!

## Solución de Problemas

### Error: "No se puede conectar"
```bash
# Verificar que PostgreSQL esté corriendo
net start postgresql-x64-15
```

### Error: "Permission denied"
- Usa el usuario `postgres`
- Contraseña predeterminada: `postgres`

### Error: "Database already exists"
- ¡Es bueno! La base de datos ya existe
- Solo necesitas ejecutar el script de tablas

### Error: "Relation does not exist"
- Ejecuta el script `database/script.sql`
- O ejecuta: `mvn exec:java -Dexec.mainClass="com.hospital.sanrafael.database.DatabaseCreator"`

## Comandos Rápidos

### Crear base de datos
```bash
mvn exec:java -Dexec.mainClass="com.hospital.sanrafael.database.DatabaseCreator"
```

### Probar conexión
```bash
mvn exec:java -Dexec.mainClass="com.hospital.sanrafael.database.TestConnection"
```

### Ejecutar aplicación
```bash
mvn clean compile javafx:run
```

## ¿Qué hace DatabaseCreator?

1. ✅ Verifica si PostgreSQL está corriendo
2. ✅ Crea la base de datos `hospital_san_rafael` si no existe
3. ✅ Conecta a la base de datos
4. ✅ Ejecuta el script `database/script.sql`
5. ✅ Crea todas las tablas
6. ✅ Inserta datos de ejemplo
7. ✅ Verifica la conexión

## ¡Listo!

Después de ejecutar `DatabaseCreator`, la base de datos estará creada y lista para usarse.

**No necesitas usar pgAdmin manualmente** (a menos que quieras).

---

**Hospital San Rafael**  
*Creación automática de base de datos*
