# Hospital San Rafael - Sistema de Gestión

Sistema de gestión para estudiantes y doctores del Hospital San Rafael desarrollado con JavaFX.

## Requisitos

- Java 17 o superior
- Maven 3.6 o superior

## Estructura del Proyecto

```
HospitalSanRafael/
├── src/
│   ├── main/
│   │   ├── java/com/hospital/sanrafael/
│   │   │   ├── Main.java                 # Clase principal
│   │   │   ├── controller/               # Controladores JavaFX
│   │   │   ├── model/                    # Modelos de datos
│   │   │   ├── service/                  # Capa de servicio
│   │   │   ├── dao/                      # Capa de acceso a datos
│   │   │   └── view/                     # Fábrica de vistas
│   │   └── resources/css/
│   │       └── styles.css                # Estilos de la aplicación
├── pom.xml                               # Configuración de Maven
└── README.md                             # Este archivo
```

## Características

### Gestión de Estudiantes
- Registro completo de información personal y académica
- Control de carrera, semestre y turno
- Visualización de materias inscritas
- Consulta de horarios semanales

### Gestión de Doctores
- Registro de información profesional
- Control de especialidad y número de colegiado
- Asignación de área en el hospital
- Gestión de estudiantes supervisados

### Materias
- Catálogo de materias con créditos
- Información de profesores y aulas
- Horarios por materia
- Requisitos por semestre

### Horarios
- Consulta de horarios por estudiante
- Visualización de materias inscritas
- Horario semanal detallado

### Registros
- Control de ingreso al hospital
- Resumen diario de asistencia
- Estadísticas por semestre y especialidad

## Instalación

1. Clonar o descargar el proyecto
2. Navegar a la carpeta del proyecto
3. Ejecutar `mvn clean install` para compilar
4. Ejecutar `mvn javafx:run` para iniciar la aplicación

## Ejecución

### Con Maven
```bash
mvn javafx:run
```

### Con Java
```bash
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -jar target/hospital-san-rafael-1.0.0.jar
```

## Uso

1. **Menú Principal**: Seleccione la sección que desea gestionar
2. **Estudiantes**: Registre o consulte información de estudiantes
3. **Doctores**: Administre el personal médico
4. **Materias**: Gestione el pensum académico
5. **Horarios**: Consulte horarios por estudiante
6. **Registros**: Controle el ingreso al hospital

## Tecnologías

- **Java 17**: Lenguaje de programación
- **JavaFX 17**: Interfaz gráfica
- **Maven**: Gestión de dependencias
- **GSON**: Serialización JSON (opcional)

## Modelo de Datos

### Persona (Clase Base)
- Estudiante
- Doctor

### Entidades
- Materia
- Horario
- RegistroHospitalario

## Autor

Desarrollado para el Hospital San Rafael

## Licencia

Propiedad del Hospital San Rafael
