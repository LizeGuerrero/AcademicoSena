package com.academico.academicosena.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Hashea una contraseña en texto plano
    public static String hashPassword(String plain) {
        if (plain == null) return null;
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }

    // Verifica una contraseña en texto plano frente a un hash almacenado
    public static boolean verifyPassword(String plain, String hashed) {
        if (plain == null || hashed == null) return false;
        try {
            return BCrypt.checkpw(plain, hashed);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
