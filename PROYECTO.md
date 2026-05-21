# Proyecto: Sistema de Gestión Hospital San Rafael

## Descripción General

Sistema de gestión integral para el control de estudiantes y doctores del Hospital San Rafael, desarrollado con JavaFX para interfaces gráficas modernas y multiplataforma.

## Objetivos del Sistema

### Objetivo Principal
Implementar un sistema de información que permita la gestión eficiente de estudiantes y doctores, facilitando el control académico y hospitalario.

### Objetivos Específicos
1. Registrar y controlar información de estudiantes
2. Administrar el personal médico (doctores)
3. Gestionar el catálogo de materias
4. Consultar horarios académicos
5. Controlar registros de ingreso al hospital

## Alcance

### Funcionalidades Implementadas

#### Módulo de Estudiantes
- [x] Registro de estudiantes
- [x] Actualización de datos
- [x] Eliminación de registros
- [x] Consulta por carrera
- [x] Filtro por semestre
- [x] Control de turno (mañana/tarde/noche)
- [x] Historial académico

#### Módulo de Doctores
- [x] Registro de doctores
- [x] Gestión de especialidades
- [x] Control de número de colegiado
- [x] Asignación de áreas
- [x] Años de experiencia
- [x] Estudiantes asignados (tutorías)

#### Módulo de Materias
- [x] Catálogo de asignaturas
- [x] Créditos académicos
- [x] Semestre recomendado
- [x] Profesores responsables
- [x] Aulas asignadas

#### Módulo de Horarios
- [x] Consulta de horarios por estudiante
- [x] Visualización semanal
- [x] Detalle de materias inscritas
- [x] Información de profesores y aulas

#### Módulo de Registros
- [x] Control de ingreso al hospital
- [x] Registro con fecha y hora
- [x] Resumen diario de asistencia
- [x] Estadísticas por semestre
- [x] Estadísticas por especialidad

## Arquitectura del Sistema

### Patrón de Diseño
El sistema implementa el patrón **MVC (Modelo-Vista-Controlador)**:

#### Modelo (Model)
- `Persona` (Clase base)
  - `Estudiante`
  - `Doctor`
- `Materia`
- `Horario`
- `RegistroHospitalario`

#### Vista (View)
- `ViewFactory` (Interfaz)
- `FXViewFactory` (Implementación JavaFX)
- Vistas especializadas por módulo

#### Controlador (Controller)
- `MainController` (Navegación principal)
- `MainMenuController` (Menú principal)
- `EstudianteController` (Gestión de estudiantes)
- `DoctorController` (Gestión de doctores)
- `MateriasController` (Gestión de materias)
- `HorarioController` (Consulta de horarios)
- `RegistroController` (Control de ingresos)

### Capas del Sistema

#### Capa de Presentación
- Interfaces JavaFX
- Componentes visuales
- Manejo de eventos de UI

#### Capa de Negocio (Service)
- `EstudianteService`
- `DoctorService`
- `MateriaService`
- `DatosInicialesService`

#### Capa de Persistencia (DAO)
- `EstudianteDAO`
- `DoctorDAO`
- `MateriaDAO`
- `GenericDAO<T>` (Genérico)

## Tecnologías Utilizadas

### Lenguaje y Framework
- **Java 17**: Lenguaje de programación
- **JavaFX 17**: Framework para interfaces gráficas

### Herramientas de Construcción
- **Maven 3.6+**: Gestión de dependencias y build
- **Git**: Control de versiones (opcional)

### Dependencias
- `javafx-controls`: Componentes UI
- `javafx-fxml`: Markup language para UI
- `gson`: Serialización JSON (opcional)

### Estructura de Archivos
```.
HospitalSanRafael/
├── src/
│   ├── main/
│   │   ├── java/com/hospital/sanrafael/
│   │   │   ├── Main.java
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── service/
│   │   │   ├── dao/
│   │   │   └── view/
│   │   └── resources/css/
│   │       └── styles.css
├── data/                    # Archivos de datos
├── pom.xml                  # Maven
├── run.bat                  # Script Windows
├── run.sh                   # Script Linux/Mac
└── docs/                    # Documentación
```

## Modelo de Datos

### Entidad: Estudiante
```java
- id: String
- nombre: String
- apellido: String
- email: String
- telefono: String
- fechaNacimiento: String
- genero: String
- direccion: String
- carrera: String
- semestre: int
- turno: String
- materias: List<Materia>
- horarioSemanal: List<Horario>
```

### Entidad: Doctor
```java
- id: String
- nombre: String
- apellido: String
- email: String
- telefono: String
- fechaNacimiento: String
- genero: String
- direccion: String
- especialidad: String
- numeroColegiado: String
- areaAsignada: String
- aniosExperiencia: int
- estudiantesAsignados: List<String>
- horarioAtencion: List<Horario>
```

### Entidad: Materia
```java
- codigo: String
- nombre: String
- descripcion: String
- creditos: int
- semestreRecomendado: int
- profesor: String
- aula: String
- horarios: List<Horario>
- requisitos: List<String>
```

### Entidad: Horario
```java
- dia: String
- horaInicio: String
- horaFin: String
- actividad: String
- responsable: String
- aula: String
```

## Casos de Uso

### CU1: Registrar Estudiante
**Actor**: Administrador
**Precondición**: El estudiante no está registrado
**Flujo Principal**:
1. Ingresa al módulo de estudiantes
2. Presiona "Nuevo"
3. Diligencia el formulario
4. Presiona "Guardar"
**Postcondición**: Estudiante registrado en el sistema

### CU2: Consultar Horario
**Actor**: Estudiante/Doctor/Administrador
**Precondición**: Estudiante registrado con materias
**Flujo Principal**:
1. Ingresa al módulo de horarios
2. Ingresa ID del estudiante
3. Presiona "Buscar"
4. Visualiza horario completo
**Postcondición**: Muestra información académica

### CU3: Registrar Ingreso
**Actor**: Estudiante/Doctor/Seguridad
**Precondición**: Persona registrada en el sistema
**Flujo Principal**:
1. Ingresa al módulo de registros
2. Selecciona tipo (Estudiante/Doctor)
3. Ingresa ID
4. Presiona "Registrar Ingreso"
5. Visualiza información registrada
**Postcondición**: Registro creado con fecha y hora

## Manual de Usuario

Ver los archivos:
- `README.md` - Descripción general
- `INSTALACION.md` - Guía de instalación
- `GUIA_USO.md` - Manual de uso detallado

## Características Técnicas

### Persistencia
- Archivos planos (.dat) con serialización Java
- Almacenamiento local en carpeta `data/`
- Soporte para operaciones CRUD completas

### Seguridad
- Validación de datos de entrada
- Control de IDs únicos
- Manejo de excepciones

### Rendimiento
- Carga bajo demanda de datos
- Listas paginados en tablas
- Búsquedas optimizadas

### Interfaz
- Diseño limpio y moderno
- Colores corporativos del hospital
- Navegación intuitiva
- Responsive a diferentes resoluciones

## Futuras Mejoras

### Corto Plazo
- [ ] Exportar reportes a PDF
- [ ] Búsqueda avanzada con filtros múltiples
- [ ] Validación de campos en tiempo real
- [ ] Mensajes de confirmación más descriptivos

### Mediano Plazo
- [ ] Base de datos MySQL/PostgreSQL
- [ ] Autenticación de usuarios
- [ ] Roles y permisos
- [ ] Historial de cambios

### Largo Plazo
- [ ] Interfaz web
- [ ] Aplicación móvil
- [ ] Notificaciones por email
- [ ] Integración con sistemas hospitalarios

## Conclusiones

El sistema implementa de manera satisfactoria los requerimientos solicitados:
- ✅ Registro de estudiantes y doctores
- ✅ Visualización de semestres
- ✅ Consulta de horarios
- ✅ Gestión de materias
- ✅ Funcionalidades adicionales pertinentes

La solución es escalable, mantenible y fácil de usar, cumpliendo con los estándares de calidad de software.

---

**Desarrollado para**: Hospital San Rafael  
**Versión**: 1.0.0  
**Fecha**: 2026  
**Tecnología**: JavaFX 17
