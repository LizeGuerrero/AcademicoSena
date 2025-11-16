# Pull Request: Feature/password-hashing

## Descripción general
Esta PR implementa:
1. **Hashing seguro de contraseñas** usando BCrypt (jBCrypt 0.4)
2. **Externalización de credenciales de BD** mediante variables de entorno
3. **Migración segura** de contraseñas existentes
4. **Pruebas unitarias** completas para validar el comportamiento de hashing

## Cambios realizados

### 1. Dependencias
- Añadido `org.mindrot:jbcrypt:0.4` en `pom.xml` para hashing criptográfico.

### 2. Utilidades
- **`PasswordUtils`**: encapsula lógica de hashing y verificación
  - `hashPassword(String)`: genera un hash BCrypt seguro con salt aleatorio
  - `verifyPassword(String plain, String hashed)`: verifica si el plaintext coincide con el hash

### 3. Modificaciones de código de autenticación
- **`UsuarioServiceImpl.registrar()`**: hashea la contraseña antes de guardar
- **`UsuarioServiceImpl.actualizar()`**: hashea si se detecta texto plano (no hash existente)
- **`UsuarioDaoImpl.iniciarSesion()`**: recupera usuario y verifica hash en lugar de comparar en consulta SQL

### 4. Configuración de BD
- **`HibernateUtil`**: ahora soporta leer configuración desde variables de entorno:
  - `JDBC_DATABASE_URL`
  - `JDBC_DATABASE_USERNAME`
  - `JDBC_DATABASE_PASSWORD`
  - Opcionales: `HIBERNATE_DIALECT`, `HIBERNATE_HBM2DDL`, `HIBERNATE_SHOW_SQL`
  - Fallback a `hibernate.cfg.xml` si no hay variables de entorno

### 5. Herramientas de migración
- **`PasswordMigration`**: utilidad para hashear contraseñas existentes (safe—verifica que no estén ya hasheadas)

### 6. Documentación
- **`CONFIG.md`**: instrucciones detalladas para configurar BD y ejecutar migración

### 7. Pruebas
- **`PasswordUtilsTest`**: 9 pruebas unitarias que cubren:
  - Hashing correcto
  - Verificación de contraseña válida/inválida
  - Manejo de null
  - Caracteres especiales
  - Contraseñas largas
  - Salt aleatorio (diferentes hashes para el mismo plaintext)

## Instrucciones de instalación/prueba

### Desarrollo local
1. Exporta variables de entorno:
```powershell
$env:JDBC_DATABASE_URL = "jdbc:mysql://localhost:3306/sistema_academico?useSSL=false&serverTimezone=UTC"
$env:JDBC_DATABASE_USERNAME = "usuario"
$env:JDBC_DATABASE_PASSWORD = "password"
$env:HIBERNATE_HBM2DDL = "update"
```

2. Compila y prueba:
```powershell
mvn clean test
mvn clean package
```

3. (Opcional) Migra contraseñas existentes:
```powershell
mvn -Dexec.mainClass="com.academico.academicosena.tools.PasswordMigration" exec:java
```

## Consideraciones de seguridad

- ✅ Contraseñas hasheadas con BCrypt (salt aleatorio, 12 rounds)
- ✅ Credenciales de BD externalizadas (no en archivos versionados)
- ✅ Verificación segura en memoria (no texto plano en base de datos)
- ✅ Migración segura disponible para usuarios existentes
- ⚠️ En producción: obliga restablecimiento de contraseña para usuarios antiguos si no estás seguro del estado

## Commits en esta rama
- a1ed658: feat(auth): add BCrypt hashing and verify passwords; hash on register/update
- c9baa7a: chore(config): load DB config from env vars; add migration tool and docs
- 045a829: test(auth): add comprehensive unit tests for PasswordUtils

## Testing
Todas las pruebas unitarias están en `src/test/java/com/academico/academicosena/util/PasswordUtilsTest.java`. Ejecuta:
```powershell
mvn test -Dtest=PasswordUtilsTest
```

## Notas
- Archivos de configuración (`persistence.xml`, `hibernate.cfg.xml`) están ignorados por `.gitignore` para evitar subir credenciales.
- Los archivos `*.example` sirven como plantilla para configuración local.
