# 🚀 Para PostgreSQL 13 y 18

Tienes dos versiones de PostgreSQL instaladas. Sigue estos pasos:

## Opción 1: Usar PostgreSQL 13 (Recomendado)

### Paso 1: Abrir pgAdmin para PostgreSQL 13
1. Presiona `Windows + R`
2. Escribe: `pgAdmin 4` (se abrirá el de la versión 13 por defecto)
3. O busca en Inicio: "pgAdmin 4"

### Paso 2: Crear la base de datos
1. En el panel izquierdo, expande **"Servers"**
2. Expande **"PostgreSQL 13"**
3. Click derecho en **"Databases"**
4. **"Create"** → **"Database"**
5. Nombre: `hospital_san_rafael`
6. Click **"Save"**

### Paso 3: Ejecutar el script
1. Click derecho en `hospital_san_rafael`
2. **"Query Tool"**
3. Copia TODO el contenido de `database/script.sql`
4. Pégalo en el Query Tool
5. Presiona **F5**

### Paso 4: Verificar
```sql
SELECT * FROM persona;
```

---

## Opción 2: Usar PostgreSQL 18

### Paso 1: Abrir pgAdmin para PostgreSQL 18
1. Busca en Inicio: "pgAdmin 4 - PostgreSQL 18"
2. Ábrelo

### Paso 2: Segir los mismos pasos anteriores
(Mismo proceso que PostgreSQL 13)

---

## ¿Cuál versión usar?

**Recomendación:** Usa **PostgreSQL 13** porque:
- ✅ Es más estable
- ✅ Tiene mejor compatibilidad
- ✅ Consume menos recursos

**Si quieres usar la 18:**
- Es más reciente
- Tiene características nuevas
- Puede tener bugs no resueltos

---

## Verificar qué versión está corriendo

En pgAdmin, ejecuta:
```sql
SELECT version();
```

Te dirá si es PostgreSQL 13 o 18.

---

## Crear la base de datos - Paso a paso visual

````
📁 pgAdmin 4
└── 🗄️ Servers (1)
    └── 🗄️ PostgreSQL 13  ← O PostgreSQL 18
        ├── 🗄️ Databases (3)
        │   ├── 🗄️ postgres
        │   ├── 🗄️ template1
        │   └── 🗄️ hospital_san_rafael  ← ¡Nueva!
        └── 🔌 Group Servers
````

---

## Ejecutar la aplicación

Después de crear la base de datos:

```bash
mvn clean compile javafx:run
```

La aplicación se conectará automáticamente.

---

## Solución de Problemas

### Error: "No se puede conectar"
- Verifica que PostgreSQL 13 esté corriendo
- Abre "Services" (Servicios de Windows)
- Busca "postgresql-x64-13"
- Debe estar "Running"

### Error: "Contraseña incorrecta"
- La contraseña por defecto es: `admin`
- Si la cambiaste, actualiza `database.properties`

### Error: "La base de datos no existe"
- Ejecuta los pasos de creación manualmente en pgAdmin
- O usa el script SQL

---

**¡Importante!** Solo necesitas crear la base de datos UNA VEZ.
Después, la aplicación funcionará siempre.

---

**Hospital San Rafael**  
*Compatible con PostgreSQL 13 y 18*
