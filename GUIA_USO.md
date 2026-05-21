# Guía de Uso - Hospital San Rafael

## Inicio del Sistema

Al iniciar la aplicación, verá el menú principal con las siguientes opciones:

1. **Gestión de Estudiantes** - Registro y consulta de estudiantes
2. **Gestión de Doctores** - Administración del personal médico
3. **Materias** - Catálogo académico
4. **Horarios** - Consulta de horarios
5. **Registros** - Control de ingreso al hospital

---

## 1. Gestión de Estudiantes

### Registrar Nuevo Estudiante
1. Haga clic en "Nuevo"
2. Complete los campos:
   - **ID**: Código único del estudiante (ej: E001)
   - **Nombre**: Nombre de pila
   - **Apellido**: Apellido paterno
   - **Email**: Correo institucional
   - **Teléfono**: Número de contacto
   - **Fecha Nacimiento**: Formato DD/MM/AAAA
   - **Género**: M/F
   - **Dirección**: Dirección completa
   - **Carrera**: Programa académico
   - **Semestre**: Número de semestre (1-10)
   - **Turno**: Mañana/Tarde/Noche

3. Presione "Guardar"

### Consultar Estudiante
- Use la tabla para buscar visualmente
- Seleccione un estudiante para ver su información completa

### Actualizar Estudiante
1. Seleccione el estudiante en la tabla
2. Modifique los campos necesarios
3. Presione "Actualizar"

### Eliminar Estudiante
1. Seleccione el estudiante
2. Presione "Eliminar"
3. Confirme la acción

---

## 2. Gestión de Doctores

### Registrar Nuevo Doctor
1. Haga clic en "Nuevo"
2. Complete la información:
   - **ID**: Código único (ej: D001)
   - **Nombre y Apellido**: Datos personales
   - **Especialidad**: Área médica
   - **N° Colegiado**: Licencia profesional
   - **Área Asignada**: Pabellón o departamento
   - **Años Experiencia**: Experiencia profesional

3. Presione "Guardar"

### Funcionalidades Adicionales
- **Asignar estudiantes**: Use el ID del estudiante para asignar tutorías
- **Horarios**: Configure horarios de atención
- **Consultar por especialidad**: Filtre doctores por especialidad

---

## 3. Materias

### Registrar Nueva Materia
1. Haga clic en "Nueva"
2. Complete:
   - **Código**: Identificador único (ej: MED101)
   - **Nombre**: Nombre de la asignatura
   - **Descripción**: Breve descripción
   - **Créditos**: Valor académico
   - **Semestre Recomendado**: Semestre sugerido
   - **Profesor**: Docente responsable
   - **Aula**: Ubicación física

3. Presione "Guardar"

### Gestionar Materias
- **Actualizar**: Modifique información existente
- **Eliminar**: Retire materias del catálogo
- **Consultar**: Filtre por semestre o profesor

---

## 4. Horarios

### Consultar Horario de Estudiante
1. Ingrese el ID del estudiante
2. Presione "Buscar"
3. Visualice:
   - Información personal
   - Materias inscritas
   - Horario semanal detallado
   - Próximas clases

### Información Mostrada
- Nombre completo
- Carrera y semestre
- Turno
- Lista de materias con códigos
- Horario por días y horas
- Profesores y aulas

---

## 5. Registros

### Registrar Ingreso
1. Seleccione el tipo: "Estudiante" o "Doctor"
2. Ingrese el ID
3. Presione "Registrar Ingreso"
4. El sistema mostrará:
   - Hora de registro
   - Fecha
   - Información completa
   - Estado actual

### Resumen Diario
El sistema muestra automáticamente:
- Total de estudiantes registrados
- Total de doctores registrados
- Distribución por semestres
- Distribución por especialidades

---

## Consejos de Uso

### Atajos y Trucos
- Use IDs consecutivos para facilitar la búsqueda
- Mantenga un estándar en los formatos de fecha
- Revise los datos antes de guardar
- Use la función de búsqueda para evitar duplicados

### Mejores Prácticas
1. **Registro oportuno**: Registre estudiantes y doctores al inicio del período
2. **Actualización constante**: Mantenga la información actualizada
3. **Respaldos**: Realice copias de seguridad de la carpeta `data/`
4. **Verificación**: Revise periódicamente los registros

### Solución de Problemas Comunes

**No se muestra información**
- Verifique que existan datos registrados
- Presione "Actualizar" o "Refrescar"

**Error al guardar**
- Revise que todos los campos obligatorios estén completos
- Verifique que el ID no esté duplicado

**La aplicación se cierra**
- Revise los requisitos de Java
- Verifique la memoria disponible
- Reinicie la aplicación

---

## Flujo de Trabajo Sugerido

### Inicio de Período
1. Registrar nuevos estudiantes
2. Actualizar información de doctores
3. Cargar catálogo de materias
4. Asignar horarios

### Durante el Período
1. Registrar ingresos diarios
2. Actualizar cambios de información
3. Consultar horarios según necesidad
4. Generar reportes

### Fin de Período
1. Revisar registros completos
2. Generar estadísticas
3. Preparar siguiente período
4. Realizar respaldos

---

## Soporte Técnico

Para asistencia:
1. Revise este manual
2. Consulte el archivo README.md
3. Verifique los logs de la aplicación
4. Contacte al administrador del sistema

---

**Hospital San Rafael**  
*Sistema de Gestión Académica*  
Versión 1.0.0
