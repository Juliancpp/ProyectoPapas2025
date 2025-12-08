CREATE DATABASE IF NOT EXISTS sistema_notas;
USE sistema_notas;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

CREATE TABLE cursos (
    codigo INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    creditos INT NOT NULL,
    id_profesor INT,
    horario VARCHAR(100),
    aula VARCHAR(50),
    capacidad INT DEFAULT 0,
    id_materias VARCHAR(50),

    FOREIGN KEY (id_profesor) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE inscripciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id INT NOT NULL,
    curso_codigo INT NOT NULL,
    fecha DATE NOT NULL,

    FOREIGN KEY (estudiante_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_codigo) REFERENCES cursos(codigo) ON DELETE CASCADE,

    UNIQUE (estudiante_id, curso_codigo)
);

CREATE TABLE nota (
    estudiante_id INT NOT NULL,
    curso_codigo INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    valor DOUBLE NOT NULL,
    peso DOUBLE NOT NULL,

    PRIMARY KEY (estudiante_id, curso_codigo, tipo),
    
    FOREIGN KEY (estudiante_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_codigo) REFERENCES cursos(codigo) ON DELETE CASCADE
);