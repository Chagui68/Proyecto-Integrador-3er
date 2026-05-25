# Cómo Probar los Dashboards

## Paso 1: Compilar
```bash
mvn clean compile
```

## Paso 2: Ejecutar
```bash
mvn javafx:run
```

## Paso 3: Iniciar Sesión

### Para ver Dashboard de Estudiante:
1. En la login, seleccionar rol: **Estudiante**
2. Username: `estudiante1`
3. Password: `estudiante123`
4. Click en "Iniciar Sesión"
5. **Debe mostrar**: Dashboard de Estudiante con sidebar verde que dice:
   - Modificar Datos
   - Mi Horario
   - Notificaciones

### Para ver Dashboard de Doctor:
1. En el login, seleccionar rol: **Doctor**
2. Username: `doctor1`
3. Password: `doctor123`
4. Click en "Iniciar Sesión"
5. **Debe mostrar**: Dashboard de Doctor con sidebar azul que dice:
   - Modificar Datos
   - Mi Horario
   - Notificaciones
   - Enviar Notificación
   - Reportes

### Para ver Menú de Administrador:
1. En el login, seleccionar rol: **Administrador**
2. Username: `admin`
3. Password: `admin123`
4. Click en "Iniciar Sesión"
5. **Debe mostrar**: Menú principal de administradores

## Flujo Correcto

```
LOGIN
  ├─ Estudiante → Dashboard Estudiante (sidebar verde, 3 opciones)
  ├─ Doctor → Dashboard Doctor (sidebar azul, 5 opciones)
  └─ Administrador → Menú Principal (sidebar normal)
```

## Si no ves los cambios:

1. **Compila nuevamente**: `mvn clean compile`
2. **Ejecuta**: `mvn javafx:run`
3. **Verifica credenciales**: Asegúrate de usar las correctas
4. **Revisa la consola**: No debe haber errores

## Credenciales

| Rol | Username | Password | Dashboard Esperado |
|-----|----------|----------|-------------------|
| Estudiante | estudiante1 | estudiante123 | Dashboard Estudiante |
| Doctor | doctor1 | doctor123 | Dashboard Doctor |
| Administrador | admin | admin123 | Menú Principal |
