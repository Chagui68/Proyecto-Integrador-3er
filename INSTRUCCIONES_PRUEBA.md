# Instrucciones de Prueba - Dashboards

## ✅ Cambios Realizados

### 1. Dashboard de Estudiantes
Al iniciar sesión como estudiante:
- **Acceso**: Username: `estudiante1`, Password: `estudiante123`
- **Sidebar con 3 opciones**:
  1. **Modificar Datos** - Formulario para editar datos personales
  2. **Mi Horario** - Visualización del horario actual
  3. **Notificaciones** - Ver notificaciones del sistema

### 2. Dashboard de Doctores  
Al iniciar sesión como doctor:
- **Acceso**: Username: `doctor1`, Password: `doctor123`
- **Sidebar con 5 opciones**:
  1. **Modificar Datos** - Formulario para editar datos personales
  2. **Mi Horario** - Horario de trabajo del doctor
  3. **Notificaciones** - Ver notificaciones
  4. **Enviar Notificación** - Enviar notificación por ID a estudiante
  5. **Reportes** - Generar reportes (Excel/PDF)

## 🚀 Cómo Probar

### 1. Ejecutar la aplicación
```bash
mvn clean compile
mvn javafx:run
```

### 2. Probar Dashboard de Estudiante
1. En el login, seleccionar rol: "Estudiante"
2. Username: `estudiante1`
3. Password: `estudiante123`
4. Presionar "Iniciar Sesión"
5. Debería mostrar el dashboard con las 3 opciones en el sidebar

### 3. Probar Dashboard de Doctor
1. En el login, seleccionar rol: "Doctor"
2. Username: `doctor1`
3. Password: `doctor123`
4. Presionar "Iniciar Sesión"
5. Debería mostrar el dashboard con las 5 opciones en el sidebar

### 4. Probar Administrador
1. En el login, seleccionar rol: "Administrador"
2. Username: `admin`
3. Password: `admin123`
4. Presionar "Iniciar Sesión"
5. Debería mostrar el menú principal de administradores

## 📋 Flujo de Navegación

### Estudiante
```
Login → Dashboard Estudiante
  ├─ Modificar Datos (formulario)
  ├─ Mi Horario (vista de horario)
  └─ Notificaciones (centro de notificaciones)
```

### Doctor
```
Login → Dashboard Doctor
  ├─ Modificar Datos (formulario)
  ├─ Mi Horario (vista de horario)
  ├─ Notificaciones (centro de notificaciones)
  ├─ Enviar Notificación (por ID)
  └─ Reportes (generador de reportes)
```

## 🔧 Solución de Problemas

### No aparece el dashboard
- Verificar que el login sea correcto
- Revisar que el rol coincida con las credenciales
- Compilar nuevamente: `mvn clean compile`

### Error de navegación
- Verificar MainController tenga los casos "student-dashboard" y "doctor-dashboard"
- Revisar que los controladores estén bien instanciados

## 📊 Credenciales de Prueba

| Rol | Username | Password | Dashboard |
|-----|----------|----------|-----------|
| Administrador | admin | admin123 | Menú Principal |
| Doctor | doctor1 | doctor123 | Dashboard Doctor |
| Estudiante | estudiante1 | estudiante123 | Dashboard Estudiante |

---
**Fecha**: 2026-05-25
**Estado**: ✅ Listo para probar
