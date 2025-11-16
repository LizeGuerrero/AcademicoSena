package com.academico.academicosena.tools;

import com.academico.academicosena.dao.UsuarioDAO;
import com.academico.academicosena.dao.impl.UsuarioDaoImpl;
import com.academico.academicosena.entity.Usuario;
import com.academico.academicosena.util.PasswordUtils;

import java.util.List;

public class PasswordMigration {

    // Utilidad para hashear contraseñas en texto plano existentes. Ejecutar una vez en un entorno controlado.
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDaoImpl();
        List<Usuario> usuarios = dao.buscarTodos();

        int updated = 0;
        for (Usuario u : usuarios) {
            String pw = u.getPassword();
            if (pw == null || pw.isEmpty()) continue;
            // Skip if looks already like a bcrypt hash
            if (pw.startsWith("$2a$") || pw.startsWith("$2b$") || pw.startsWith("$2y$")) continue;

            String hashed = PasswordUtils.hashPassword(pw);
            u.setPassword(hashed);
            dao.actualizar(u);
            updated++;
            System.out.println("Contraseña hasheada para usuario id=" + u.getId());
        }

        System.out.println("Migración de contraseñas completada. Actualizados: " + updated);
    }
}
