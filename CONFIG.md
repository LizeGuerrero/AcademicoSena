# Configuración de base de datos (desarrollo)

Este proyecto puede leer la configuración de la base de datos desde variables de entorno o desde un archivo `hibernate.cfg.xml` local (no versionado).

Variables de entorno soportadas (recomendadas):

- `JDBC_DATABASE_URL` — URL JDBC completa, por ejemplo `jdbc:mysql://localhost:3306/mi_bd?useSSL=false&serverTimezone=UTC`
- `JDBC_DATABASE_USERNAME` — usuario de BD
- `JDBC_DATABASE_PASSWORD` — contraseña de BD
- `HIBERNATE_DIALECT` — (opcional) dialecto de Hibernate, p.ej. `org.hibernate.dialect.MySQL8Dialect` (por defecto: MySQL8)
- `HIBERNATE_HBM2DDL` — (opcional) `update`/`validate`/`none` (por defecto: `validate`)
- `HIBERNATE_SHOW_SQL` — `true` o `false` (por defecto: `false`)

Cómo usarlo localmente:

1. Copia los archivos de ejemplo si prefieres usar archivos de configuración:

   - `src/main/resources/META-INF/persistence.xml.example` → `src/main/resources/META-INF/persistence.xml`
   - `src/main/resources/hibernate.cfg.xml.example` → `src/main/resources/hibernate.cfg.xml`

   NOTA: los archivos reales `persistence.xml` y `hibernate.cfg.xml` están ignorados por `.gitignore` para evitar subir credenciales.

2. O, exporta las variables de entorno (ejemplo en PowerShell):

```powershell
$env:JDBC_DATABASE_URL = "jdbc:mysql://localhost:3306/sistema_academico?useSSL=false&serverTimezone=UTC"
$env:JDBC_DATABASE_USERNAME = "mi_usuario"
$env:JDBC_DATABASE_PASSWORD = "mi_password"
$env:HIBERNATE_HBM2DDL = "update"
```

3. Ejecuta la aplicación desde tu contenedor/IDE. `HibernateUtil` preferirá las variables de entorno, y si no están definidas, cargará `hibernate.cfg.xml` local.

Migración de contraseñas:

- Para migrar contraseñas existentes (si estaban en texto plano), hay una utilidad `src/main/java/com/academico/academicosena/tools/PasswordMigration.java`.
- Ejecútala localmente con `mvn -q -Dexec.mainClass="com.academico.academicosena.tools.PasswordMigration" exec:java` (o desde tu IDE).
- La utilidad sólo hashea contraseñas que no parezcan ya hasheadas con BCrypt.

Precauciones:

- Haz una copia de seguridad de tu base de datos antes de ejecutar migraciones.
- En producción, obliga a los usuarios a resetear contraseñas si no estás seguro del estado actual.
