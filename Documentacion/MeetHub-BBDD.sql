-- Creación de la base de datos "meethub" (si aún no existe)
CREATE DATABASE IF NOT EXISTS meethub;

-- Usar la base de datos "meethub"
USE meethub;

-- Borrar las tablas antes de volver a ejecuatarlas
drop table if exists Reunion;
drop table if exists Usuario;

-- Crear la tabla de Usuario s
CREATE TABLE Usuario (
    ID_Usuario INT AUTO_INCREMENT PRIMARY KEY,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Contraseña VARCHAR(255) NOT NULL,
    TokenDeActualizacion VARCHAR(255),
    Avatar VARCHAR(255)
);

-- Crear la tabla de Reuniones
CREATE TABLE Reunion (
    ID_Reunion INT AUTO_INCREMENT PRIMARY KEY,
    ID_Usuario INT,
    Titulo VARCHAR(255),
    Descripcion VARCHAR(255),
    FechaInicio DATETIME NOT NULL,
    FechaFin DATETIME NOT NULL,
    ID_Calendario VARCHAR(255),
    FOREIGN KEY (ID_Usuario) REFERENCES Usuario(ID_Usuario)
);

