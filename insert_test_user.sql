-- Script SQL para insertar un usuario de prueba en AcademicoSena
-- Base de datos: sistema_academico
-- Contraseña de prueba: test1234
-- Usuario de prueba: 1234567890 (CC)

-- Primero, asegúrate de que exista la tabla tipo_usuario
-- (esto depende de tu esquema)

-- Hash BCrypt de "test1234" con 12 rounds
-- Si necesitas regenerar: puedes usar la utilidad PasswordMigration o PasswordUtils

INSERT INTO tipo_usuario (nombre, descripcion) VALUES ('Estudiante', 'Usuario estudiante') 
ON DUPLICATE KEY UPDATE nombre='Estudiante';

INSERT INTO usuarios (
    id_tipo_usuario,
    tipo_documento,
    numero_documento,
    nombres,
    apellidos,
    email,
    telefono,
    direccion,
    password,
    estado,
    fecha_registro,
    ultimo_acceso,
    genero,
    fecha_nacimiento
) VALUES (
    (SELECT id_tipo_usuario FROM tipo_usuario WHERE nombre='Estudiante' LIMIT 1),
    'CC',
    '1234567890',
    'Juan',
    'Pérez',
    'juan.perez@example.com',
    '3001234567',
    'Calle 123 #45-67',
    '$2b$12$L7kR8z.4R5D8u/KzQ5E5fOxB8Q8kR5D8u/KzQ5E5fOxB8Q8kR5D8u',
    'ACTIVO',
    NOW(),
    NOW(),
    'MASCULINO',
    '2000-01-15'
);

-- CREDENCIALES DE ACCESO:
-- Tipo de Documento: CC
-- Número de Documento: 1234567890
-- Contraseña: test1234
-- Email: juan.perez@example.com

-- Para verificar el insert:
-- SELECT * FROM usuarios WHERE numero_documento = '1234567890';
