-- Script SQL para crear las tablas de AcademicoSena
-- Basado en las entidades JPA del proyecto

-- Tabla tipo_usuario
CREATE TABLE IF NOT EXISTS tipo_usuario (
    id_tipo_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla escala_calificacion
CREATE TABLE IF NOT EXISTS escala_calificacion (
    id_escala INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    minimo DECIMAL(5, 2) NOT NULL,
    maximo DECIMAL(5, 2) NOT NULL,
    descripcion VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla institucion
CREATE TABLE IF NOT EXISTS institucion (
    id_institucion INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    sigla VARCHAR(20),
    tipo_institucion VARCHAR(50),
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    email VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla nivel
CREATE TABLE IF NOT EXISTS nivel (
    id_nivel INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    numero INT,
    descripcion VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla tipo_programa
CREATE TABLE IF NOT EXISTS tipo_programa (
    id_tipo_programa INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla programa_academico
CREATE TABLE IF NOT EXISTS programa_academico (
    id_programa INT AUTO_INCREMENT PRIMARY KEY,
    id_institucion INT NOT NULL,
    id_tipo_programa INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) UNIQUE,
    modalidad VARCHAR(50),
    duracion_meses INT,
    descripcion VARCHAR(255),
    FOREIGN KEY (id_institucion) REFERENCES institucion(id_institucion),
    FOREIGN KEY (id_tipo_programa) REFERENCES tipo_programa(id_tipo_programa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla periodo_academico
CREATE TABLE IF NOT EXISTS periodo_academico (
    id_periodo INT AUTO_INCREMENT PRIMARY KEY,
    id_programa INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado VARCHAR(50),
    FOREIGN KEY (id_programa) REFERENCES programa_academico(id_programa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla asignatura
CREATE TABLE IF NOT EXISTS asignatura (
    id_asignatura INT AUTO_INCREMENT PRIMARY KEY,
    id_programa INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) UNIQUE,
    creditos INT,
    horas_semana INT,
    descripcion VARCHAR(255),
    FOREIGN KEY (id_programa) REFERENCES programa_academico(id_programa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla configuracion_periodo
CREATE TABLE IF NOT EXISTS configuracion_periodo (
    id_configuracion INT AUTO_INCREMENT PRIMARY KEY,
    id_periodo INT NOT NULL,
    max_estudiantes_por_grupo INT,
    fecha_inicio_inscripcion DATE,
    fecha_fin_inscripcion DATE,
    FOREIGN KEY (id_periodo) REFERENCES periodo_academico(id_periodo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_tipo_usuario INT NOT NULL,
    tipo_documento VARCHAR(50) NOT NULL,
    numero_documento VARCHAR(20) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE,
    genero VARCHAR(50),
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    estado VARCHAR(50) DEFAULT 'ACTIVO',
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso DATETIME,
    FOREIGN KEY (id_tipo_usuario) REFERENCES tipo_usuario(id_tipo_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla docente
CREATE TABLE IF NOT EXISTS docente (
    id_docente INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL UNIQUE,
    tipo_documento VARCHAR(50),
    numero_documento VARCHAR(20) UNIQUE,
    especialidad VARCHAR(100),
    estado VARCHAR(50),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla estudiante
CREATE TABLE IF NOT EXISTS estudiante (
    id_estudiante INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL UNIQUE,
    tipo_documento VARCHAR(50),
    numero_documento VARCHAR(20) UNIQUE,
    genero VARCHAR(50),
    estado VARCHAR(50),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla matricula
CREATE TABLE IF NOT EXISTS matricula (
    id_matricula INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante INT NOT NULL,
    id_periodo INT NOT NULL,
    fecha_matricula DATE NOT NULL,
    estado VARCHAR(50),
    FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_estudiante),
    FOREIGN KEY (id_periodo) REFERENCES periodo_academico(id_periodo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla asignacion_docente
CREATE TABLE IF NOT EXISTS asignacion_docente (
    id_asignacion INT AUTO_INCREMENT PRIMARY KEY,
    id_docente INT NOT NULL,
    id_asignatura INT NOT NULL,
    id_periodo INT NOT NULL,
    grupo INT,
    FOREIGN KEY (id_docente) REFERENCES docente(id_docente),
    FOREIGN KEY (id_asignatura) REFERENCES asignatura(id_asignatura),
    FOREIGN KEY (id_periodo) REFERENCES periodo_academico(id_periodo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla actividad_evaluacion
CREATE TABLE IF NOT EXISTS actividad_evaluacion (
    id_actividad INT AUTO_INCREMENT PRIMARY KEY,
    id_asignatura INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    tipo_actividad VARCHAR(50),
    valor_porcentaje INT,
    fecha_limite DATE,
    descripcion VARCHAR(255),
    FOREIGN KEY (id_asignatura) REFERENCES asignatura(id_asignatura)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla calificacion_actividad
CREATE TABLE IF NOT EXISTS calificacion_actividad (
    id_calificacion INT AUTO_INCREMENT PRIMARY KEY,
    id_actividad INT NOT NULL,
    id_estudiante INT NOT NULL,
    calificacion DECIMAL(5, 2),
    fecha_calificacion DATE,
    estado VARCHAR(50),
    FOREIGN KEY (id_actividad) REFERENCES actividad_evaluacion(id_actividad),
    FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_estudiante)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla calificacion_asignatura
CREATE TABLE IF NOT EXISTS calificacion_asignatura (
    id_calificacion INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante INT NOT NULL,
    id_asignatura INT NOT NULL,
    id_periodo INT NOT NULL,
    calificacion DECIMAL(5, 2),
    estado_asignatura VARCHAR(50),
    fecha_calificacion DATE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_estudiante),
    FOREIGN KEY (id_asignatura) REFERENCES asignatura(id_asignatura),
    FOREIGN KEY (id_periodo) REFERENCES periodo_academico(id_periodo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla calificacion_periodo
CREATE TABLE IF NOT EXISTS calificacion_periodo (
    id_calificacion INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante INT NOT NULL,
    id_periodo INT NOT NULL,
    calificacion_promedio DECIMAL(5, 2),
    estado VARCHAR(50),
    fecha_calificacion DATE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_estudiante),
    FOREIGN KEY (id_periodo) REFERENCES periodo_academico(id_periodo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar tipos de usuario
INSERT INTO tipo_usuario (nombre, descripcion) VALUES 
('Estudiante', 'Usuario tipo estudiante'),
('Docente', 'Usuario tipo docente'),
('Administrador', 'Usuario administrador del sistema');
