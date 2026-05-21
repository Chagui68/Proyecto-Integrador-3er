# Instrucciones de Instalación y Ejecución

## Requisitos Previos

### 1. Java Development Kit (JDK) 17
El proyecto requiere JDK 17 o superior.

**Descarga e instalación:**
- Windows: https://adoptium.net/temurin/releases/?version=17
- Seleccione el instalador MSI para Windows
- Siga las instrucciones del instalador

### 2. Apache Maven 3.6+
Maven se encarga de gestionar las dependencias y compilar el proyecto.

**Descarga:**
- Visite: https://maven.apache.org/download.cgi
- Descargue la versión binaria (zip)
- Extraiga en una carpeta (ej: C:\Program Files\Apache\maven)

**Configuración en Windows:**
1. Agregue Maven al PATH del sistema:
   - Panel de Control → Sistema → Configuración avanzada → Variables de entorno
   - En "Variables del sistema", busque "Path"
   - Agregue: `C:\Program Files\Apache\maven\bin`

2. Verifique la instalación:
   ```cmd
   java -version
   mvn -version
   ```

## Instalación del Proyecto

### Paso 1: Abrir el proyecto
Abra una terminal o PowerShell y navegue a la carpeta del proyecto:
```cmd
cd "C:\Users\danie\Documents\Proyectos\U\Proyecto Integrador 3er\HospitalSanRafael"
```

### Paso 2: Compilar el proyecto
```cmd
mvn clean install
```

Esto descargará las dependencias necesarias y compilará el proyecto.

## Ejecución

### Opción 1: Usando Maven (Recomendado)
```cmd
mvn javafx:run
```

### Opción 2: Usando el script incluido
```cmd
run.bat
```

## Estructura del Proyecto

```
HospitalSanRafael/
├── src/
│   ├── main/
│   │   ├── java/com/hospital/sanrafael/
│   │   │   ├── Main.java                    # Punto de entrada
│   │   │   ├── controller/                  # Controladores
│   │   │   │   ├── MainController.java
│   │   │   │   ├── MainMenuController.java
│   │   │   │   ├── EstudianteController.java
│   │   │   │   ├── DoctorController.java
│   │   │   │   ├── MateriasController.java
│   │   │   │   ├── HorarioController.java
│   │   │   │   └── RegistroController.java
│   │   │   ├── model/                       # Modelos
│   │   │   │   ├── Persona.java
│   │   │   │   ├── Estudiante.java
│   │   │   │   ├── Doctor.java
│   │   │   │   ├── Materia.java
│   │   │   │   ├── Horario.java
│   │   │   │   └── RegistroHospitalario.java
│   │   │   ├── service/                     # Lógica de negocio
│   │   │   │   ├── EstudianteService.java
│   │   │   │   ├── DoctorService.java
│   │   │   │   ├── MateriaService.java
│   │   │   │   └── DatosInicialesService.java
│   │   │   ├── dao/                         # Acceso a datos
│   │   │   │   ├── EstudianteDAO.java
│   │   │   │   ├── DoctorDAO.java
│   │   │   │   └── MateriaDAO.java
│   │   │   └── view/                        # Vistas
│   │   │       ├── ViewFactory.java
│   │   │       └── FXViewFactory.java
│   │   └── resources/css/
│   │       └── styles.css
├── pom.xml                                   # Configuración Maven
├── run.bat                                   # Script de ejecución
└── README.md                                 # Documentación
```

## Funcionalidades

### 1. Gestión de Estudiantes
- Registrar nuevos estudiantes
- Actualizar información
- Eliminar registros
- Consultar por carrera y semestre

### 2. Gestión de Doctores
- Registrar doctores
- Administrar especialidades
- Controlar número de colegiado
- Asignar áreas

### 3. Materias
- Catálogo completo
- Créditos y semestres
- Profesores responsables
- Aulas asignadas

### 4. Horarios
- Consulta por estudiante
- Visualización semanal
- Materias inscritas
- Horarios de clase

### 5. Registros
- Control de ingreso
- Resumen diario
- Estadísticas

## Datos de Ejemplo

El sistema incluye datos precargados:

### Doctores
- D001: Dr. Carlos Mendoza - Medicina Interna
- D002: Dra. Ana Rodríguez - Pediatría
- D003: Dr. Luis García - Cirugía General
- D004: Dra. María López - Ginecología
- D005: Dr. Roberto Silva - Cardiología

### Estudiantes
- E001: José Martínez - 1er Semestre
- E002: Sofía Hernández - 2do Semestre
- E003: Diego Torres - 3er Semestre
- E004: Valentina Ramírez - 4to Semestre
- E005: Andrés Vargas - 5to Semestre

## Solución de Problemas

### Error: "Java no está instalado"
- Verifique que JDK 17 esté instalado
- Verifique que JAVA_HOME apunte a la instalación de Java
- Reinicie la terminal después de instalar Java

### Error: "Maven no se encuentra"
- Verifique que Maven esté en el PATH
- Use `mvn -version` para verificar

### Error: "Module not found"
- Ejecute `mvn clean install` nuevamente
- Verifique que todas las dependencias se descargaron

### La aplicación no inicia
- Verifique los logs en la consola
- Asegúrese de tener JavaFX en las dependencias
- Revise que el module-info.java esté correcto

## Soporte

Para asistencia técnica, contacte al departamento de sistemas del Hospital San Rafael.
