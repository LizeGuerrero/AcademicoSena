package com.academico.academicosena.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Hash a plain-text password
    public static String hashPassword(String plain) {
        if (plain == null) return null;
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }

    // Verify a plain-text password against a stored hash
    public static boolean verifyPassword(String plain, String hashed) {
        if (plain == null || hashed == null) return false;
        try {
            return BCrypt.checkpw(plain, hashed);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
