-- Creación de la base de datos "meethub" (si aún no existe)
CREATE DATABASE IF NOT EXISTS meethub;

-- Usar la base de datos "meethub"
USE meethub;

-- Borrar las tablas antes de volver a ejecuatarlas
drop table if exists Usuario;

-- Crear la tabla de Usuarios
CREATE TABLE Usuario (
    ID_Usuario INTEGER AUTO_INCREMENT PRIMARY KEY,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Nombre VARCHAR(255) NOT NULL,
    Apellidos VARCHAR(255),
    Contrasena VARCHAR(255) NOT NULL,
    FechaNacimiento VARCHAR(255),
    sexo ENUM('Hombre','Mujer'),
    Token VARCHAR(255),
    Avatar VARCHAR(255)
);

