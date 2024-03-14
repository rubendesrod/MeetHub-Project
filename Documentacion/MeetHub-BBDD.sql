-- Creación de la base de datos "meethub" (si aún no existe)
CREATE DATABASE IF NOT EXISTS meethub;

-- Usar la base de datos "meethub"
USE meethub;

-- Borrar las tablas si existen
DROP TABLE IF EXISTS Invitaciones;
DROP TABLE IF EXISTS Reuniones;
DROP TABLE IF EXISTS Usuarios;

-- Creación de la tabla Usuarios
CREATE TABLE Usuarios (
    id_usuario INT PRIMARY KEY,
    nombre VARCHAR(100),
    apellidos VARCHAR(100),
    correo VARCHAR(100),
    token_google VARCHAR(100)
);

-- Creación de la tabla Reuniones
CREATE TABLE Reuniones (
    id_reunion INT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion TEXT,
    enlace_reunion VARCHAR(255),
    hora_inicio TIME,
    hora_fin TIME,
    fecha DATE,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Creación de la tabla Invitaciones
CREATE TABLE Invitaciones (
    id_asistencia INT PRIMARY KEY,
    id_usuario INT,
    id_reunion INT,
    confirmacion BOOLEAN,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_reunion) REFERENCES Reuniones(id_reunion)
);
