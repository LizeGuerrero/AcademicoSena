package com.academico.academicosena.tools;

import com.academico.academicosena.util.PasswordUtils;

public class PasswordCheck {
    public static void main(String[] args) {
        String plain = "test1234";
        String hash = null;
        if (args != null && args.length > 0) {
            hash = args[0];
        }
        if (hash == null) {
            System.err.println("Uso: PasswordCheck <hash>");
            System.exit(2);
        }
        boolean ok = PasswordUtils.verifyPassword(plain, hash);
        System.out.println(ok ? "COINCIDE" : "NO COINCIDE");
    }
}
