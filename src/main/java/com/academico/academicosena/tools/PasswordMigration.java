package com.academico.academicosena.tools;

import com.academico.academicosena.dao.UsuarioDAO;
import com.academico.academicosena.dao.impl.UsuarioDaoImpl;
import com.academico.academicosena.entity.Usuario;
import com.academico.academicosena.util.PasswordUtils;

import java.util.List;

public class PasswordMigration {

    // Utility to hash existing plaintext passwords. Run once in a controlled environment.
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
            System.out.println("Hashed password for user id=" + u.getId());
        }

        System.out.println("Password migration completed. Updated: " + updated);
    }
}
