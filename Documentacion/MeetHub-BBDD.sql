-- Creación de la base de datos "meethub" (si aún no existe)
CREATE DATABASE IF NOT EXISTS meethub;

-- Usar la base de datos "meethub"
USE meethub;

-- Borrar las tablas si existen
DROP TABLE IF EXISTS Invitaciones;
DROP TABLE IF EXISTS Reuniones;
DROP TABLE IF EXISTS Usuarios;

-- Tabla Usuarios
CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    apellidos VARCHAR(255),
    correo VARCHAR(255),
    avatar VARCHAR(255),
    token_google VARCHAR(255)
);

-- Tabla Reuniones
CREATE TABLE Reuniones (
    id_reunion INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    descripcion TEXT,
    enlace_reunion VARCHAR(255),
    hora_inicio TIME,
    hora_fin TIME,
    fecha DATE,
    acta TEXT,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Tabla Invitaciones
CREATE TABLE Invitaciones (
    id_invitacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_reunion INT,
    confirmacion BOOLEAN,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_reunion) REFERENCES Reuniones(id_reunion)
);
