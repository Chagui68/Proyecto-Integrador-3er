@echo off
echo ==========================================
echo   Crear Base de Datos - Hospital San Rafael
echo ==========================================
echo.
echo Ejecutando creacion de base de datos...
echo.

REM Ejecutar la clase DatabaseCreator
mvn exec:java -Dexec.mainClass="com.hospital.sanrafael.database.DatabaseCreator" -q

echo.
echo ==========================================
echo Proceso completado
echo ==========================================
pause
