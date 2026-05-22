# 📋 Pasos para Conectar PostgreSQL - Hospital San Rafael

## Paso 1: Instalar PostgreSQL (5 minutos)

### Descargar
1. Ve a: https://www.postgresql.org/download/windows/
2. Haz clic en "Download the installer"
3. Descarga la versión para Windows (ej: PostgreSQL 15.x)

### Instalar
1. Ejecuta el instalador descargado
2. Acepta los términos → Next
3. Directorio: `C:\Program Files\PostgreSQL\15` → Next
4. Componentes: Deja todo marcado → Next
5. **Contraseña**: `postgres` (¡Anótala!)
6. Puerto: `5432` → Next
7. Locale: Default → Next
8. Install → Espera a que termine

## Paso 2: Abrir pgAdmin 4 (2 minutos)

1. Busca en Inicio: "pgAdmin 4"
2. O navega: `Inicio → PostgreSQL 15 → pgAdmin 4`
3. Se abrirá en tu navegador: http://localhost:5050

## Paso 3: Crear la Base de Datos (3 minutos)

### Opción A: Interfaz Gráfica (Recomendado)
1. En pgAdmin, expande "Servers"
2. Click derecho en "Databases"
3. Selecciona "Create" → "Database"
4. Nombre: `hospital_san_rafael`
5. Owner: `postgres`
6. Click en "Save"

### Opción B: Con SQL
1. Click derecho en "Databases" → "Query Tool"
2. Pega este código:
```sql
CREATE DATABASE hospital_san_rafael
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Spain.1252'
    LC_CTYPE = 'Spanish_Spain.1252'
    CONNECTION LIMIT = -1;
```
3. Presiona F5 o click en "Execute"

## Paso 4: Ejecutar Script de Tablas (2 minutos)

1. Abre el archivo: `database/script.sql`
2. Copia TODO el contenido
3. En pgAdmin:
   - Click derecho en `hospital_san_rafael`
   - "Query Tool"
   - Pega el script
   - Click en "Execute" (F5)
4. Deberías ver mensajes de éxito

### Verificar tablas creadas
```sql
SELECT tablename FROM pg_tables WHERE schemaname = 'public';
```

## Paso 5: Configurar la Aplicación (1 minuto)

1. Abre: `src/main/resources/database.properties`
2. Verifica que esté así:
```properties
db.host=localhost
db.port=5432
db.name=hospital_san_rafael
db.username=postgres
db.password=postgres
```
3. Si cambiaste la contraseña, actualiza `db.password`

## Paso 6: Probar la Conexión (1 minuto)

### Desde la aplicación
Ejecuta la clase `TestConnection.java` o inicia la aplicación principal.

### Desde pgAdmin
```sql
SELECT * FROM persona LIMIT 5;
SELECT * FROM estudiante LIMIT 5;
SELECT * FROM doctor LIMIT 5;
```

## ✅ Verificación Final

Deberías ver:
- ✅ PostgreSQL corriendo en puerto 5432
- ✅ Base de datos `hospital_san_rafael` creada
- ✅ 8 tablas creadas (persona, estudiante, doctor, materia, horario, etc.)
- ✅ Datos de ejemplo insertados
- ✅ Vistas creadas

## 🚀 Ejecutar la Aplicación

1. Abre terminal en la carpeta del proyecto
2. Ejecuta:
```bash
mvn clean compile javafx:run
```
3. La aplicación debería iniciar sin errores
4. Prueba las funcionalidades

## 📊 Ver Datos en la Base de Datos

### Estudiantes por carrera
```sql
SELECT e.carrera, COUNT(*) as cantidad
FROM estudiante e
GROUP BY e.carrera
ORDER BY cantidad DESC;
```

### Doctores por especialidad
```sql
SELECT d.especialidad, COUNT(*) as cantidad
FROM doctor d
GROUP BY d.especialidad;
```

### Horarios de clases
```sql
SELECT 
    h.dia_semana,
    h.hora_inicio,
    h.hora_fin,
    m.nombre as materia
FROM horario h
JOIN materia m ON h.id_materia = m.codigo
WHERE h.tipo_horario = 'CLASE'
ORDER BY h.dia_semana, h.hora_inicio;
```

## ❌ Solución de Problemas

### Error: "No se puede conectar al servidor"
```bash
# Verificar si PostgreSQL está corriendo
net start postgresql-x64-15

# Si no está instalado el servicio
# Reinstalar PostgreSQL
```

### Error: "La base de datos no existe"
```sql
-- Crear la base de datos
CREATE DATABASE hospital_san_rafael;
```

### Error: "La relación 'tabla' no existe"
```sql
-- Ejecutar el script nuevamente
-- database/script.sql
```

### Error: "Contraseña incorrecta"
1. Abre pgAdmin
2. Click derecho en "PostgreSQL 15"
3. "Properties" → "Connection"
4. Ingresa la contraseña correcta
5. Actualiza `database.properties`

## 📞 Comandos Útiles

### Reiniciar PostgreSQL
```bash
net stop postgresql-x64-15
net start postgresql-x64-15
```

### Ver logs de PostgreSQL
```
C:\Program Files\PostgreSQL\15\data\log
```

### Respaldar base de datos
```bash
pg_dump -U postgres hospital_san_rafael > backup.sql
```

### Restaurar respaldo
```bash
psql -U postgres -d hospital_san_rafael < backup.sql
```

## 📚 Recursos Adicionales

- **Script SQL**: `database/script.sql`
- **Documentación**: `database/README.md`
- **Guía PostgreSQL**: `database/INSTALACION_POSTGRESQL.md`

---

**¡Listo! Tu base de datos está configurada y lista para usarse.**

**Hospital San Rafael**  
*PostgreSQL 15 + pgAdmin 4*
