# 🏥 Hospital San Rafael - Base de Datos PostgreSQL

## ✅ ¿Qué se ha creado?

### Archivos Creados

1. **`script.sql`** - Script completo de creación de base de datos
   - 8 tablas principales
   - 7 índices para rendimiento
   - 3 vistas predefinidas
   - Datos de ejemplo precargados

2. **`database.properties`** - Configuración de conexión
   - Host, puerto, nombre de BD
   - Credenciales
   - URL de conexión JDBC

3. **`DatabaseConnection.java`** - Clase de conexión
   - Singleton pattern
   - Manejo de propiedades
   - Test de conexión

4. **`PostgreEstudianteDAO.java`** - DAO para estudiantes
   - CRUD completo (Create, Read, Update, Delete)
   - Mapeo de ResultSet a objetos
   - Transacciones con PostgreSQL

5. **`TestConnection.java`** - Clase de prueba
   - Verifica conexión
   - Muestra estadísticas
   - Lista datos de ejemplo

6. **Documentación completa**:
   - `INSTALACION_POSTGRESQL.md` - Guía de instalación
   - `README.md` - Estructura de la BD
   - `PASOS_INSTALACION.md` - Pasos paso a paso
   - `RESUMEN.md` - Este archivo

## 📊 Estructura de la Base de Datos

### Tablas (8)
1. **persona** - Información común
2. **estudiante** - Datos de estudiantes
3. **doctor** - Datos de doctores
4. **materia** - Catálogo académico
5. **horario** - Horarios de clases/atención
6. **estudiante_materia** - Inscripciones
7. **doctor_estudiante** - Tutorías
8. **registro_hospitalario** - Control de acceso

### Vistas (3)
- `v_estudiantes_carrera`
- `v_doctores_especialidad`
- `v_horario_estudiantes`

### Datos Precargados
- 5 estudiantes
- 5 doctores
- 10 materias
- Horarios de clase
- Inscripciones
- Tutorías

## 🚀 Pasos para Usar

### 1. Instalar PostgreSQL
- Descargar de: https://www.postgresql.org/download/windows/
- Instalar con configuración predeterminada
- Contraseña: `postgres`

### 2. Crear Base de Datos
```sql
CREATE DATABASE hospital_san_rafael;
```

### 3. Ejecutar Script
- Abrir pgAdmin 4
- Click derecho en `hospital_san_rafael` → Query Tool
- Ejecutar `database/script.sql`

### 4. Configurar Aplicación
- Editar `src/main/resources/database.properties`
- Verificar credenciales

### 5. Probar Conexión
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.hospital.sanrafael.database.TestConnection"
```

### 6. Ejecutar Aplicación
```bash
mvn clean compile javafx:run
```

## 🔧 Dependencias Agregadas

En `pom.xml`:
```xml
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <version>42.6.0</version>
</dependency>
```

## 📁 Archivos del Proyecto

```
HospitalSanRafael/
├── database/
│   ├── script.sql                    # Script principal
│   ├── database.properties           # Configuración
│   ├── INSTALACION_POSTGRESQL.md     # Guía instalación
│   ├── README.md                     # Documentación
│   ├── PASOS_INSTALACION.md          # Pasos
│   └── RESUMEN.md                    # Este archivo
├── src/main/java/.../database/
│   ├── DatabaseConnection.java       # Conexión BD
│   ├── PostgreEstudianteDAO.java     # DAO Estudiantes
│   └── TestConnection.java           # Test
├── src/main/resources/
│   └── database.properties           # Props (copia)
└── pom.xml                           # Con driver PostgreSQL
```

## 🎯 Características

### ✅ Completado
- [x] Script SQL completo
- [x] Conexión JDBC configurada
- [x] DAO para estudiantes
- [x] Datos de ejemplo
- [x] Documentación completa
- [x] Test de conexión
- [x] Propiedades configurables

### 🔄 Pendiente (Opcional)
- [ ] DAO para Doctores
- [ ] DAO para Materias
- [ ] DAO para Horarios
- [ ] Migrar servicios existentes
- [ ] Validaciones adicionales
- [ ] Pool de conexiones (HikariCP)

## 📞 Comandos Rápidos

### Probar conexión
```bash
mvn exec:java -Dexec.mainClass="com.hospital.sanrafael.database.TestConnection"
```

### Ejecutar aplicación
```bash
mvn javafx:run
```

### Ver datos
```sql
SELECT * FROM v_estudiantes_carrera;
SELECT * FROM v_doctores_especialidad;
SELECT * FROM v_horario_estudiantes;
```

## 🔗 Enlaces

- **PostgreSQL**: https://www.postgresql.org/
- **pgAdmin**: https://www.pgadmin.org/
- **JDBC Driver**: https://jdbc.postgresql.org/
- **Documentación**: https://www.postgresql.org/docs/

---

**Hospital San Rafael**  
*PostgreSQL 15 | pgAdmin 4 | JDBC*  
*¡Base de datos lista para producción!*
