# 🚀 Crear Base de Datos Manualmente - Hospital San Rafael

## ¡Sigue estos pasos!

### Paso 1: Abrir pgAdmin 4
1. Presiona la tecla `Windows`
2. Escribe "pgAdmin 4"
3. Haz clic para abrirlo

### Paso 2: Navegar a la base de datos
1. En el panel izquierdo, expande **"Servers"**
2. Expande **"PostgreSQL 15"** (o tu versión)
3. Si te pide contraseña, usa: `admin`

### Paso 3: Crear la base de datos
1. Haz **click derecho** en **"Databases"**
2. Selecciona **"Create"** → **"Database..."**
3. En la pestaña **"General"**:
   - **Database**: escribe `hospital_san_rafael`
4. En la pestaña **"Properties"**:
   - **Owner**: `postgres`
5. Haz clic en **"Save"**

### Paso 4: Ejecutar el script SQL
1. En el panel izquierdo, busca `hospital_san_rafael`
2. Haz **click derecho** sobre ella
3. Selecciona **"Query Tool"**
4. Abre el archivo: `database/script.sql`
5. **Copia TODO** el contenido
6. **Pégalo** en el Query Tool
7. Presiona **F5** o el botón **"Execute"** (▶️)

### Paso 5: Verificar
Deberías ver mensajes como:
- `CREATE TABLE`
- `INSERT 0 5`
- etc.

Si ves errores, ignora los que digan "ya existe".

### Paso 6: ¡Listo!
Ahora puedes ejecutar la aplicación JavaFX.

---

## Verificar que todo está bien

En el Query Tool, ejecuta:
```sql
SELECT * FROM persona LIMIT 5;
SELECT * FROM estudiante LIMIT 5;
SELECT * FROM doctor LIMIT 5;
```

Si ves datos, ¡todo está perfecto!

---

## Ejecutar la Aplicación

```bash
mvn clean compile javafx:run
```

La aplicación ahora usará la base de datos PostgreSQL.

---

**Hospital San Rafael**  
*PostgreSQL + pgAdmin 4*
